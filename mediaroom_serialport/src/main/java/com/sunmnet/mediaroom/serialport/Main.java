package com.sunmnet.mediaroom.serialport;

import com.sunmnet.mediaroom.serialport.bean.SerialPortCMD;
import com.sunmnet.mediaroom.serialport.bean.SerialPortMSG;
import com.sunmnet.mediaroom.serialport.bean.SerialPortMSGInterface;
import com.sunmnet.mediaroom.serialport.common.Exception.UDPCreateException;
import com.sunmnet.mediaroom.serialport.common.Exception.UDPSendException;
import com.sunmnet.mediaroom.serialport.common.net.UdpProvider;
import com.sunmnet.mediaroom.serialport.utils.LogManager;
import com.sunmnet.mediaroom.serialport.utils.SerialPortHandle;
import com.sunmnet.mediaroom.serialport.utils.SerialPortMessageFactory;

public class Main implements SerialPortMSGInterface {

    private static UdpProvider provider;
    public static void main(String[] args) {

        try {
            LogManager.setIsDebug(true);
            provider = SerialPortMessageFactory.createProvider(new SerialPortHandle() {
                @Override
                public void handle(SerialPortCMD spCMD, UdpProvider provider) {
                    System.out.println(spCMD);
                }

                @Override
                public void handle(SerialPortMSG spMSG, UdpProvider provider) {
                    String str = new String(spMSG.getData());
                    System.out.println(spMSG + "\n" + str);
                    if ("Stop".equals(str)){
                        System.out.println(" == ");
                        provider.halt();
                    }
                }
            });

            provider.send(SerialPortMessageFactory.createUdpPacket(SU_TASK_TYPE.PA, SU_TASK_TYPE.APP, SU_MSG_TYPE.DATA, "Stop".getBytes()));

//            testSend();
        } catch (UDPCreateException e) {
            e.printStackTrace();
        } catch (UDPSendException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (SerialPortMessageFactory.provider != null){
                    SerialPortMessageFactory.provider.halt();
                }
            }
        });
    }
    private static void testSend() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {

                        Thread.sleep(5000);
                        // 开投影仪电源
                        byte[] data = SerialPortMessageFactory.initData(SerialPortMSGInterface.SU_TASK_TYPE.PA, SerialPortMSGInterface.SU_TASK_TYPE.APP, SerialPortMSGInterface.SU_MSG_TYPE.DATA, "U1130014003A07090909010909010AU".getBytes());
                        provider.send(SerialPortMessageFactory.createUdpPacket(data));

                        Thread.sleep(5000);

                        // 关投影仪电源
                        data = SerialPortMessageFactory.initData(SerialPortMSGInterface.SU_TASK_TYPE.PA, SerialPortMSGInterface.SU_TASK_TYPE.APP, SerialPortMSGInterface.SU_MSG_TYPE.DATA, "U1130014003A070909090009090109U".getBytes());
                        provider.send(SerialPortMessageFactory.createUdpPacket(data));

                    } catch (UDPSendException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
