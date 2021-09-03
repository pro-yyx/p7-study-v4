package com.yyx.rabbitmq.workqueue;

/**
 * @author yinyuxin
 * @description 短信服务伪代码
 * @date 2021/6/2 22:38
 */
public class SMS {

    private String name;

    private String content;

    private String phone;

    public SMS(String name, String content, String phone) {
        this.name = name;
        this.content = content;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
