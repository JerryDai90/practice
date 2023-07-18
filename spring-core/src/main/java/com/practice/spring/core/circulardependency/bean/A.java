package com.practice.spring.core.circulardependency.bean;

/**
 * @author jerry dai
 * @date 2023-06-07- 17:29
 */
public class A {

    public B b;

    public void setB(B b) {
        this.b = b;
    }
}
