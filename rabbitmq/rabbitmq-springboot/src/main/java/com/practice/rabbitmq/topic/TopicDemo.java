package com.practice.rabbitmq.topic;

import com.practice.rabbitmq.fanout.FanoutConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 主题模式，换句话来说是通配符模式，通过通过通配符来匹配相关routingKey
 * @author jerry dai
 * @date 2023-01-09- 20:18
 */
@Component
public class TopicDemo {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostConstruct
    public void test() {
        //这个只能是 TopicConfig.sb_topic_queue 队列可以接受到
        rabbitTemplate.convertAndSend(TopicConfig.sb_topic_ex, "sb_topic.2", "only queue "+TopicConfig.sb_topic_queue+" can be receiver ex message");

        //这样是2个队列都可以接收到
        rabbitTemplate.convertAndSend(TopicConfig.sb_topic_ex, "sb_topic.queue2", "all topic queue can be receiver ex message");
    }

    //队列1
    @RabbitListener(queues = TopicConfig.sb_topic_queue)
    @RabbitHandler
    public void receiver(String str) {
        System.out.println("[topic] Received queue ["+TopicConfig.sb_topic_queue+"]: "+str);
    }

    //队列2
    @RabbitListener(queues = TopicConfig.sb_topic_queue2)
    @RabbitHandler
    public void receiver2(String str) {
        System.out.println("[topic] Received queue ["+TopicConfig.sb_topic_queue2+"]: "+str);
    }

}
