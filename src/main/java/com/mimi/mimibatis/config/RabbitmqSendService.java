package com.mimi.mimibatis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * rabbitMQ的失败回调的实现
 * @date: 2019-07-31 14:43
 *
 */
@Component
public class RabbitmqSendService implements RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    private @PostConstruct void init(){
//        //开启/设置失败回调
//        this.rabbitTemplate.setMandatory(true);
//        this.rabbitTemplate.setReturnCallback(this);
//    }


    /** 错误消息存储最大时间：30天 */
    private static final int MAX_TIME = 2592000;

    /** 队列错误存储键值 */
    private static final String MQ_ERROR = "mq.error.";


    /**
     * 具体实现
     * @param message   发送的消息
     * @param replyCode 错误状态码
     * @param replyText 错误原因
     * @param exchange  交换机名字
     * @param routingKey    路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
                                String exchange, String routingKey) {
        String key = MQ_ERROR+replyCode+"."+routingKey+".1";
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode data = mapper.createObjectNode();
        data.set("msg",mapper.valueToTree(message));
        data.set("content",mapper.valueToTree(new String(message.getBody())));
        //不能放到队列
        this.sendMessage("","ttt",data.toString());
        System.out.println(1);
        //jedisManager.set(key,data.toString(),MAX_TIME);
    }
    /**
     * 发送消息
     * @param json 发送消息需要先把对象转为json字符串发送
     * @param queueName 在{@link }配置自己的队列
     */
    public  void sendMessage(String queueName,String json){
        rabbitTemplate.convertAndSend(queueName,json);
    }
    /**
     * 发送消息
     * @param json 发送消息需要先把对象转为json字符串发送
     */
    public void sendMessage(String exchangeName,String queueName,String json){
        rabbitTemplate.convertAndSend(exchangeName,queueName,json);
    }

}
