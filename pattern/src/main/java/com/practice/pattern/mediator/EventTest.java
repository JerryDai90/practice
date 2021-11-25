package com.practice.pattern.mediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventTest {

    public static void main(String[] args) {

        //初始化事件处理器
        LoginEventHandle loginEventHandle = new LoginEventHandle();

        //触发事件
        new LoginService().login("admin");
    }
}

class LoginService {

    public void login(String username) {
        System.out.println("调用登陆: " + username);
        EventMediator.notice("login", username);
    }
}


class LoginEventHandle implements Event {

    public LoginEventHandle() {
        EventMediator.registry("login", this);
    }

    @Override
    public void handle(Object object) {
        System.out.println("LoginEventHandle： 处理事件");
    }
}


class EventMediator {

    static Map<String, List<Event>> cache = new HashMap<>();

    public static void registry(String key, Event event) {
        List<Event> events = cache.get(key);
        if (null == events) {
            events = new ArrayList<>();
            cache.put(key, events);
        }
        events.add(event);
    }

    public static void notice(String key, Object object) {

        List<Event> events = cache.get(key);
        if (null == events) {
            System.out.println("没找到事件处理器");
            return;
        }
        events.forEach(e -> {
            e.handle(object);
        });
    }

}


interface Event {

    void handle(Object object);

}

