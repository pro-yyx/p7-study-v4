package com.yyx.jvm;

import com.sun.crypto.provider.DESKeyFactory;
import sun.misc.Launcher;
import sun.misc.URLClassPath;

import java.math.BigDecimal;

public class TestJDKClassLoader {

    public static void main(String[] args) {
      /*  // 引导类加载器 ： jre/lib下的 （打印出来为null的原因是 引导类加载器是由C++编写的）
        System.out.println(String.class.getClassLoader());
        //扩展类加载器 jre/lib/ext下的
        System.out.println(DESKeyFactory.class.getClassLoader());
        //应用类加载器 classpath下自定义的类的加载器
        System.out.println(TestJDKClassLoader.class.getClassLoader());

        System.out.println();*/


        BigDecimal b1 = new BigDecimal(0.0+(-1999.3));
        double v = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(v);
    }
}
