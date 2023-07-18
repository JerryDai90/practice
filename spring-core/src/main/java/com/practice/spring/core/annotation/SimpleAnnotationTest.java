package com.practice.spring.core.annotation;

import com.practice.spring.core.annotation.bean.A1;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author jerry dai
 * @date 2023-06-09- 09:44
 */
public class SimpleAnnotationTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.practice.spring.core.annotation");

        A1 bean = (A1) context.getBean("a1");

        System.out.println(bean);

    }

}
