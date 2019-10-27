package com.mimi.mimibatis.product.service;


import com.mimi.mimibatis.util.R;


public interface ProductService {


    //数组分页
    public R getProductByArrayPage(int pageNum, int pageSize);
    //数据库分页
    public R getProductPage(int pageNum, int pageSize);
    //Rowbounds分页
    public R getProductByRowboundsPage(int startNum, int limit);
    //插件分页
    public R getProductByPage(int page, int limit);


}
