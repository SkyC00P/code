package com.sunmnet.mediaroom.serialport.common.net;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * UdpPacket 提供统一的UDP数据包接口
 *
 * @author : skyco
 * @date : 2017/2/15
 */
public class UdpPacket {

    /**
     * Java 底层UDP数据包实现
     */
    DatagramPacket packet;

    ///////////////////////////////////////////////////////////////////////////
    // UdpPacket 构造方法

    public UdpPacket(DatagramPacket packet) {
        this.packet = packet;
    }

    /**
     * 接收专用数据包
     * @param buf
     * @param length
     */
    public UdpPacket(byte[] buf, int length) {
        packet = new DatagramPacket(buf, length);
    }

    /**
     * 发送专用 Udp 数据包
     * @param buf
     * @param length
     * @param ipaddr
     * @param port
     */
    public UdpPacket(byte[] buf, int length, InetAddress ipaddr, int port) {
        packet = new DatagramPacket(buf, length, ipaddr, port);
    }

    /**
     * 接收专用数据包
     * @param buf
     * @param offset
     * @param length
     */
    public UdpPacket(byte[] buf, int offset, int length) {
        packet = new DatagramPacket(buf, offset, length);
    }

    /**
     * 发送专用 Udp 数据包
     * @param buf
     * @param offset
     * @param length
     * @param ipaddr
     * @param port
     */
    public UdpPacket(byte[] buf, int offset, int length, InetAddress ipaddr, int port) {
        packet = new DatagramPacket(buf, offset, length, ipaddr, port);
    }
    ///////////////////////////////////////////////////////////////////////////

    DatagramPacket getDatagramPacket() {
        return packet;
    }

    void setDatagramPacket(DatagramPacket packet) {
        this.packet = packet;
    }

    /**
     * 返回该数据报文发送的目标机器的Ip地址或是接收的目标机器的Ip地址
     */
    public InetAddress getIpAddress() {
        return packet.getAddress();
    }

    /**
     * 返回接收或发送的数据
     */
    public byte[] getData() {
        return packet.getData();
    }

    /**
     * 返回接收或发送的数据的长度
     */
    public int getLength() {
        return packet.getLength();
    }

    /**
     * 数据报中数据开始的点
     */
    public int getOffset() {
        return packet.getOffset();
    }

    /**
     * 返回接收或发送的数据报文的端口
     */
    public int getPort() {
        return packet.getPort();
    }

    /**
     * 设置数据报文往哪个Ip地址发送
     */
    public void setIpAddress(InetAddress ipaddr) {
        packet.setAddress(ipaddr);
    }

    /**
     * 设置发送的数据
     * @param bytes
     */
    public void setData(byte[] bytes) {
        packet.setData(bytes);
    }

    /**
     * 发送字节数组里指定长度的数据
     */
    public void setData(byte[] buf, int offset, int length) {
        packet.setData(buf, offset, length);
    }

    /**
     * 设置数据报文的长度
     */
    public void setLength(int length) {
        packet.setLength(length);
    }

    /**
     * 设置接收或发送的数据报文的端口
     */
    public void setPort(int iport) {
        packet.setPort(iport);
    }
}
