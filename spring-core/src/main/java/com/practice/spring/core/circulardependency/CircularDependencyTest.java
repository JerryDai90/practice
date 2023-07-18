package com.practice.spring.core.circulardependency;

import com.practice.spring.core.TestBean;
import com.practice.spring.core.circulardependency.bean.A;
import com.practice.spring.core.circulardependency.bean.B;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author jerry dai
 * @date 2023-06-07- 17:16
 */
public class CircularDependencyTest {

    public static void main(String[] args) throws URISyntaxException {

        Resource resource = new ClassPathResource("/com/practice/spring/core/circulardependency/applicationContext-cd.xml");
        DefaultListableBeanFactory registry = new DefaultListableBeanFactory();

        //可以注册一个bean对象初始化成功后的动作。
        registry.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//                if(bean instanceof B)
//                    ((B)bean).setA(null);
                return null;
            }
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }
        });

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);

        reader.loadBeanDefinitions(resource);

        B bean = (B) registry.getBean("b");

        System.out.println(bean.a);
    }

}
