package com.sunmnet.mediaroom.serialport.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * SerialPortMSG
 * 串口协议对象定义
 * @author : skyco
 * @date : 2017/2/15
 */
public class SerialPortMSG implements Serializable {

    /** 8位消息发送的目标模块 **/
    private int dest_task_type_u8;

    /** 8位发送消息的模块 **/
    private int src_task_type_u8;

    /** 8位消息类型 **/
    private int msg_type_u8;

    /** 8位预留字段 **/
    private int app_data_u8;

    /** 16位总长度(字节数) **/
    private int len_u16;

    /** 16位序列号，消息可靠传输使用 **/
    private int seqno_u16;

    /** 携带的数据 **/
    private byte[] data;

    public int getApp_data_u8() {
        return app_data_u8;
    }

    public void setApp_data_u8(int app_data_u8) {
        this.app_data_u8 = app_data_u8;
    }

    public int getDest_task_type_u8() {
        return dest_task_type_u8;
    }

    public void setDest_task_type_u8(int dest_task_type_u8) {
        this.dest_task_type_u8 = dest_task_type_u8;
    }

    public int getLen_u16() {
        return len_u16;
    }

    public void setLen_u16(int len_u16) {
        this.len_u16 = len_u16;
    }

    public int getMsg_type_u8() {
        return msg_type_u8;
    }

    public void setMsg_type_u8(int msg_type_u8) {
        this.msg_type_u8 = msg_type_u8;
    }

    public int getSeqno_u16() {
        return seqno_u16;
    }

    public void setSeqno_u16(int seqno_u16) {
        this.seqno_u16 = seqno_u16;
    }

    public int getSrc_task_type_u8() {
        return src_task_type_u8;
    }

    public void setSrc_task_type_u8(int src_task_type_u8) {
        this.src_task_type_u8 = src_task_type_u8;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "\nSerialPortMSG{" +
                "app_data_u8=" + app_data_u8 +
                ", dest_task_type_u8=" + dest_task_type_u8 +
                ", src_task_type_u8=" + src_task_type_u8 +
                ", msg_type_u8=" + msg_type_u8 +
                ", len_u16=" + len_u16 +
                ", seqno_u16=" + seqno_u16 +
                ", data=" + Arrays.toString(data) +
                '}' + "\n";
    }
}
