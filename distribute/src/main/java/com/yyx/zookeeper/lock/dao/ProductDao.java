package com.yyx.zookeeper.lock.dao;

import com.yyx.zookeeper.lock.po.ProductPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDao {

    ProductPo queryById(@Param("id") int id);

    void dedueStock(@Param("id") int id, @Param("buyNum") int buyNum);
}
