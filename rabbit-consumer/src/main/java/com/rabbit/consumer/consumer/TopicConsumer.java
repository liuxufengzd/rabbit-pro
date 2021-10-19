package com.rabbit.consumer.consumer;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {
    // @Queue 是构建临时queue
    @RabbitListener(bindings = {@QueueBinding(value = @Queue,
            exchange = @Exchange(value = "topic_exchange",type = "topic"),
            key = {"*.liu","xu.#"})})
    public void consumer1(String message){
        System.out.println("topic consumer1:"+message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue,
            exchange = @Exchange(value = "topic_exchange",type = "topic"),
            key = {"*.feng","lxf","xu.liu"})})
    public void consumer2(String message){
        System.out.println("topic consumer2:"+message);
    }
}
