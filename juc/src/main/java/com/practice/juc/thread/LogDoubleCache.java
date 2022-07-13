package com.practice.juc.thread;

import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 基于双缓存模式来记录日志。动态切换存储
 */
public class LogDoubleCache {

    static int totalData = 10000000;

    //验证相关类
    static volatile CountDownLatch proveTotal = new CountDownLatch(totalData);
    static volatile Map<String, String> verifyRepeat = new ConcurrentHashMap<>();


    //是否正在同步刷盘
    volatile boolean isSync = false;

    static Timer timer = new Timer();

    private LogDoubleCache() {
        //异步每3秒钟写入一次磁盘。
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flash();
            }
        }, 1000, 1000);
    }

    private Log log = new Log();

    public void write(String content) {

        synchronized (this) {
            log.write2cache(content);
        }
    }


    private void flash() {
        synchronized (this) {
            //方式1：通过 isSync 来判断是否正在处理，等待处理完成后此线程再继续
            //增加标记位 isSync 是为了解决没处理完成刷盘（flash）动作时候通过 wait 释放锁就不会影响到 插入的锁获取。
            while (isSync) {
                try {
                    System.out.println(Thread.currentThread().getName() + ": wait");
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //方式2：如果发现有正在处理，就直接返回释放锁
//            if( isSync ){
//                System.out.println(Thread.currentThread().getName() + ": return");
//                return;
//            }

            isSync = true;
            log.readyFlashDisk();
        }
        System.out.println(Thread.currentThread().getName() + ": execute");
        //挪动到外面是因为真正写日志逻辑很慢，需要释放锁后，插入才不阻塞。
        log.flash();

        //再次加锁修改标识符
        synchronized (this) {
            isSync = false;
        }
    }


    class Log {

        //前台线程写入的缓冲区
        LinkedList<String> currentBuffer = new LinkedList<>();

        //异步写日志缓冲区
        LinkedList<String> syncBuffer = new LinkedList<>();


        public void write2cache(String content) {
            currentBuffer.add(content);
        }

        //交互指针，
        public void readyFlashDisk() {
            //先清空刷盘缓冲
            syncBuffer.clear();

            //备份当前缓冲区的内存地址（引用）
            LinkedList<String> temp = this.currentBuffer;

            //把当前缓冲区的内存地址改到刷盘的内存地址
            currentBuffer = syncBuffer;

            //拿到之前备份的缓冲区地址
            syncBuffer = temp;
        }


        public void flash() {
            for (String str : syncBuffer) {
                //验证
                if (verifyRepeat.putIfAbsent(str, "1") != null) {
                    //如果有重复执行的，会报错
                    throw new RuntimeException("find repeat key: " + str);
                }
//                System.out.println(str);
            }
            //休眠来模拟写入速度
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        final LogDoubleCache ldc = new LogDoubleCache();

        //测试并发下刷新磁盘问题
        ExecutorService testFlash = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            testFlash.execute(new Runnable() {
                @Override
                public void run() {
                    ldc.flash();
                }
            });
        }

        //模拟并发写入日志
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(200);
        for (int i = 0; i < totalData; i++) {
            executorService.execute(new TestRunnable(i, ldc));
        }

        //等待所有线程完成
        proveTotal.await();
//
//        System.out.println(executorService.getActiveCount());
//
//        System.out.println(executorService.isTerminated());
//        System.out.println("CompletedTaskCount: " + executorService.getCompletedTaskCount());
//        executorService.shutdown();

//        executorService.shutdown();
//        System.out.println("线程池关闭");
//        timer.cancel();

//        System.exit(1);

        System.out.println("数据处理完成");

    }

    static class TestRunnable implements Runnable {
        int i;
        LogDoubleCache ldc;

        public TestRunnable(int i, LogDoubleCache ldc) {
            this.i = i;
            this.ldc = ldc;
        }

        @Override
        public void run() {
            ldc.write("" + i);
            //验证个数
            proveTotal.countDown();
        }
    }

}
