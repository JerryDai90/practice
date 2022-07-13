package com.practice.juc.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask submit = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(5000);
                System.out.println(1111111);
                return "werwrwr";
            }
        });

        submit.run();


        while(true){
            if(submit.isDone() && !submit.isCancelled()){

                System.out.println(1111+">>>>"+submit.get());
//                break;
            }
        }






//
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//
//        Future<Object> submit = executorService.submit(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                Thread.sleep(2000);
//                return "sdfsfss";
//            }
//        });
//
//        while(true){
//            if(submit.isDone() && !submit.isCancelled()){
//
//                System.out.println(1111+">>>>"+submit.get());
//                break;
//            }
//        }






    }

}
