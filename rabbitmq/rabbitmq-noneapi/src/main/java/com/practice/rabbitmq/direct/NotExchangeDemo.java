package com.practice.rabbitmq.direct;

import com.practice.rabbitmq.Utils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Direct Exchange（直连模型） 模式
 * @author jerry dai
 * @date 2023-01-09- 10:07
 */
public class NotExchangeDemo {

    final static String single_queue = "single_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = Utils.getConn();

        Channel channel = connection.createChannel();

        //必须要先定义队列，无论是接受还是发送
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(single_queue, true, false, false, null);

        channel.basicPublish("", single_queue, null, "test".getBytes());

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[x] Received '" + message + "'");

                //确认
                channel.basicAck(envelope.getDeliveryTag(), true);
            }
        };

        channel.basicConsume(single_queue, consumer);
    }


}
