package com.practice.pattern.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

//组合模式
public class DeleteOrgTree {

    public static void main(String[] args) {
        Org p1 = new Org("p1");
        Org p2 = new Org("p2");
//        Org p3 = new Org("p3");
//        Org p4 = new Org("p4");


        Org p11 = new Org("p11");

        Org p21 = new Org("p21");
//        Org p31 = new Org("p31");
//        Org p41 = new Org("p41");


        Org p111 = new Org("p111");
        Org p112 = new Org("p112");
        Org p211 = new Org("p211");
//        Org p311 = new Org("p311");
//        Org p411 = new Org("p411");


        p1.addChild(p11);
        p11.addChild(p111);
        p11.addChild(p112);


        p2.addChild(p21);
        p21.addChild(p211);


        System.out.println(p1);
        p1.remove();
        System.out.println(p1);
    }


    public static class Org implements AbsOrg {

        List<Org> childs = new ArrayList<>();

        String name;

        public Org(String name) {
            this.name = name;
        }

        public void addChild(Org org) {
            this.childs.add(org);
        }

        @Override
        public void remove() {
            for (Org org : childs) {
                org.remove();
            }
            childs.clear();
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Org.class.getSimpleName() + "[", "]")
                    .add("childs=" + childs)
                    .add("name='" + name + "'")
                    .toString();
        }
    }

    public interface AbsOrg {
        void remove();
    }

}
