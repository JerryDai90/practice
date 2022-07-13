package com.practice.juc.aqs;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    static Semaphore semaphore = new Semaphore(2);


    public static void main(String[] args) {


        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+": 开始");

            try {
                semaphore.acquire();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(Thread.currentThread().getName()+": 休眠结束");
            semaphore.release();
            System.out.println(Thread.currentThread().getName()+": 释放");

        },"t1").start();



        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+": 开始");

            try {
                semaphore.acquire();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(Thread.currentThread().getName()+": 休眠结束");
            semaphore.release();
            System.out.println(Thread.currentThread().getName()+": 释放");

        },"t2").start();


        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+": 开始");

            try {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+": 准备获取锁");
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+": 已获得");
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            semaphore.release();
            System.out.println(Thread.currentThread().getName()+": 释放");

        },"t3").start();



    }


}
