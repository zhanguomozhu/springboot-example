package com.dyq.springboot.rocketmq.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = RocketContants.CONSUMER_GROUP2, topic = RocketContants.TEST_TOPIC)
public class RocketConsumer3 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        //同个组订阅同一主题，同一个消费者组的消费者订阅同一话题，组内只能消费一次消息
        System.err.println("RocketConsumer3接收到消息：" + message);
    }
}