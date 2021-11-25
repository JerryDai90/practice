package com.practice.pattern.chainofresponsibility;

import java.util.ArrayList;
import java.util.List;


/**
 * 拦截器
 * <p>
 * 每个拦截器都经过
 */
public class InterceptorDemo {

    public static void main(String[] args) {

        InterceptorBuilder builder = new InterceptorBuilder();
        builder.add(new UrlInterceptor()).add(new ParamInterceptor()).add(new AuthInterceptor());

        builder.presses(new HttpRequest("我是一个对象"));
    }
}

class InterceptorBuilder {

    List<Interceptor> interceptors = new ArrayList<>();

    public InterceptorBuilder add(Interceptor interceptor) {
        interceptors.add(interceptor);
        return this;
    }

    public void presses(HttpRequest request) {
        for (Interceptor interceptor : interceptors) {
            interceptor.handle(request);
        }
        System.out.println("处理完成。。。");
    }
}

interface Interceptor {
    void handle(HttpRequest request);
}

class AuthInterceptor implements Interceptor {

    @Override
    public void handle(HttpRequest request) {
        System.out.println("AuthInterceptor 我处理了");
    }
}

class UrlInterceptor implements Interceptor {

    @Override
    public void handle(HttpRequest request) {
        System.out.println("UrlInterceptor 我处理了");
    }
}


class ParamInterceptor implements Interceptor {

    @Override
    public void handle(HttpRequest request) {
        System.out.println("ParamInterceptor 我处理了");
    }
}


