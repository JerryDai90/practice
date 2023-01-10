package com.practice.rabbitmq.fanout;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jerry dai
 * @date 2023-01-09- 20:14
 */
@Configuration
public class FanoutConfig {

    public static final String sb_fanout_queue = "sb_fanout_queue";
    public static final String sb_fanout_queue2 = "sb_fanout_queue2";
    public static final String sb_fanout_ex = "sb_fanout_ex";

    @Bean
    public Queue fanoutQueue() {
        return new Queue(sb_fanout_queue, true, false, false);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue(sb_fanout_queue2, true, false, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(sb_fanout_ex);
    }

    @Bean
    Binding fanoutBinding(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(fanoutQueue)
                .to(fanoutExchange);
    }

    @Bean
    Binding fanoutBinding2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(fanoutQueue2)
                .to(fanoutExchange);
    }

}
