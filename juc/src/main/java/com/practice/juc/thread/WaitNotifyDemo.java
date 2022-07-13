package com.practice.juc.thread;

/**
 * 测试 notify 和 wait 之间的逻辑，
 * 证明 调用notify后，需要等待锁释放后wait才会回调继续执行。
 */
public class WaitNotifyDemo {

    static volatile boolean flag = true;

    static Object lock = new Object();

    public static void log(String str) {
        System.out.println(Thread.currentThread().getName() + ": " + System.currentTimeMillis() + " >> " + str);

    }

    public static void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Thread(new WaitThread(), "Wait").start();

        new Thread(new NotifyThread(), "Notify").start();
    }


    public static class WaitThread implements Runnable {

        @Override
        public void run() {
            log("");


            synchronized (lock) {
                while (flag) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            log("拿到锁且状态标志位准确，执行业务，flag = " + flag);
        }
    }

    public static class NotifyThread implements Runnable {


        @Override
        public void run() {
            log("");

            log("锁外面休眠开始");
            sleep(2000);
            log("锁外面休眠结束");

            synchronized (lock) {
                lock.notifyAll();
                flag = false;
                log("notify 休眠");
                sleep(4000);
                log("notify 休眠后");
            }
            log("退出锁, 准备再次休眠");
            sleep(2000);
            log("再次休眠结束");

            synchronized (lock) {
                log("再次拿到锁");
            }

        }
    }


}
