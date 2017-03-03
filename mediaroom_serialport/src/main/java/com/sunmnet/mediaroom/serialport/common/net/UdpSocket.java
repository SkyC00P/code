package com.sunmnet.mediaroom.serialport.common.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UdpSocket 提供统一的UDP传输协议接口
 *
 * @author : skyco
 * @date : 2017/2/15
 */
public class UdpSocket {

    // Java 提供的Udp实现接口
    DatagramSocket socket;

    ///////////////////////////////////////////////////////////////////////////
    // UdpSocket 构造方法

    public UdpSocket() throws java.net.SocketException {
        socket = new DatagramSocket();
    }

    public UdpSocket(DatagramSocket sock) {
        socket = sock;
    }

    /**
     * 创建监听本地指定端口的UdpSocket
     */
    public UdpSocket(int port) throws java.net.SocketException {
        socket = new DatagramSocket(port);
    }

    /**
     * 创建指定IP地址跟端口的UdpSocket
     */
    public UdpSocket(int port, InetAddress laddr) throws java.net.SocketException {
        socket = new DatagramSocket(port, laddr);
    }
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 关闭连接
     */
    public void close() {
        socket.close();
    }

    /**
     * 获取该socket绑定的IP地址
     */
    public InetAddress getLocalAddress() {
        return socket.getInetAddress();
    }

    /**
     * 获取该socket绑定的端口
     */
    public int getLocalPort() {
        return socket.getLocalPort();
    }

    /**
     * 获取SoTimeout时间
     */
    public int getSoTimeout() throws java.net.SocketException {
        return socket.getSoTimeout();
    }

    /**
     * 开启/关闭 SoTimeout 选项，以 milliseconds 计
     */
    public void setSoTimeout(int timeout) throws java.net.SocketException {
        socket.setSoTimeout(timeout);
    }

    /**
     * 接收 Udp 数据包
     */
    public void receive(UdpPacket pkt) throws java.io.IOException {
        DatagramPacket dgram = pkt.getDatagramPacket();
        socket.receive(dgram);
        pkt.setDatagramPacket(dgram);
    }

    /**
     * 发送 Udp 数据包
     */
    public void send(UdpPacket pkt) throws java.io.IOException {
        socket.send(pkt.getDatagramPacket());
    }

    public String toString() {
        return socket.toString();
    }
}
