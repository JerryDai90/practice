package com.practice.juc.threadpool;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单实现线程池
 */
public class FixedThreadPool {

    //当前核心线程数
    AtomicInteger coreThread = new AtomicInteger(0);

    BlockingQueue<Worker> coreThreadWorker = new LinkedBlockingQueue<Worker>();

    //最大线程数
    int maxThread;

    //保持线程数
    int keepThread;

    //基于阻塞队列来实现，可以休眠保持核心数以及快速获取到任务
    BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();


    public FixedThreadPool(int maxThread, int keepThread) {
        this.maxThread = maxThread;
        this.keepThread = keepThread;
    }

    public void execute(Runnable runnable) {

        //如果核心线程数大于设置的线程，则入队列
        if (coreThread.get() >= maxThread) {
            boolean offer = taskQueue.offer(runnable);
        } else {
            while (true) {
                int ctNum = coreThread.get();
                if (ctNum >= maxThread) {
                    //入队了
                    boolean offer = taskQueue.offer(runnable);
                    return;
                } else {
                    Worker worker = new Worker(runnable);
                    if (coreThread.compareAndSet(ctNum, ctNum + 1)) {
                        //立即创建一个新的线程
                        coreThreadWorker.offer(worker);
                        worker.start();
                        return;
                    }
                }
            }
        }
    }


    private class Worker implements Runnable {

        Thread thread;

        Runnable runnable;

        public Worker(Runnable runnable) {
            this.runnable = runnable;
        }

        public void start() {
            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ">>>>>>启动线程");
            while (true) {
                try {
                    if (null != runnable) {
                        runnable.run();
                        runnable = null;
                    } else {
                        Runnable take = taskQueue.take();
                        take.run();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + ">>>>>>销毁线程");
        }
    }


    public static void main(String[] args) throws InterruptedException {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        FixedThreadPool pool = new FixedThreadPool(5, 5);

        for (int i = 0; i < 10; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String string = atomicInteger.incrementAndGet() + "";

                        System.out.println(Thread.currentThread().getName() + " ------ 1111开始-" + string);
                        Thread.sleep(1000 * 1l);
                        System.out.println(Thread.currentThread().getName() + " -------------------------- 2222结束-" + string);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}
