package com.practive.jvm;

//模拟大对象直接进入老年代

/**

 -XX:NewSize=10485760
 -XX:MaxNewSize=10485760
 -XX:InitialHeapSize=20971520
 -XX:MaxHeapSize=20971520
 -XX:SurvivorRatio=8
 -XX:MaxTenuringThreshold=15
 -XX:PretenureSizeThreshold=5485760
 -XX:+UseParNewGC
 -XX:+UseConcMarkSweepGC
 -XX:+PrintGCDetails
 -XX:+PrintGCTimeStamps
 -Xloggc:gc.log

 *
 *
 */
public class ParamsTest4EntryOldGeneration {

    public static void main(String[] args) throws InterruptedException {

        byte[] array4 = new byte[(7 * 1024 * 1024)-20];

    }
}

