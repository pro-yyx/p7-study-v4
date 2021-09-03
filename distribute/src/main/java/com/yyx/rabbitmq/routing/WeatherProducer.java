package com.yyx.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yyx.rabbitmq.utils.RabbitConstant;
import com.yyx.rabbitmq.utils.RabbitmqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author yinyuxin
 * @description 天气producer exchangetype: direct
 * @date 2021/6/3 15:29
 */
@Slf4j
public class WeatherProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        Map<String, String> weatherMessage = packageWeatherMessage();
        Iterator<Map.Entry<String, String>> iterator = weatherMessage.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
//            第一个参数：exchange  第二个阐述  routing key
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_ROUTING,next.getKey(),null,next.getValue().getBytes());
        }
        log.info("消息发送完毕");
        channel.close();
        connection.close();
    }

    public static Map<String,String> packageWeatherMessage() {
        Map<String,String> area=new HashMap<>();
        area.put("china.sichuan.cd.20210603", "中国四川成都20210603天气数据");
        area.put("china.sichuan.cd.20210602", "中国四川成都20210602天气数据");
        area.put("china.sichuan.zg.20210603", "中国四川自贡20210603天气数据");
        area.put("china.beijing.bj.20210603", "中国北京20210603天气数据");
        area.put("us.cal.lsj.20210603", "美国加州洛杉矶20210603天气数据");
        return area;
    }
}
