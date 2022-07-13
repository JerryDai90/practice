package com.practive.jvm;

import java.util.ArrayList;
import java.util.List;

public class StringOutOfMemory {

    public static void main(String[] args) {

        List<String> xxx = new ArrayList<>();

        int i = 0;

        while (true){


            String xdff = String.valueOf(i++);

            xxx.add(xdff);

        }

    }


}
