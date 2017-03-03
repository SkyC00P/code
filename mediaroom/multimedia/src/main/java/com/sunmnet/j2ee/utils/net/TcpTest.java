package com.sunmnet.j2ee.utils.net;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * TcpTest
 *
 * @author : skyco
 * @date : 2017/3/2
 */
public class TcpTest {


    public static void main(String[] args) throws Exception {
        TcpTest tcpTest = new TcpTest();
        FileOutputStream file = new FileOutputStream("d:\\test.txt");
        tcpTest.write(file);
        tcpTest.write(file);
        Thread.sleep(3000);
        file.close();
        tcpTest.write(file);
    }

    public void write(OutputStream output) {
        BufferedOutputStream out = new BufferedOutputStream(output);
        try {
            out.write("fuck_you".getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

