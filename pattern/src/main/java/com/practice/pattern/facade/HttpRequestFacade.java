package com.practice.pattern.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequestFacade {


    public static void main(String[] args) {

        new ExportData().export();

    }


}


/**
 * 导出用户数据
 */
class ExportData {


    /**
     * 通过外观模式，把导出的这个动作整理起来直接返回
     *
     * @return
     */
    public Map<String, List<String>> export() {

        List<String> users = new User().getUsers();

        List<String> deps = new Dep().getDep();

        return null;
    }

}


class User {

    public List<String> getUsers() {
        return new ArrayList<>();
    }

}

class Dep {

    public List<String> getDep() {
        return new ArrayList<>();
    }

}
