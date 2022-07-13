package com.practice.lang.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Test extends ArrayList{

    public static void main(String[] args) {

        Test list = new Test();
        list.add("123");
        list.add("345");

        list.forEach2(System.out::println);

        System.out.println("------------------------");

        System.out.println("------------------------");

        list.forEach(list::ooo);


        System.out.println("---------=======---------------");

        list.stream().filter(a -> a instanceof String).skip(1).limit(10).forEach(System.out::println);


        System.out.println("---------=======---------------222");

        list.stream().map(a->a.toString()).forEach(System.out::println);
    }

    private void ooo(Object o) {

    }


    void forEach2(TestFun testFun){

        this.forEach(a->{
            testFun.apt(a);
        });

//        testFun.apt(this);
    }



}
