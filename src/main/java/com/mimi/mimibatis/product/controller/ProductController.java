package com.mimi.mimibatis.product.controller;


import com.mimi.mimibatis.product.service.impl.ProductServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @RequestMapping("/send")
    public Object send(){
        rabbitTemplate.convertAndSend ("testExchange","asdasd","111");
        return "111";
    }

    @Autowired
    ProductServiceImpl productService;


    @RequestMapping("/getProductByArrayPage")
    public Object getProductByArrayPage(int page,int limit){

        return productService.getProductByArrayPage(page, limit);
    }




    @RequestMapping("/getProductPage")
    public Object getProductPage(int page,int limit){

        return productService.getProductPage(page,limit);
    }

    @RequestMapping("/getProductByRowboundsPage")
    public Object getProductByRowboundsPage(int page,int limit){
        return productService.getProductByRowboundsPage(page,limit);
}

    @RequestMapping("/getProductByPage")
    public Object getProductByPage(int page,int limit){
        return  productService.getProductByPage(page,limit);
    }
}
