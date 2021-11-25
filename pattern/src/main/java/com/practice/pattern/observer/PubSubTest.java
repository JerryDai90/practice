package com.practice.pattern.observer;

import java.util.Observable;
import java.util.Observer;

public class PubSubTest {

    public static void main(String[] args) {

        Publish pub = new Publish();
        Subject sub = new Subject();

        //订阅消息
        pub.addObserver(sub);

        //变更，即会通知 sub
        pub.setValue("11111");
    }
}

class Subject implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("接收到更新通知：" + o.toString());
    }
}


class Publish extends Observable {

    String value;

    public void setValue(String _value) {
        System.out.println("更改值");
        this.value = _value;

        this.setChanged();
        this.notifyObservers();
    }
}
