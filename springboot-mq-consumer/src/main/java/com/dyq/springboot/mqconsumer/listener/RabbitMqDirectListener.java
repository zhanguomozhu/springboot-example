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
public class RabbitMqDirectListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqDirectListener.class);



    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "directModel",type = ExchangeTypes.DIRECT),
                    key = {
                            "info"
                    }
            )
    })
    public void receive1(String msg){
        LOGGER.info("direct rabbitmq1 = " + msg);
    }


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "directModel",type = ExchangeTypes.DIRECT),
                    key = {
                            "warn",
                            "error"
                    }
            )
    })
    public void receive2(String msg){
        LOGGER.info("direct rabbitmq2 = " + msg);
    }


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "directModel",type = ExchangeTypes.DIRECT),
                    key = {
                            "debug"
                    }
            )
    })
    public void receive3(String msg){
        LOGGER.info("direct rabbitmq3 = " + msg);
    }



    @RabbitListener(queues = RabbitConst.DIRECT_QUEUE)
    public void receive4(String msg) {
        LOGGER.info("direct rabbitmq4 = " + msg);
    }

}
