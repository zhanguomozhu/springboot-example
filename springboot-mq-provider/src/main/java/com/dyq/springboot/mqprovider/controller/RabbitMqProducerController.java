package com.dyq.springboot.mqprovider.controller;

import com.dyq.springboot.mqprovider.config.rabbitmq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RabbitMqProducerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqProducerController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*******************************默认*********************************/

    /**
     * 处理路由键，需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
     * 这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键为 “info”，
     * 则只有路由键为“info”的消息才被转发，不会转发路由键为"error"，只会转发路由键为"info"。
     */
    @GetMapping("/direct/info")
    public void rabbitMqDirect1(){
        rabbitTemplate.convertAndSend("directModel","info","direct msg info");
    }

    @GetMapping("/direct/error")
    public void rabbitMqDirect2(){
        rabbitTemplate.convertAndSend("directModel","info","direct msg error");
    }

    @GetMapping("/direct/debug")
    public void rabbitMqDirect3(){
        rabbitTemplate.convertAndSend("directModel","info","direct msg debug");
    }

    /**
     * Fanout 不处理路由键。只需要简单的将队列绑定到交换机上。一个发送到该类型交换机的消息都会被广播到与该交换机绑定的所有队列上。
     */
    @GetMapping("/fanout")
    public void rabbitMqFanout(){
        rabbitTemplate.convertAndSend("fanoutModel","","fanout msg");
    }

    /**
     * 动态路由。将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。符号“#”匹配一个或多个词，符号“*”只能匹配一个词。
     */
    @GetMapping("/topic")
    public void rabbitMqTopic(){
        rabbitTemplate.convertAndSend("topicModel","amqp.test.info","Topic msg");
    }

    /******************************绑定队列*******************************/

    @GetMapping("/const/direct")
    public void sendDirectMessage() {
        rabbitTemplate.convertAndSend(RabbitConst.DIRECT_EXCHANGE, RabbitConst.DIRECT_KEY, "direct msg");
    }


    @GetMapping("/const/topic/msg1")
    public void sendTopicMessage1() {
        rabbitTemplate.convertAndSend(RabbitConst.TOPIC_EXCHANGE, RabbitConst.TOPIC_MAN, "topic msg1");
    }

    @GetMapping("/const/topic/msg2")
    public void sendTopicMessage2() {
        rabbitTemplate.convertAndSend(RabbitConst.TOPIC_EXCHANGE, RabbitConst.TOPIC_WOMAN, "topic msg2");
    }

    @GetMapping("/const/fanout")
    public void sendFanoutMessage() {
        rabbitTemplate.convertAndSend(RabbitConst.FANOUT_EXCHANGE, null, "fanout msg");
    }




    /**
     * 发送消息  延时
     * @param msg      发送对象
     * @param delayTime 延时（毫秒）
     */
    @GetMapping("/delay")
    public void sendDelayMessage(String msg, Integer delayTime) {
        System.out.println(delayTime);
        rabbitTemplate.convertAndSend(RabbitConst.DELAY_EXCHANGE, RabbitConst.DELAY_KEY, msg,
                message -> {
                    //几种延时设置
//                    message.getMessageProperties().setHeader("x-delay", delayTime); //毫秒
                    message.getMessageProperties().setDelay(delayTime); //毫秒 推荐
                    return message;
                }
        );
        LOGGER.info("delay 已发送：msg:{}，delayTime:{}",msg,delayTime);
    }

    /**
     * 发送消息  延时
     * @param msg      发送对象
     * @param delayTime 延时（毫秒）
     */
    @GetMapping("/delay1")
    public void sendMessage(String msg, Integer delayTime){
        //给延迟队列发送消息
        rabbitTemplate.convertAndSend(RabbitConst.DELAY1_TTL_EXCHANGE, RabbitConst.DELAY1_TTL_KEY, msg,
            message -> {
                message.getMessageProperties().setExpiration(String.valueOf(delayTime));
                return message;
            }
       );
        LOGGER.info("delay1 已发送：msg:{}，delayTime:{}",msg,delayTime);
    }

}