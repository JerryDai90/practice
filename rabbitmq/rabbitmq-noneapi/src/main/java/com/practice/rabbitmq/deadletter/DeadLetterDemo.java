package com.practice.rabbitmq.deadletter;

import com.practice.rabbitmq.Utils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列
 * @author jerry dai
 * @date 2023-01-10- 15:03
 */
public class DeadLetterDemo {

    final static String business_queue = "business_queue";
    final static String business_queue_ex = "business_queue_ex";
    final static String business_queue_routing_key = "business_queue_routing_key";

    final static String business_dead_letter_queue = "business_dead_letter_queue";
    final static String business_dead_letter_ex = "business_dead_letter_ex";
    final static String business_dead_letter_routing_key = "business_dead_letter_routing_key";


    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = Utils.getConn();
        Channel channel = connection.createChannel();


        //死信队列定义---------------------------------------------------------------
        channel.exchangeDeclare(business_dead_letter_ex, "direct");
        channel.queueDeclare(business_dead_letter_queue, true, false, false, null);
        channel.queueBind(business_dead_letter_queue, business_dead_letter_ex, business_dead_letter_routing_key);



        //业务队列定义---------------------------------------------------------------
        channel.exchangeDeclare(business_queue_ex, "direct");

        //设置业务队列被拒绝后回到的指定 死信exchange
        Map<String, Object> queueArgs = new HashMap<String, Object>();
        queueArgs.put("x-dead-letter-exchange", business_dead_letter_ex);
        //这里指定了 死信的路由建，无非就是消费不了的，就往这个 exchange 和指定的 routing_key 发送
        queueArgs.put("x-dead-letter-routing-key", business_dead_letter_routing_key);
        channel.queueDeclare(business_queue, true, false, false, queueArgs);
        channel.queueBind(business_queue, business_queue_ex, business_queue_routing_key);


        //业务队列消息接受---------------------------------------------------------------
        Consumer bsConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[Reject] '" + message + "'");
                //确认
                channel.basicNack(envelope.getDeliveryTag(), false, false);
            }
        };
        channel.basicConsume(business_queue, bsConsumer);

        //死信队列消息接受---------------------------------------------------------------
        Consumer dlConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[dead letter] '" + message + "'");
                //确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(business_dead_letter_queue, dlConsumer);

        //给业务队列发送消息---------------------------------------------------------------

        channel.basicPublish(business_queue_ex, business_queue_routing_key, null, ("test: "+System.currentTimeMillis()+"").getBytes());


    }


}
