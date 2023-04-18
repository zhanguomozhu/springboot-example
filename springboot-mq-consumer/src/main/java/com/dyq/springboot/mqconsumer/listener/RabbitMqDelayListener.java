package com.dyq.springboot.mqconsumer.listener;

import com.dyq.springboot.mqconsumer.config.RabbitConst;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMqDelayListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqDelayListener.class);

    /**
     * 接收消息 延时
     *
     * @param object 监听的内容
     */
    @RabbitListener(queues = RabbitConst.DELAY_QUEUE)
    public void receive(Object object, Message message, Channel channel) throws IOException {
        try {
            LOGGER.info("delay rabbitmq = {}", object.toString());
            //确认签收参数说明（deliveryTag,是否批量签收）
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            /**
             * basicRecover方法是进行补发操作，
             * 其中的参数如果为true是把消息退回到queue但是有可能被其它的consumer(集群)接收到，
             * 设置为false是只补发给当前的consumer
             */
            channel.basicRecover(false);
        }
    }

    /**
     * 接收消息 延时
     *
     * @param object 监听的内容
     */
    @RabbitListener(queues = RabbitConst.DELAY1_QUEUE)
    public void receive1(Object object, Message message, Channel channel) throws IOException {
        try {
            LOGGER.info("delay1 rabbitmq = {}", object.toString());
            //确认签收参数说明（deliveryTag,是否批量签收）
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            /**
             * basicRecover方法是进行补发操作，
             * 其中的参数如果为true是把消息退回到queue但是有可能被其它的consumer(集群)接收到，
             * 设置为false是只补发给当前的consumer
             */
            channel.basicRecover(false);
        }
    }
}
