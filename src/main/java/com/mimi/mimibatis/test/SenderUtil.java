package com.mimi.mimibatis.test;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SenderUtil {

    @Autowired
    AmqpTemplate amqpTemplate;

    public  void send(String message){
        amqpTemplate.convertAndSend("topic.message",message);
    }

    public  void send(String message,String roukey){
        amqpTemplate.convertAndSend(roukey,message);
    }
}
