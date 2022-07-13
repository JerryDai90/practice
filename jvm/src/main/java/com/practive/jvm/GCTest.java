package com.practive.jvm;

public class GCTest {

    public static void main(String[] args) throws InterruptedException {

//        byte[] array1 = new byte[2 *1024 * 1024];
//
//        array1 = new byte[2 *1024 * 1024];
//        array1 = new byte[2 *1024 * 1024];
//
//        array1 = null;
//
//        byte[] array2 = new byte[128 * 1024];
//
//        byte[] array3 = new byte[2 * 1024 * 1024];
//
        Thread.sleep(1000 * 20);

        while (true){
            xx();
        }

    }


    public static void xx() throws InterruptedException {
        byte[] array1 = new byte[2 *1024 * 1024];
        Thread.sleep(1000);
    }
}
