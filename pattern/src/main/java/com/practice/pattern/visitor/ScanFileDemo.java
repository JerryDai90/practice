package com.practice.pattern.visitor;

import java.io.File;

public class ScanFileDemo {


    public static void main(String[] args) {

        JavaFileScanVisitor javaVisitor = new JavaFileScanVisitor();
        ClassFileScanVisitor classVisitor = new ClassFileScanVisitor();

        ScanFile scanFile = new ScanFile();
        scanFile.scan(new File("/Users/jerry/Private/999_self/1_code/practice/pattern"), javaVisitor);

        scanFile.scan(new File("/Users/jerry/Private/999_self/1_code/practice/pattern"), classVisitor);

    }

}


interface ScanVisitor {

    void isFile(File file);

    void isDir(File file);
}


class JavaFileScanVisitor implements ScanVisitor {

    @Override
    public void isFile(File file) {
        if (file.getName().indexOf(".java") != -1) {
            System.out.println(file.getName());
        }
    }

    @Override
    public void isDir(File file) {

    }
}

class ClassFileScanVisitor implements ScanVisitor {

    @Override
    public void isFile(File file) {
        if (file.getName().indexOf(".class") != -1) {
            System.out.println(file.getName());
        }
    }

    @Override
    public void isDir(File file) {

    }
}


class ScanFile {


    void scan(File basePath, ScanVisitor visitor) {

        if (basePath.isFile()) {
            visitor.isFile(basePath);
        }

        File[] files = basePath.listFiles();
        if (null != files) {
            for (File child : basePath.listFiles()) {
                scan(child, visitor);
            }
        }

        if (basePath.isDirectory()) {
            visitor.isDir(basePath);
        }
    }

}
