package com.practice.pattern.factory;

public class MsgDemo {

    public static void main(String[] args) {

        MsgFactory factory = WechatFactory.getFactory();
        factory.getMsg().send();

        MsgFactory factory1 = SMSFactory.getFactory();
        factory1.getMsg().send();

    }
}


interface IMsg {
    void send();
}


interface MsgFactory {
    IMsg getMsg();
}

class Wechat implements IMsg {
    @Override
    public void send() {
        System.out.println("使用微信发送消息");
    }
}

class WechatFactory implements MsgFactory {
    @Override
    public IMsg getMsg() {
        return new Wechat();
    }

    public static MsgFactory getFactory() {
        return new WechatFactory();
    }
}


class SMSFactory implements MsgFactory {
    @Override
    public IMsg getMsg() {
        return new SMS();
    }

    public static MsgFactory getFactory() {
        return new SMSFactory();
    }
}


class SMS implements IMsg {
    @Override
    public void send() {
        System.out.println("使用短信发送消息");
    }
}
