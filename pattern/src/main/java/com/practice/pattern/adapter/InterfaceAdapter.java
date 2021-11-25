package com.practice.pattern.adapter;

public class InterfaceAdapter {

    public static void main(String[] args) {

        new Adapter().exec();

    }
}


class OldInterface {

    public void exec() {
        System.out.println("我是旧接口");
    }
}


class Adapter {

    public void exec() {
        //这里进行字段值转换
        System.out.println("适配器转换业务逻辑, 不修改原来的接口");

        //转换完成后调用旧接口
        new OldInterface().exec();

        System.out.println("再次转换调用认的数据结构");
    }


}

