package com.practice.rabbitmq.fanout;

import com.practice.rabbitmq.direct.DirectConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 发布订阅模式，给交换机发送，相关绑定的队列就收到了
 * @author jerry dai
 * @date 2023-01-09- 20:18
 */
@Component
public class FanoutDemo {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostConstruct
    public void test() {
        //发送到交换机，通过交换机发送，会发送到下面2个队列中去
        rabbitTemplate.convertAndSend(FanoutConfig.sb_fanout_ex, null, "fanout to ex message");
    }

    //队列1
    @RabbitListener(queues = FanoutConfig.sb_fanout_queue)
    @RabbitHandler
    public void receiver(String str) {
        System.out.println("[fanout] Received queue ["+FanoutConfig.sb_fanout_queue+"]: "+str);
    }

    //队列2
    @RabbitListener(queues = FanoutConfig.sb_fanout_queue2)
    @RabbitHandler
    public void receiver2(String str) {
        System.out.println("[fanout] Received queue ["+FanoutConfig.sb_fanout_queue2+"]: "+str);
    }

}
