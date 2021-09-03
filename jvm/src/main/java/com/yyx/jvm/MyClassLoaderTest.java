package com.yyx.jvm;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class MyClassLoaderTest {



    static class MyClassLoader extends ClassLoader {
        private String classPath;


        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节 数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }


        protected Class<?> loadClass(String name, boolean resolve)
                throws ClassNotFoundException
        {
            synchronized (super.getClassLoadingLock(name)) {
                // First, check if the class has already been loaded
                Class<?> c = findLoadedClass(name);
                if (c == null) {
                    long t0 = System.nanoTime();
                    if (c == null) {
                        // If still not found, then invoke findClass in order
                        // to find the class.
                        long t1 = System.nanoTime();
                        if (name.startsWith("com.yyx.jvm.User")) {
                            c = findClass(name);
                        } else {
                            c=this.getParent().loadClass(name);
                        }
                        // this is the defining class loader; record the stats
                        sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                        sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                        sun.misc.PerfCounter.getFindClasses().increment();
                    }
                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
        }


        public static void main(String[] args) throws Exception {
            MyClassLoader myClassLoader = new MyClassLoader("D:/personal/projects/class");
            Class clazz = myClassLoader.loadClass("com.yyx.jvm.User");
            Object object = clazz.newInstance();
            Method say = clazz.getDeclaredMethod("say", String.class);
            say.invoke(object, "yyx");
            System.out.println(clazz.getClassLoader().getClass().getName());

            MyClassLoader myClassLoader2 = new MyClassLoader("D:/personal/projects/class2");
            Class clazz2 = myClassLoader2.loadClass("com.yyx.jvm.User");
            Object object2 = clazz2.newInstance();
            Method say2 = clazz2.getDeclaredMethod("say", String.class);
            say2.invoke(object2, "yyx2");
            System.out.println(clazz2.getClassLoader().getClass().getName());
        }
    }
}
