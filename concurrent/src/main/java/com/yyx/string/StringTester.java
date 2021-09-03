package com.yyx.string;

import java.util.ArrayList;
import java.util.List;

public class StringTester {

    /*  public static void func1(List<String> strings) {
          strings.add("efg");
      }
  */
    public static void func2(String s) {
        s += "bb";
        System.out.println("func2 s:"+s);
    }

    public static void main(String[] args) {
       /* List<String> strings=new ArrayList<>();
        strings.add("abc");
        func1(strings);
        System.out.println("strings:"+strings.toString());
        String s = "aa";
        func2(s);
        System.out.println("s:"+s);

        */

           /* String s1="java";
            String s2 = "java";
            String s3 = s1.intern();
            String s4=s3+"";
            String s5=new String("abc");

            System.out.println(s1.intern()==s2);
            System.out.println(s1==s3);
            System.out.println(s1==s4);
            System.out.println(s1==s5);*/
       /* String s0 = "jva";
        String s1 = "jva";
        System.out.println(s1==s1.intern());*/

        /*String  s3 = new String("2") + new String("2");
        String  s4 = "22";        // 常量池中不存在22，所以会新开辟一个存储22对象的常量池地址
        s3.intern();    // 常量池22的地址和s3的地址不同
        System.out.println(s3 == s4); // false*/

        Object ob0 = new Object();
        Object ob1 = ob0;
        System.out.println(ob0==ob1);
    }
}
