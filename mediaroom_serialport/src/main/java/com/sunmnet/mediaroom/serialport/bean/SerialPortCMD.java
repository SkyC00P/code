package com.sunmnet.mediaroom.serialport.bean;

import java.io.Serializable;

/**
 * SerialPortCMD
 * 串口交互消息体定义
 *
 * @author : skyco
 * @date : 2017/2/15
 */
public class SerialPortCMD extends SerialPortMSG implements Serializable {

    /**
     * 8位命令类型
     **/
    private int cmd_type_u8;

    /**
     * 8位命令总长度
     **/
    private int len_8;

    public int getCmd_type_u8() {
        return cmd_type_u8;
    }

    public void setCmd_type_u8(int cmd_type_u8) {
        this.cmd_type_u8 = cmd_type_u8;
    }

    public int getLen_8() {
        return len_8;
    }

    public void setLen_8(int len_8) {
        this.len_8 = len_8;
    }

    @Override
    public String toString() {
        return "\nSerialPortCMD{" +
                "cmd_type_u8=" + cmd_type_u8 +
                ", len_8=" + len_8 +
                "} " + super.toString() + "\n";
    }
}
