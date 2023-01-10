package com.practice.rabbitmq.topic;

import com.practice.rabbitmq.Utils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Topic Exchange，主题交换机，其实可以说是通配符交换机
 * @author jerry dai
 * @date 2023-01-09- 19:19
 */
public class TopicDemo {


    final static String topic_ex = "topic_ex";
    final static String topic_queue1 = "topic_queue1";
    final static String topic_queue2 = "topic_queue2";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = Utils.getConn();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(topic_ex, "topic");

        //定义队列1
        channel.queueDeclare(topic_queue1, true, false, false, null);
        channel.queueBind(topic_queue1, topic_ex, "topic.*");

        //定义队列2
        channel.queueDeclare(topic_queue2, true, false, false, null);
        channel.queueBind(topic_queue2, topic_ex, "topic.a");

        //会发送到2个队列中去
        channel.basicPublish( topic_ex, "topic.a", null, "topic message test".getBytes());

        channel.close();
        connection.close();
    }


}
