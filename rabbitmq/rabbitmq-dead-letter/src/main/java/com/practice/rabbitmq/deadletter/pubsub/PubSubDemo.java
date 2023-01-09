package com.practice.rabbitmq.deadletter.pubsub;

import com.practice.rabbitmq.deadletter.Utils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Fanout Exchange，发布订阅模式
 * @author jerry dai
 * @date 2023-01-09- 10:44
 */
public class PubSubDemo {

    final static String pub_sub_ex = "pub_sub_ex";

    final static String pub_sub_queue1 = "pub_sub_queue1";

    final static String pub_sub_queue2 = "pub_sub_queue2";


    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = Utils.getConn();

        Channel channel = connection.createChannel();

        //发布订阅ex
        channel.exchangeDeclare(pub_sub_ex, "fanout");

        //定义队列
        channel.queueDeclare(pub_sub_queue1, true, false, false, null);
        channel.queueDeclare(pub_sub_queue2, true, false, false, null);

        //队列绑定交换机，routingKey 是没用的，绑定到了交换机的都会自动发送，不走 routingKey
        channel.queueBind(pub_sub_queue1, pub_sub_ex, "");
        channel.queueBind(pub_sub_queue2, pub_sub_ex, "");

        channel.basicPublish( pub_sub_ex, "", null, "fanout message test".getBytes());

        //接收数据

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[x] Received '" + message + "'" +envelope.getExchange());
                //确认
                channel.basicAck(envelope.getDeliveryTag(), true);
            }
        };

        channel.basicConsume(pub_sub_queue1, consumer);
        channel.basicConsume(pub_sub_queue2, consumer);
    }


}
