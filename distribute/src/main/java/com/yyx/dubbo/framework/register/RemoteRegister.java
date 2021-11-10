package com.yyx.dubbo.framework.register;

import com.yyx.dubbo.framework.URL;
import org.apache.commons.collections.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/15 18:27
 */
public class RemoteRegister {

    //注册中心注册：将服务注册到注册中心
    private static Map<String, List<URL>> map = new HashMap<>();
    private static File file = null;

    public static void register(String serviceName, URL url) throws IOException {
        List<URL> urls = get(serviceName);
        if (CollectionUtils.isEmpty(urls)) {
            urls = new ArrayList<>();
        }
        urls.add(url);
        map.put(serviceName, urls);
        //saveFile();
    }

    public static List<URL> get(String serviceName) {
        //map = getFile();

        return map.get(serviceName);
    }

    public static void saveFile() throws IOException {
        if (null == file) {
            file=new File("/temp.txt");
            file.createNewFile();
        }
        FileOutputStream io=new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(io);
        objectOutputStream.writeObject(map);
    }

    private static Map<String, List<URL>> getFile() {
        try {
            if (null == file) {
                file=new File("/temp.txt");
                file.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
