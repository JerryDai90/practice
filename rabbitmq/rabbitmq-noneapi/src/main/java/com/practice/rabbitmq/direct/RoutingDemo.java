package com.practice.rabbitmq.direct;

import com.practice.rabbitmq.Utils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jerry dai
 * @date 2023-01-09- 11:38
 */
public class RoutingDemo {

    final static String direct_routing_info = "direct_routing_info";
    final static String direct_routing_error = "direct_routing_error";

    final static String EXCHANGE_NAME = "direct_routing_ex";

    final static String infoRoutingKey = "info";

    final static String errorRoutingKey = "error";


    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = Utils.getConn();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        //定义队列
        channel.queueDeclare(direct_routing_info, true, false, false, null);
        //直连交换机，绑定特定 routingkey,只有完全匹配了才接收到
        channel.queueBind(direct_routing_info, EXCHANGE_NAME, infoRoutingKey);

        //定义队列
        channel.queueDeclare(direct_routing_error, true, false, false, null);
        //直连交换机，绑定特定 routingkey,只有完全匹配了才接收到
        channel.queueBind(direct_routing_error, EXCHANGE_NAME, errorRoutingKey);


        //只会发送到 direct_routing_info 队列
        channel.basicPublish(EXCHANGE_NAME, infoRoutingKey, null, "info test".getBytes());

        //只会发送到 direct_routing_error 队列
        channel.basicPublish(EXCHANGE_NAME, errorRoutingKey, null, "error test".getBytes());

        channel.close();
        connection.close();
    }

}
