package com.practice.juc.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionModel {

    //使用Condition来实现wait/notify

    static ReentrantLock reentrantLock = new ReentrantLock();

    static Condition condition = reentrantLock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    reentrantLock.lock();

                    try {
                        System.out.println(System.currentTimeMillis()+": 准备等待条件，休眠");
                        condition.await();
                        System.out.println(System.currentTimeMillis()+": 准备等待条件，唤醒");

//                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } finally {
                    reentrantLock.unlock();
                }
            }
        });

        thread.start();

        Thread.sleep(3000);
        try{
            reentrantLock.lock();

            System.out.println(System.currentTimeMillis()+": 准备通知");
            condition.signal();
            System.out.println(System.currentTimeMillis()+": 通知完成");

        }finally {
            reentrantLock.unlock();
        }
    }


}
