package com.dyq.springboot.mqprovider.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutRabbitConfig {

    @Bean
    public Queue queueA() {
        return new Queue(RabbitConst.FANOUT_A);
    }
 
    @Bean
    public Queue queueB() {
        return new Queue(RabbitConst.FANOUT_B);
    }
 
    @Bean
    public Queue queueC() {
        return new Queue(RabbitConst.FANOUT_C);
    }
 
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConst.FANOUT_EXCHANGE);
    }
 
    @Bean
    Binding bindingExchangeA() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }
 
    @Bean
    Binding bindingExchangeB() {
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }
 
    @Bean
    Binding bindingExchangeC() {
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }
}