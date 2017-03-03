package com.sunmnet.mediaroom.serialport;

import com.sunmnet.mediaroom.serialport.utils.LogManager;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        LogManager.info(Test.class, "执行测试" + Arrays.toString(args));
    }

}
