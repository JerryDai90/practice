package com.practice.rabbitmq.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jerry dai
 * @date 2023-01-09- 20:14
 */
@Configuration
public class DirectConfig {

    public static final String sb_direct_queue = "sb_direct_queue";
    public static final String sb_direct_ex = "sb_direct_ex";
    public static final String sb_direct_key = "sb_direct_key";

    @Bean
    public Queue directQueue() {
        return new Queue(sb_direct_queue, true, false, false);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(sb_direct_ex);
    }

    @Bean
    Binding binding(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder
                .bind(directQueue)
                .to(directExchange)
                .with(sb_direct_key);
    }

}
