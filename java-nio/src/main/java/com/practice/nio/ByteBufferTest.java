package com.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ByteBufferTest {

    public static void main(String[] args) throws Exception {


//        ByteBuffer allocate = ByteBuffer.allocate(10);
//
//        allocate.position();
//
////        allocate.flip()
//
//        allocate.put("1".getBytes());
//
//        System.out.println(allocate.position());

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocketChannel socketChannel = serverSocketChannel.bind(new InetSocketAddress(8999));
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        selector.select();

                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();

                        while (iterator.hasNext()){

                            SelectionKey selectionKey = iterator.next();
                            iterator.remove();

                            if( selectionKey.isAcceptable() ){
                                SocketChannel accept = serverSocketChannel.accept();
                                accept.configureBlocking(false);
                                accept.register(selector, SelectionKey.OP_READ);
                                System.out.println("isAcceptable");
                            }else if( selectionKey.isReadable()){
//                                System.out.println(selectionKey.isAcceptable());
                                System.out.println("isReadable");

                                try {
                                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(200);


                                    int read = channel.read(byteBuffer);
                                    while ( read > 0 ){
                                        byteBuffer.flip();
                                        System.out.println(new String(byteBuffer.array()));
                                        read = channel.read(byteBuffer);
                                    }
                                    ByteBuffer wbb = ByteBuffer.allocate(100);
                                    wbb.put("1111111111111111".getBytes());
                                    channel.write(wbb);
//                                    channel.close();


                                }catch (Exception e){

                                }




                            }

                        }



                    }


                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();


    }

}
