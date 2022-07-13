package com.practice.juc.thread;

public class TheadTest {


    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long l = 0;
                while (!Thread.currentThread().isInterrupted()){
                    l++;
                }

                System.out.println(System.currentTimeMillis()+">"+1111);
            }
        });

        thread.start();


        Thread.sleep(5000);

        System.out.println(System.currentTimeMillis()+"发起终止");
        thread.interrupt();
        System.out.println(System.currentTimeMillis()+"发起完成");
        System.out.println(1112221);

    }

}
