package com.practice.juc.thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpServerDemo {


    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9900);

        ExecutorService executorService = Executors.newCachedThreadPool();

        Socket socket = null;
        //接收请求
        while ((socket = serverSocket.accept()) != null) {
            executorService.execute(new HttpRequestHandler(socket));
        }
    }


    static class HttpRequestHandler implements Runnable {
        Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String s = bufferedReader.readLine();
                System.out.println(s);

                socket.getOutputStream().write("hello word".getBytes("UTF-8"));
                socket.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}

