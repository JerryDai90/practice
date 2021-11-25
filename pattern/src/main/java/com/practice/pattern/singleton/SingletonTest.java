package com.practice.pattern.singleton;

public class SingletonTest {

    public static void main(String[] args) {

    }
}

/***
 * 方法一，使用私有化的构造函数来做到控制外部生成实例
 *
 * 但是存在可以使用反射的方式来破坏掉单例。
 *
 */
class Singleton1 {

    private static final Singleton1 instance = new Singleton1();

    private Singleton1() {
    }

    //private构造方法保证外部无法实例化，但是还是可以通过反射构建
    public static Singleton1 getInstance() {
        return instance;
    }


}


/***
 * 方法2，
 *
 *
 *
 */
class Singleton2 {

    //使用 volatile防止指令重拍导致单例失效
    private volatile static Singleton2 instance;

    private Singleton2() {
        if (null == instance) {
            synchronized (Singleton2.class) {
                if (null == instance) {
                    instance = new Singleton2();
                }
            }
        }
    }

    public static Singleton2 getInstance() {
        return instance;
    }


}
