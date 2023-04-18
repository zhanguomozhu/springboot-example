package com.dyq.springboot.mqconsumer.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConst {

    /**
     * direct交换机,队列,路由
     */
    public static final String DIRECT_EXCHANGE = "direct.exchange";
    public static final String DIRECT_QUEUE = "direct.queue";
    public static final String DIRECT_KEY = "direct.key";
    /**
     * fanout交换机,队列,路由
     */
    public static final String FANOUT_EXCHANGE = "fanout.exchange";
    public static final String FANOUT_A = "fanout.A";
    public static final String FANOUT_B = "fanout.B";
    public static final String FANOUT_C = "fanout.C";
    /**
     * topic交换机,队列,路由
     */
    public final static String TOPIC_MAN = "topic.man";
    public final static String TOPIC_WOMAN = "topic.woman";
    public final static String TOPIC_EXCHANGE = "topic.exchange";


    /**
     * 延迟交换机,队列,路由
     */
    public static final String DELAY_EXCHANGE = "delay.exchange";
    public static final String DELAY_QUEUE = "delay.queue";
    public static final String DELAY_KEY = "delay.key";

    /**
     * 死信队列交换机,队列,路由
     */
    public static final String DELAY1_EXCHANGE = "delay1.exchange";
    public static final String DELAY1_QUEUE = "delay1.queue";
    public static final String DELAY1_KEY = "delay1.key";

    /**
     * 延迟队列交换机,队列,路由
     */
    public static final String DELAY1_TTL_EXCHANGE = "delay1.ttl.exchange";
    public static final String DELAY1_TTL_QUEUE = "delay1.ttl.queue";
    public static final String DELAY1_TTL_KEY = "delay1.ttl.key";
}

