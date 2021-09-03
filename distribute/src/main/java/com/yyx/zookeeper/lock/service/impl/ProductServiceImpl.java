package com.yyx.zookeeper.lock.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yyx.zookeeper.lock.dao.OrderDao;
import com.yyx.zookeeper.lock.dao.ProductDao;
import com.yyx.zookeeper.lock.po.OrderPo;
import com.yyx.zookeeper.lock.po.ProductPo;
import com.yyx.zookeeper.lock.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public void addOrder() {

        ProductPo productPo= productDao.queryById(1);
        if (productPo == null) {
            log.error("商品不存在");
            return;
        }
        int stock = productPo.getStock();
        if (stock <= 0) {
            log.error("商品库存不足,product:{}", JSONObject.toJSONString(productPo));
        }
        productDao.dedueStock(1,productPo.getStock()-1);
        OrderPo orderPo = new OrderPo();
        orderPo.setPid(productPo.getId());
        orderPo.setUserId(UUID.randomUUID().toString());
        orderDao.addOrder(orderPo);
        log.info("购买成功");
    }

}
