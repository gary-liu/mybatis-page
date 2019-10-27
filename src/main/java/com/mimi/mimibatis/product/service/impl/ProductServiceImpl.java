package com.mimi.mimibatis.product.service.impl;

import com.mimi.mimibatis.product.mapper.ProductMapper;
import com.mimi.mimibatis.product.service.ProductService;
import com.mimi.mimibatis.util.PageUtil;
import com.mimi.mimibatis.util.R;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductMapper productMapper;





    @Override
    public R getProductByArrayPage(int page, int limit) {
        List<Map<String,Object>> data = productMapper.getProductByArrayPage();
        //从第几条开始
        int startNum = (page-1)*limit;
        //到第几条结束
        int lastNum = page * limit;
        if (lastNum>data.size()){
            lastNum = data.size();
        }
        return R.success().set("count",data.size()).data(data.subList(startNum,lastNum));
    }

    @Override
    public R getProductPage(int page,int limit) {
        Map<String,Object> map = new HashMap<>();
        map.put("start",(page-1)*limit);
        map.put("limit",limit);
        System.out.println("调用了sql分页");
        return R.success().data(productMapper.getProductPage(map)).set("count",productMapper.getProductCount());
    }

    @Override
    public R getProductByRowboundsPage(int page,int limit) {
        RowBounds rowBounds = new RowBounds((page-1)*limit,limit);
        System.out.println("调用了RowBounds分页");
        return R.success().data(productMapper.getProductByRowboundsPage(rowBounds)).set("count",productMapper.getProductCount());
    }

    @Override
    public R getProductByPage(int page,int limit) {
        Map<String,Object> map =new HashMap<>();
        PageUtil pageUtil = new PageUtil(page,limit);
        map.put("page",pageUtil);
        return R.success().data(productMapper.getProductByPage(map)).set("count",pageUtil.getCount());
}
}
