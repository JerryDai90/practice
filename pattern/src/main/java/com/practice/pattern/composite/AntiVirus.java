package com.practice.pattern.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class AntiVirus {


    public static void main(String[] args) {
        Folder f1 = new Folder("我是文件夹f1");
        Folder f11 = new Folder("我是文件夹f11");
        Folder f12 = new Folder("我是文件夹f12");

        f1.files.add(f11);
        f1.files.add(f12);


        PDF p1 = new PDF("我是PDF  p1");
        PDF p2 = new PDF("我是PDF  p2");
        PDF p3 = new PDF("我是PDF  p3");
        f11.files.add(p1);
        f11.files.add(p2);
        f11.files.add(p3);

        Image i1 = new Image("我是Image i1");
        f12.files.add(i1);

        System.out.println(f1);
        f1.scan();

    }


    public static class Folder implements AbsAntVirus {

        String name;
        List<AbsAntVirus> files = new ArrayList<>();

        public Folder(String name) {
            this.name = name;
        }

        @Override
        public void scan() {
            System.out.println("Folder:" + name);
            for (AbsAntVirus _f : files) {
                _f.scan();
            }
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Folder.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("files=" + files)
                    .toString();
        }
    }


    public static class PDF implements AbsAntVirus {

        String name;

        public PDF(String name) {
            this.name = name;
        }

        @Override
        public void scan() {
            System.out.println("PDF:" + name);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", PDF.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .toString();
        }
    }


    public static class Image implements AbsAntVirus {

        String name;

        public Image(String name) {
            this.name = name;
        }

        @Override
        public void scan() {
            System.out.println("图片扫描:" + name);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Image.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .toString();
        }
    }


    public interface AbsAntVirus {
        void scan();
    }
}
