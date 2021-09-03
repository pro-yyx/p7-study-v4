package com.yyx.jvm;

import java.util.ArrayList;
import java.util.List;

public class TestOOM {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        while (true) {
            users.add(new User());
            User user2 = new User();
        }
    }

}
