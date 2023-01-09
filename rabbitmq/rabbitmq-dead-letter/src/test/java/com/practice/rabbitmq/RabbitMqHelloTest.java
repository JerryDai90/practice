package com.practice.rabbitmq;

import com.practice.rabbitmq.config.RabbitConfig;
import com.practice.rabbitmq.mq.HelloReceiver;
import com.practice.rabbitmq.mq.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jerry dai
 * @date 2023-01-07- 22:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = {RabbitConfig.class, HelloReceiver.class, HelloSender.class})
public class RabbitMqHelloTest {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void hello() throws Exception {
        helloSender.send();
    }

}
