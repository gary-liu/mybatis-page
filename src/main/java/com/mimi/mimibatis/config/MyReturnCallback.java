package com.mimi.mimibatis.config;//package com.luban.config;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//

//@Component
//public class MyReturnCallback implements RabbitTemplate.ReturnCallback {
//
//
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//
//
//    @Override
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        System.out.println(message);
//        System.out.println(replyCode);
//        System.out.println(replyText);
//        System.out.println(exchange);
//        System.out.println(routingKey);
//        rabbitTemplate.convertAndSend(null,"ttt","123");
//        System.out.println(1);
//    }
//}