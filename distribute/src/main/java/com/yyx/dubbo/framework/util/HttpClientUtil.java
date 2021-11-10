package com.yyx.dubbo.framework.util;

import com.alibaba.fastjson.JSONObject;
import com.yyx.dubbo.framework.Invocation;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/14 18:31
 */
public class HttpClientUtil {

    public String send(String hostName, Integer port, Invocation invocation)  {
        //读取用户的配置，选择哪种http的实现
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = JSONObject.toJSONString(invocation);
        Request request=new Request.Builder().url("http://"+hostName+":"+port+"/")
                .post(RequestBody.create(mediaType,requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        try (Response response = okHttpClient.newCall(request).execute()) {
           return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
