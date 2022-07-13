package com.practice.juc.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {


        CountDownLatch countDownLatch = new CountDownLatch(2);



        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getName()+"开始执行");
                try {
                    Thread.sleep(1000);
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"结束执行");
            }
        },"t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getName()+"开始执行");
                try {
                    Thread.sleep(2000);
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"结束执行");
            }
        },"t2").start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getName()+"开始执行>>>await");
                try {
                    Thread.sleep(1000);
                    countDownLatch.await();
//                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"结束执行>>>await>>"+System.currentTimeMillis());
            }
        },"t3").start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getName()+"开始执行>>>await>>");
                try {
                    countDownLatch.await();
//                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"结束执行>>>"+System.currentTimeMillis());
            }
        },"t4").start();


        System.out.println("000000000");
        countDownLatch.await();
        System.out.println("1111111111>>>"+System.currentTimeMillis());

    }



}
