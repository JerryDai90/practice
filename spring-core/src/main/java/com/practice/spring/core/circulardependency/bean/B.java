package com.practice.spring.core.circulardependency.bean;

/**
 * @author jerry dai
 * @date 2023-06-07- 17:29
 */
public class B {

    public A a;

    public void setA(A a) {
        this.a = a;
    }
}
