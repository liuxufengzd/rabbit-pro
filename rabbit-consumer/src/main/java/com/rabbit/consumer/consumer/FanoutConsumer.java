package com.rabbit.consumer.consumer;

import com.rabbit.consumer.entity.User;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    @RabbitListener(bindings = {@QueueBinding(value = @Queue,
            exchange = @Exchange(name = "fanout_error", type = "fanout"))})
    public void consumer1(String message) {
        System.out.println("fanout consumer error1:" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue,
            exchange = @Exchange(name = "fanout_error", type = "fanout"))})
    public void consumer2(String message) {
        System.out.println("fanout consumer error2:" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue,
            exchange = @Exchange(name = "fanout_info", type = "fanout"))})
    public void consumer3(String message) {
        System.out.println("fanout consumer info1:" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue,
            exchange = @Exchange(name = "fanout_info", type = "fanout"))})
    public void consumer4(String message) {
        System.out.println("fanout consumer info2:" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue,
            exchange = @Exchange(name = "fanout_user", type = "fanout"))})
    public void consumer4(User user) {
        System.out.println("fanout consumer user1:" + user);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue,
            exchange = @Exchange(name = "fanout_user", type = "fanout"))})
    public void consumer5(User user) {
        System.out.println("fanout consumer user2:" + user);
    }
}
