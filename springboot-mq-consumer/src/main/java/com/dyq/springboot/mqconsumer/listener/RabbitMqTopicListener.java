package com.dyq.springboot.mqconsumer.listener;

import com.dyq.springboot.mqconsumer.config.RabbitConst;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMqTopicListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqTopicListener.class);

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "topicModel", type = ExchangeTypes.TOPIC),
                    key = {
                            "*.test.*"
                    }
            )
    })
    /**
     * @param content:监听到的信息
     * @param channel:绑定的队列
     * @param message
     */
    public void receive1(String content, Channel channel, Message message) throws IOException {
        try {
            LOGGER.info("topic rabbitmq = {}", content);
            //确认签收参数说明（deliveryTag,是否批量签收）
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            //拒绝签收参数说明（deliveryTag,是否批量签收,是否放回队列中）
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            e.printStackTrace();
        }
    }


    @RabbitListener(queues = RabbitConst.TOPIC_MAN)
    public void receive2(String msg) {
        LOGGER.info("topic rabbitmq2 = " + msg);
    }

    @RabbitListener(queues = RabbitConst.TOPIC_WOMAN)
    public void receive3(String msg) {
        LOGGER.info("topic rabbitmq3 = " + msg);
    }
}
