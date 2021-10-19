package com.rabbit.consumer.consumer;

import com.rabbit.consumer.entity.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

// 这里我们实现了能者多劳的背压
// RabbitListener(都相同的话) + RabbitHandler可以节省重复代码
@Component
//@RabbitListener(queuesToDeclare = {@Queue(name = "workerString")}, ackMode = "MANUAL")
public class WorkerConsumer {
    // 默认不设置bindings的情况下，会将queue和默认exchange (name="AMQP default",type=direct)绑定并设置routingKey为该queue的名字
    @RabbitListener(queuesToDeclare = {@Queue(name = "workerString")}, ackMode = "MANUAL")
//    @RabbitHandler
    public void consumer1(String message, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        System.out.println("worker consumer1:" + message);
        channel.basicAck((long) headers.get(AmqpHeaders.DELIVERY_TAG), true);
    }

    @RabbitListener(queuesToDeclare = {@Queue(name = "workerString")}, ackMode = "MANUAL")
    //    @RabbitHandler
    public void consumer2(String message, Channel channel, @Headers Map<String, Object> headers) {
        try {
            Thread.sleep(1000);
            System.out.println("worker consumer2:" + message);
            channel.basicAck((long) headers.get(AmqpHeaders.DELIVERY_TAG), true);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queuesToDeclare = {@Queue(name = "workerObject")}, ackMode = "MANUAL")
    public void consumer3(User user, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        System.out.println("worker consumer3:" + user);
        channel.basicAck((long) headers.get(AmqpHeaders.DELIVERY_TAG), true);
    }

    @RabbitListener(queuesToDeclare = {@Queue(name = "workerObject")}, ackMode = "MANUAL")
    public void consumer4(User user, Channel channel, @Headers Map<String, Object> headers) {
        try {
            Thread.sleep(1000);
            System.out.println("worker consumer4:" + user);
            channel.basicAck((long) headers.get(AmqpHeaders.DELIVERY_TAG), true);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
