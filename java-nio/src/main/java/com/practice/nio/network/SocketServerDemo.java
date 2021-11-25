package com.practice.nio.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketServerDemo {

    //    static LinkedBlockingQueue<SelectionKey> queue = new LinkedBlockingQueue();
    static ExecutorService executorService = Executors.newFixedThreadPool(1);


    public static void main(String[] args) throws Exception {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocketChannel socketChannel = serverSocketChannel.bind(new InetSocketAddress(8999));
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    SocketChannel accept = serverSocketChannel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ);
                    System.out.println("isAcceptable");
                } else if (selectionKey.isReadable()) {
                    System.out.println("isReadable");
                    handle(selectionKey);
//                    executorService.execute(new Worker(selectionKey));
//                    new Thread(new Worker(selectionKey)).start();
                }
            }
        }
    }

    public static class Worker implements Runnable {
        SelectionKey selectionKey;

        public Worker(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    handle(selectionKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static void handle(SelectionKey selectionKey) throws IOException {

//                                System.out.println(selectionKey.isAcceptable());
        System.out.println("isReadable");

        try {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            channel.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocate(200);

            int read = channel.read(byteBuffer);
            while (read > 0) {
                byteBuffer.flip();
                System.out.println(new String(byteBuffer.array()));
                read = channel.read(byteBuffer);
            }
            ByteBuffer wbb = ByteBuffer.allocate(100);
            wbb.put("1111111111111111".getBytes());
            channel.write(wbb);
            channel.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}
