package com.practice.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductDemo {


    public static void main(String[] args) {

        //构建商场
        Store store = new Store();

        //构建客户
        Customer customer = new Customer();

        //增加观察者
        store.addObserver(customer);

        //更新产品
        store.updateProduct("macbook m1");
    }
}


class Store {

    private List<ProductObserver> observers = new ArrayList<>();

    public void addObserver(ProductObserver observer) {
        observers.add(observer);
    }

    public void updateProduct(String name) {
        Product product = new Product(name);

        observers.forEach(observer -> {
            observer.onUpdate(product);
        });
    }
}

interface ProductObserver {
    void onUpdate(Product product);
}


class Customer implements ProductObserver {
    @Override
    public void onUpdate(Product product) {
        System.out.println("商品更新了，通知了我：" + product.name);
    }
}


class Product {
    String name;

    public Product(String name) {
        this.name = name;
    }
}

