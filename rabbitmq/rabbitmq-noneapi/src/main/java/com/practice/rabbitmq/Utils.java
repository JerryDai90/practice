package com.practice.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jerry dai
 * @date 2023-01-09- 14:28
 */
public class Utils {

    public static Connection getConn() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPassword("guest");
        factory.setUsername("guest");
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        return connection;
    }

}
