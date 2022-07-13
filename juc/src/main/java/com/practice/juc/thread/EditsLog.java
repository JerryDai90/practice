package com.practice.juc.thread;


import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 实现高性能的刷盘逻辑，所有的写请求都需要真实写入磁盘后才返回。
 *
 * 实现流图图  https://kdocs.cn/l/csf9wLIvBr47
 *
 */
public class EditsLog {

    volatile Object lock = new Object();
    volatile boolean flushRunning = false;

    //自增id
    long maxTxId = 0;

    //刷入磁盘中的最大事物id
    long maxTxIdOnDist = 0;
    DoubleBufferCache bufferCache = new DoubleBufferCache();
    ThreadLocal<Long> cacheTxId = new ThreadLocal<>();

    volatile boolean isWaitSync = false;

    public static void main(String[] args) throws InterruptedException {

        EditsLog log = new EditsLog();
        long l = System.currentTimeMillis();

        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(500);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //刷入磁盘才返回
                        log.writeForMustWriteToDisk(UUID.randomUUID().toString());

                        //如果有人已经刷入磁盘了，就直接返回
//                        log.writeForQuick(UUID.randomUUID().toString());
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        while (executorService.getActiveCount() != 0) {
            Thread.sleep(10);
        }
        System.out.println(System.currentTimeMillis()-l);
        executorService.shutdown();
    }


    /**
     * 调用写入的时候必须等落地到磁盘后才返回
     * @param context
     * @throws InterruptedException
     */
    public void writeForMustWriteToDisk(String context) throws InterruptedException {

        synchronized (lock) {
            //txId 写入的是一定有顺序的
            Node node = new Node(maxTxId, context);
            bufferCache.write(node);

            cacheTxId.set(maxTxId);
            maxTxId++;
        }

        synchronized (lock) {

            if (flushRunning) {
                Long threadTxId = cacheTxId.get();
                //刷入磁盘的最大值如果大于当前值，就证明已经被刷入磁盘了。（因为这里是有序的，必然可以做到）
                if (maxTxIdOnDist >= threadTxId) {
                    return;
                }

                while (flushRunning) {
                    //休眠后，就一定还要判断是否有线程在跑，如果还在跑就继续休眠
                    lock.wait(100);
                }

                if (maxTxIdOnDist >= threadTxId) {
                    return;
                }
            }
            //交互数据开始刷盘
            flushRunning = true;
            bufferCache.readyToFlush();
        }
        //开始刷盘，到这里的线程只能有一个，
        bufferCache.flush();

        synchronized (lock) {
            flushRunning = false;
            //完成后同时所有线程
            lock.notifyAll();
        }
    }


    /**
     * 调用写入的时候如果有线程帮忙写入磁盘时就立即返回。让其他线程进行写入。然后返回（可能返回的时候还没写入磁盘）
     * @param context
     * @throws InterruptedException
     */
    public void writeForQuick(String context) throws InterruptedException {

        synchronized (lock) {
            //txId 写入的是一定有顺序的
            Node node = new Node(maxTxId, context);
            bufferCache.write(node);

            cacheTxId.set(maxTxId);
            maxTxId++;
        }

        synchronized (lock) {

            if (flushRunning) {
                Long threadTxId = cacheTxId.get();
                //刷入磁盘的最大值如果大于当前值，就证明已经被刷入磁盘了。（因为这里是有序的，必然可以做到）
                if (maxTxIdOnDist >= threadTxId) {
                    return;
                }
                //这里做了限制后，直接就让正在等待的线程帮当前线程序列号到磁盘去（但是如果这个线程没跑完的话，数据还是会丢失）
                if( isWaitSync ){
                    return;
                }
                isWaitSync = true;
                while (flushRunning) {
                    //休眠后，就一定还要判断是否有线程在跑，如果还在跑就继续休眠
                    lock.wait(100);
                }
                isWaitSync = false;

                if (maxTxIdOnDist >= threadTxId) {
                    return;
                }
            }
            //交互数据开始刷盘
            flushRunning = true;
            bufferCache.readyToFlush();
        }
        //开始刷盘，到这里的线程只能有一个，
        bufferCache.flush();

        synchronized (lock) {
            flushRunning = false;
            //完成后同时所有线程
            lock.notifyAll();
        }
    }


    class DoubleBufferCache {

        //一直接受的数据
        volatile LinkedList<Node> data = new LinkedList<>();

        //刷盘数据
        volatile LinkedList<Node> syncData = new LinkedList<>();

        void write(Node node) {
            data.add(node);
        }

        //刷盘
        public void flush() throws InterruptedException {
            if (syncData.isEmpty()) {
                return;
            }

            for (Node node : syncData) {
                Thread.sleep(1);
                //真正落盘的时候
                maxTxIdOnDist = node.getTxId();
            }
            syncData.clear();
        }

        //刷盘
        public void readyToFlush() {
            LinkedList<Node> temp = data;

            data = syncData;

            syncData = temp;
        }

    }


    class Node {
        long txId;
        String value;

        public Node(long txId, String value) {
            this.txId = txId;
            this.value = value;
        }

        public long getTxId() {
            return txId;
        }

        public void setTxId(long txId) {
            this.txId = txId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
