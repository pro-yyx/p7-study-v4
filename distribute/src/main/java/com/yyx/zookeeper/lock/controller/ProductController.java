package com.yyx.zookeeper.lock.controller;

import com.yyx.zookeeper.lock.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/addOrder")
    public void addOrder() {
        productService.addOrder();
    }
}
