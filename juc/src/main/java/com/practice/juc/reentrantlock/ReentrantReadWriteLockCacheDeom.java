package com.practice.juc.reentrantlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockCacheDeom {

    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    static Map<String, String> cache = new HashMap<>();



    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            new Thread(new UpdateCache()).start();
        }

        for (int i = 0; i < 20; i++) {
            new Thread(new GetCache()).start();
        }

    }



    public static class UpdateCache implements Runnable{
        @Override
        public void run() {
            while (true){

//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                readWriteLock.writeLock().lock();
                try{
                    cache.put("time", System.currentTimeMillis()+"");
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }finally {
                    readWriteLock.writeLock().unlock();
                }
            }
        }
    }

    public static class GetCache implements Runnable{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                readWriteLock.readLock().lock();
                try{
                    String time = cache.get("time");
                    System.out.println(time);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    readWriteLock.readLock().unlock();
                }


            }
        }
    }



}
