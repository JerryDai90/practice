package com.practice.spring.core.transaction;

import com.practice.spring.core.transaction.bean.IUserService;
import com.practice.spring.core.transaction.bean.UserServiceImpl;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 手动创建事务代理对象案例
 * @author jerry dai
 * @date 2023-07-11- 09:49
 */
public class TransactionTest {

    public static void main(String[] args) {

        Resource resource = new ClassPathResource(
                "/com/practice/spring/core/transaction/applicationContext-transaction.xml");
        //DefaultListableBeanFactory这个只有对元素的解析，bean对象的管理作用，是没有AOP的能力的。
        DefaultListableBeanFactory registry = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        reader.loadBeanDefinitions(resource);


        IUserService datasource = (IUserService)registry.getBean("userServiceProxy");

        datasource.delete();

        System.out.println(datasource);

    }

}
