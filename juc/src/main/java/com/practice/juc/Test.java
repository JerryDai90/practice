package com.practice.juc;

import java.util.concurrent.locks.ReentrantLock;

public class Test {

    static final int SHARED_SHIFT   = 16;
    static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
    static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

    public static void main(String[] args) {

//        ReentrantLock reentrantLock = new ReentrantLock(true);
//
//        reentrantLock.lock();


        System.out.println(SHARED_UNIT);

        System.out.println(MAX_COUNT);

        System.out.println(EXCLUSIVE_MASK);


        System.out.println(2 & EXCLUSIVE_MASK);

        System.out.println(1 >> SHARED_UNIT);

    }

}
