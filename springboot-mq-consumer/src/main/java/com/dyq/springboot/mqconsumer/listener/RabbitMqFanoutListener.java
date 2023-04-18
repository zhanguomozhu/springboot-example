package com.dyq.springboot.mqconsumer.listener;

import com.dyq.springboot.mqconsumer.config.RabbitConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqFanoutListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqFanoutListener.class);


    // rabbitMq监听
    @RabbitListener(bindings = {
            // 队列绑定
            @QueueBinding(
                    value = @Queue, // 队列名称，这里设置为临时队列
                    exchange = @Exchange(value = "fanoutModel",type = ExchangeTypes.FANOUT)  // 交换机定义
            )
    })
    public void receive1(String msg){
        LOGGER.info("fanout rabbitmq1 = " + msg);
    }



    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "fanoutModel",type = ExchangeTypes.FANOUT)
            )
    })
    public void receive2(String msg){
        LOGGER.info("fanout rabbitmq2 = " + msg);
    }


    @RabbitListener(queues = RabbitConst.FANOUT_A)
    public void receive3(String msg) {
        LOGGER.info("fanout rabbitmq_a = " + msg);
    }

    @RabbitListener(queues = RabbitConst.FANOUT_B)
    public void receive4(String msg) {
        LOGGER.info("fanout rabbitmq_b = " + msg);
    }

    @RabbitListener(queues = RabbitConst.FANOUT_C)
    public void receive5(String msg) {
        LOGGER.info("fanout rabbitmq_c = " + msg);
    }

}
