package com.mimi.mimibatis.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {


    //数组分页
    List<Map<String,Object>> getProductByArrayPage();
    //数据库分页
    List<Map<String,Object>> getProductPage(Map<String, Object> map);
    //Rowbounds分页
    List<Map<String,Object>> getProductByRowboundsPage(RowBounds rowBounds);

    //插件分页
    List<Map<String,Object>> getProductByPage(Map<String, Object> map);

    int getProductCount();








}
