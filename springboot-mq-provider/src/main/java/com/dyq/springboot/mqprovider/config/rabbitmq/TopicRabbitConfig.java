package com.dyq.springboot.mqprovider.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {

    @Bean
    public Queue firstQueue() {
        return new Queue(RabbitConst.TOPIC_MAN);
    }
 
    @Bean
    public Queue secondQueue() {
        return new Queue(RabbitConst.TOPIC_WOMAN);
    }
 
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(RabbitConst.TOPIC_EXCHANGE);
    }
 

    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(RabbitConst.TOPIC_MAN);
    }

    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }
 
}