package com.practice.spring.core.transaction;

import com.practice.spring.core.transaction.bean.IUserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 基于AOP事务的方式来处理事务
 * @author jerry dai
 * @date 2023-07-11- 09:49
 */
public class AOPTransactionTest {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext(
                "classpath:com/practice/spring/core/transaction/applicationContext-transaction2.xml");

        //配置了单例，所以2次获取的对象都是同一个
        IUserService datasource = applicationContext.getBean(IUserService.class);
        datasource.delete();

        IUserService datasource2 = applicationContext.getBean(IUserService.class);

        System.out.println(datasource);
        System.out.println(datasource2);

    }

}
