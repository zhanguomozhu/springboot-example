package com.dyq.springboot.mqprovider.config.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Delay1RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(Delay1RabbitConfig.class);

    /**
     * 死信队列交换机
     */
    @Bean
    DirectExchange orderDirect() {
        return new DirectExchange(RabbitConst.DELAY1_EXCHANGE,true,false);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(RabbitConst.DELAY1_QUEUE);
    }

    /**
     * 死信队列绑定到交换机
     */
    @Bean
    Binding orderBinding(DirectExchange orderDirect,Queue orderQueue){
        return BindingBuilder.bind(orderQueue).to(orderDirect).with(RabbitConst.DELAY1_KEY);
    }

    /**
     * 延迟队列交换机
     */
    @Bean
    DirectExchange orderTtlDirect() {
        return new DirectExchange(RabbitConst.DELAY1_TTL_EXCHANGE,true,false);
    }

    /**
     * 延迟队列
     */
    @Bean
    public Queue orderTtlQueue() {
        return QueueBuilder
                .durable(RabbitConst.DELAY1_TTL_QUEUE)
                .withArgument("x-dead-letter-exchange", RabbitConst.DELAY1_EXCHANGE)//到期后转发的交换机
                .withArgument("x-dead-letter-routing-key", RabbitConst.DELAY1_KEY)//到期后转发的路由键
                .build();
    }

    /**
     * 延迟队列绑定到交换机
     */
    @Bean
    Binding orderTtlBinding(DirectExchange orderTtlDirect,Queue orderTtlQueue){
        return BindingBuilder.bind(orderTtlQueue).to(orderTtlDirect).with(RabbitConst.DELAY1_TTL_KEY);
    }
}

