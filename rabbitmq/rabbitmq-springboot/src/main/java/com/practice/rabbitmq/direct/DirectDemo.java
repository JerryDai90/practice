package com.practice.rabbitmq.direct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jerry dai
 * @date 2023-01-09- 20:18
 */
@Component
public class DirectDemo {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostConstruct
    public void test() {
        //可以直接发送到队列（其实是通过默认的交换机发送的）
        rabbitTemplate.convertAndSend(DirectConfig.sb_direct_queue, "direct to queue message");

        //发送到交换机，通过交换机发送
        rabbitTemplate.convertAndSend(DirectConfig.sb_direct_ex, DirectConfig.sb_direct_key, "direct to ex message");
    }

    @RabbitListener(queues = DirectConfig.sb_direct_queue)
    @RabbitHandler
    public void receiver(String str) {
        System.out.println(str);
    }

}
