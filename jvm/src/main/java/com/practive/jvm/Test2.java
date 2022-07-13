package com.practive.jvm;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class Test2 {

    public static void main(String[] args) throws InterruptedException, IOException {

//        final CommandLine cmdLine = new CommandLine("/Users/jerry/Mix/temp/date.sh");
//        cmdLine.addArgument("1  s");
//        cmdLine.addArgument("2");
//        DefaultExecutor executor = new DefaultExecutor();
//        int exitValue = executor.execute(cmdLine);


        FileReader fileReader = new FileReader("/Users/jerry/Private/993_KINTH/1_code/1_kinth_qd/kinth-qd-javafx/kinth-qd-terminal-swing/target/live.log");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = null;

//        FaceFactory.getFace().deleteAll();
        int i = 0;

        while ((line = bufferedReader.readLine()) != null) {

            byte[] decode = Base64.getDecoder().decode(line);
            FileUtil.writeBytes(decode, "/Users/jerry/Mix/temp/"+ i+++".jpg");

        }
    }
}
