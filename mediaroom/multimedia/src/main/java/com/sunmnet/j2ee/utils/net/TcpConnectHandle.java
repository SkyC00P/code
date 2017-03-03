package com.sunmnet.j2ee.utils.net;

/**
 * TcpConnectHandle
 *
 * @author : skyco
 * @date : 2017/3/2
 */
public interface TcpConnectHandle {

    /** 处理Tcp连接 **/
    void handleTcpConn(TcpConnect conn);

    /** 断开连接 **/
    void disconnect(TcpConnect conn);

    /** 发送数据 **/
    boolean sendData(TcpConnect conn, byte[] data);
}
