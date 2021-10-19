package com.rabbit.publisher.controller;

import com.rabbit.publisher.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AppController {
    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/worker/{count}/{message}")
    public void workerModeString(@PathVariable("message") String message, @PathVariable("count") int count) {
        for (int i = 0; i < count; i++) {
            // 使用MessageConverter转换为byte[]后再发送，默认是SimpleMessageConverter
            // SimpleMessageConverter可以处理byte[],String and serializable object
            // 不指定exchange则是使用默认exchange
            rabbitTemplate.convertAndSend("workerString", message);
        }
    }

    @PostMapping("/worker/{count}")
    public void workerModeObject(@RequestBody User user, @PathVariable("count") int count) {
        // Jackson2JsonMessageConverter 也支持String
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        for (int i = 0; i < count; i++) {
            rabbitTemplate.convertAndSend("workerObject", user);
        }
    }

    @GetMapping("/fanout/{count}/{message}")
    public void fanoutModeString(@PathVariable("message") String message, @PathVariable("count") int count) {
        for (int i = 0; i < count; i++) {
            rabbitTemplate.convertAndSend("fanout_error", "", "error:" + message);
            rabbitTemplate.convertAndSend("fanout_info", "", "info:" + message);
        }
    }

    @PostMapping("/fanout/{count}")
    public void fanoutModeObject(@RequestBody User user, @PathVariable("count") int count) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        for (int i = 0; i < count; i++)
            rabbitTemplate.convertAndSend("fanout_user", "", user);
    }

    @GetMapping("/topic/{key}/{message}")
    public void topicModeString(@PathVariable("key") String key, @PathVariable("message") String message) {
        rabbitTemplate.convertAndSend("topic_exchange", key, message);
    }
}
