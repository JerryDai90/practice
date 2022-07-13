package com.practice.juc.lock;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于redis做的分段扣减逻辑，
 * <p>
 * 业务场景：
 * 用户每天可以领用积分，积分每次领用一个，一天可以领用3次
 * 积分总池是 5000W
 */
public class RedisCellLock4integral {


    //lua脚本只能用于当前实例，跨实例就无法解决了。

    //分桶
    static int bucketLength = 10;
    //实现分段的锁
    static volatile Map<String, ReentrantLock> locks = new ConcurrentHashMap<String, ReentrantLock>();

    //分段的库存
    static volatile Map<String, Integer> inventorys = new ConcurrentHashMap<String, Integer>();

    //总的量
    static int maxCount = 10000000;

    static int pool = 100;

    static LongAdder longAdder = new LongAdder();

//    static {
//
//
//    }

    static void init(){
        for (int i = 0; i < bucketLength; i++) {
            locks.put("iPool_lock_" + i, new ReentrantLock());
        }

        for (int i = 0; i < bucketLength; i++) {
            inventorys.put("iPool_" + i, new Integer(maxCount / bucketLength));
        }
    }

    //这里其实可以基于redis DECR 命令来，
    //当前目前采用自己实现的方案。
    public boolean getIntegral(String userId) {

        int randomBucket = randomBucket();

        lock("iPool_lock_" + randomBucket);

        int currentInventory = getInventory("iPool_" + randomBucket);
        //如果为0，则需要遍历其他桶
        if (currentInventory == 0) {

//            System.out.println("不够库存："+randomBucket);

            //解锁掉，因为已经没有了库存可用。这个桶，其实可以通过配置中心下发此桶已经没有库存了，移除这个桶的取模
            unlock("iPool_lock_" + randomBucket);

            for (int i = 0; i < bucketLength; i++) {
                //如果是当前桶，则跳过
                if (randomBucket == i) {
                    continue;
                }

                //获取下一个桶的锁
                lock("iPool_lock_" + i);
                try {
                    //获取下一个桶的数据
                    int tempInventory = getInventory("iPool_" + i);
                    if (tempInventory == 0) {
                        continue;
                    }
                    //扣减积分
                    update("iPool_" + i, tempInventory - 1);
                    return true;

                } finally {
                    unlock("iPool_lock_" + i);
                }

            }
        } else {
            //如果不为0，则说明有库存

            //扣减积分
            update("iPool_" + randomBucket, currentInventory - 1);

            //解锁掉，因为已经没有了库存可用。这个桶，其实可以通过配置中心下发此桶已经没有库存了，移除这个桶的取模
            unlock("iPool_lock_" + randomBucket);
        }

        return false;
    }


    public int randomBucket() {
        return new Random().nextInt(bucketLength);
    }


    public void lock(String lockName) {
        //lock 的时候需要设置有效期，避免死锁，而且需要设置相关加锁机器相关信息。

        //TODO 但是还需要解决还没处理完成就已经解锁。 导致数据出现问题。
        ReentrantLock reentrantLock = locks.get(lockName);
        reentrantLock.lock();
    }

    public void unlock(String lockName) {
        //解锁需要增加机器信息
        ReentrantLock reentrantLock = locks.get(lockName);
        reentrantLock.unlock();
    }


    public int getInventory(String pool) {
        return inventorys.get(pool);
    }

    public int update(String pool, int value) {

//        Integer v = inventorys.get(pool);
//        int i = v - value;
        return inventorys.put(pool, value);
    }


    public static void main(String[] args) throws InterruptedException {

        if (args != null && args.length != 0){
            bucketLength = Integer.valueOf(args[0]);
            maxCount = Integer.valueOf(args[1]);
            pool = Integer.valueOf(args[2]);
        }

        init();

        long l = System.currentTimeMillis();

        RedisCellLock4integral redisCellLock = new RedisCellLock4integral();

        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(pool);

        for (int i = 0; i < maxCount; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    redisCellLock.getIntegral(null);
                    longAdder.increment();
                }
            });
        }

        while (longAdder.intValue() != maxCount) {
            Thread.sleep(100);
        }



        System.out.println(inventorys);
        System.out.println(System.currentTimeMillis() - l);

        executorService.shutdown();
//        System.exit();


    }


}