package com.yyx.rabbitmq.topic;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import com.yyx.rabbitmq.utils.RabbitConstant;
import com.yyx.rabbitmq.utils.RabbitmqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author yinyuxin
 * @description 天气producer exchangetype: routing  通配符  # 一个或多个字符   * 一个字符
 * @date 2021/6/3 15:29
 */
@Slf4j
public class WeatherProducer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        //开启投送监听

        channel.confirmSelect();
        channel.basicQos(1);

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                //b代表接受的数据是否为批量接收，一般用不到
                if (b) {
                    log.info("消息投递成功，mmulti,tag:{}", l);
                } else {
                    log.info("消息投递成功，sington,tag:{}", l);
                }

            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                log.info("消息投递失败，tag:{}",l);
            }
        });

        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return aReturn) {
                log.info("return:{}", JSONObject.toJSONString(aReturn));
                log.info("---------------------------");

            }
        });

        Map<String, String> weatherMessage = packageWeatherMessage();
        Iterator<Map.Entry<String, String>> iterator = weatherMessage.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
//            第一个参数：exchange  第二个阐述  routing key  第三个参数 默认false 可不写. false
            //意味着投递queue失败会直接丢弃message，true会回调returnListener方法
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC,next.getKey(),true,null,next.getValue().getBytes());
            log.info(String.valueOf(i++));
        }
        log.info("消息发送完毕");

       /* channel.close();
        connection.close();*/
    }

    public static Map<String,String> packageWeatherMessage() {
        Map<String,String> area=new HashMap<>();
        area.put("china.sichuan.cd.20210603", "中国四川成都20210603天气数据");
        area.put("china.sichuan.cd.20210602", "中国四川成都20210602天气数据");
        area.put("2222222china.sichuan.zg.20210603", "中国四川自贡20210603天气数据");
        area.put("china.beijing.bj.20210603", "中国北京20210603天气数据");
        area.put("us.cal.lsj.20210603", "美国加州洛杉矶20210603天气数据");
        return area;
    }
}
