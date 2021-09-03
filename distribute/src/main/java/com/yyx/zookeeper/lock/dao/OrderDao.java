package com.yyx.zookeeper.lock.dao;

import com.yyx.zookeeper.lock.po.OrderPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    void addOrder(@Param("order") OrderPo orderPo);
}
