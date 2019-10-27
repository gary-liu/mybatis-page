package com.mimi.mimibatis.test;

import com.mimi.mimibatis.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Producer {


    @Autowired
    SenderUtil senderUtil;

    @Autowired
    ProductMapper mapper;


}
