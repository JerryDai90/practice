package com.practice.pattern.chainofresponsibility;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * 通用责任链模式，
 * <p>
 * 一般是找到一个可以使用的就返回
 */
public class CommonDemo {

    public static void main(String[] args) {
        Chain chain = new Chain();

        chain.addHandler(new GreaterThan0());
        chain.addHandler(new LessThan0());

        chain.process(new Request(1f));
        chain.process(new Request(-1f));
        chain.process(new Request(0f));

    }
}


class Chain {

    private List<Handler> handlers = new ArrayList<>();

    public void addHandler(Handler _handler) {
        handlers.add(_handler);
    }

    public void process(Request request) {
        for (Handler handler : handlers) {
            Boolean process = handler.process(request);
            if (process) {
                return;
            }
        }
        throw new RuntimeException("未找到处理器处理:" + request);
    }
}


interface Handler {

    Boolean process(Request request);
}

class Request {

    float money;

    public Request(float money) {
        this.money = money;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Request.class.getSimpleName() + "[", "]")
                .add("money=" + money)
                .toString();
    }
}

class LessThan0 implements Handler {
    @Override
    public Boolean process(Request request) {
        if (request.money < 0) {

            System.out.println("我处理了： " + request);
            return true;
        }
        return false;
    }
}

class GreaterThan0 implements Handler {
    @Override
    public Boolean process(Request request) {
        if (request.money > 0) {
            System.out.println("我处理了： " + request);
            return true;
        }
        return false;
    }
}