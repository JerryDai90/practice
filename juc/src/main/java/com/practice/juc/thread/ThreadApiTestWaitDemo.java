package com.practice.juc.thread;

import java.util.ArrayList;
import java.util.UUID;

public class ThreadApiTestWaitDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadApiTestWaitDemo testWait = new ThreadApiTestWaitDemo();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        Object take = testWait.take();
                        System.out.println(Thread.currentThread().getName() + ": " + take);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "1").start();



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        Object take = testWait.take();
                        System.out.println(Thread.currentThread().getName() + ": " + take);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "2").start();

//        testWait.put("");

        Thread.sleep(1000 *5);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {

                        testWait.put(UUID.randomUUID().toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "2").start();


    }


    ArrayList queue = new ArrayList(2);


    public synchronized Object put(Object o) throws InterruptedException {
        while (queue.size() == 2) {
            System.out.println(Thread.currentThread().getName() + ": put into wait");
            wait();
        }
        queue.add(o);
        notifyAll();
        return o;
    }


    public synchronized Object take() throws InterruptedException {
        while (queue.size() == 0) {
            System.out.println(Thread.currentThread().getName() + ": take wait");
            wait();
        }
        Object o = queue.get(queue.size() - 1);
        queue.remove(queue.size() - 1);
        notifyAll();
        return o;
    }

}
