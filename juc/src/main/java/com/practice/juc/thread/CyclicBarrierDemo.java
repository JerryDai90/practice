package com.practice.juc.thread;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    public static void main(String[] args) {


        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {

                System.out.println("所有线程都已经完成。。。");

            }


        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getId()+"开始执行");
                try {
                    Thread.sleep(1000);
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId()+"结束执行");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getId()+"开始执行");
                try {
                    Thread.sleep(2000);
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId()+"结束执行");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getId()+"开始执行");
                try {
                    Thread.sleep(1000);
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId()+"结束执行");
            }
        }).start();


    }

}
