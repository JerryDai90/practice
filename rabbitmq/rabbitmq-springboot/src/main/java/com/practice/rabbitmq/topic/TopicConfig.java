package com.practice.rabbitmq.topic;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jerry dai
 * @date 2023-01-09- 20:14
 */
@Configuration
public class TopicConfig {

    public static final String sb_topic_queue = "sb_topic_queue";
    public static final String sb_topic_queue2 = "sb_topic_queue2";
    public static final String sb_topic_ex = "sb_topic_ex";

    public static final String sb_topic_routing_Key = "sb_topic_routing_Key";

    @Bean
    public Queue topicQueue() {
        return new Queue(sb_topic_queue, true, false, false);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(sb_topic_queue2, true, false, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(sb_topic_ex);
    }

    @Bean
    Binding topicBinding(Queue topicQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(topicQueue)
                .to(topicExchange)
                .with("sb_topic.*");
    }

    @Bean
    Binding topicBinding2(Queue topicQueue2, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(topicQueue2)
                .to(topicExchange)
                .with("sb_topic.queue2");
    }

}
