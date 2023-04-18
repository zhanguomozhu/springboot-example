package com.dyq.springboot.mqprovider.config.rabbitmq;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitMqConfig {
 
 
    /**
     * 定义交换机名称
     */
    private String EXCHANGE_NAME="fanout_exchange";
 
    /**
     * 短信队列
     */
    private String FANOUT_SMS_QUEUE = "fanout_sms_queuerk";
 
    /**
     * 邮件队列
     */
    private String FANOUT_EMAIL_QUEUE = "fanout_email_queuerk";
 
 
    /**
     * 定义 扇形交换机
     */
    @Bean
    public FanoutExchange fanoutExchange1(){
        //参数 交换机名称
        return new FanoutExchange(EXCHANGE_NAME);
    }
 
    /**
     *参数一:队列名称
     *参数二durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
     *参数三exclusive:默认也是false，是否独占队列
     *参数四autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
     */
    @Bean
    public Queue smsQueue(){
        return new Queue(FANOUT_SMS_QUEUE,true, false,false);
    }
 
    /**
     *参数一:队列名称
     *参数二:durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
     *参数三:exclusive:默认也是false，是否独占队列
     *参数四:autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
     */
    @Bean
    public Queue emailQueue(){
        return new Queue(FANOUT_EMAIL_QUEUE,true,false,false);
    }
 
    /**
     * 短信队列绑定交换机
     * @param smsQueue   短信队列注入到容器的id，也就是方法名 sumQueue
     * @param fanoutExchange   交换机注入到容器的id，也就是方法名 fanoutExchange
     * @return
     */
    @Bean
    public Binding bindingSmsFanoutExchange(Queue smsQueue,FanoutExchange fanoutExchange){
        return  BindingBuilder.bind(smsQueue).to(fanoutExchange1());
    }
 
    /**
     * 邮件队列队列绑定交换机
     * @param emailQueue   短信队列注入到容器的id，也就是方法名 emailQueue
     * @param fanoutExchange   交换机注入到容器的id，也就是方法名 fanoutExchange
     * @return
     */
    @Bean
    public Binding bindingEmailFanoutExchange(Queue emailQueue,FanoutExchange fanoutExchange){
        return  BindingBuilder.bind(emailQueue).to(fanoutExchange1());
    }
}