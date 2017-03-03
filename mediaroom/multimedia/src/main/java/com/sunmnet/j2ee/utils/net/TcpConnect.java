package com.sunmnet.j2ee.utils.net;

import com.sunmnet.j2ee.entity.vo.TaskName;

import java.io.*;
import java.net.Socket;

/**
 * TcpConnect
 * Tcp连接基础类
 *
 * @author : skyco
 * @date : 2017/3/2
 */
public class TcpConnect extends Thread {

    /**
     * 维护的Socket
     **/
    private Socket socket;

    /**
     * 长连接还是短连接
     **/
    private boolean keepAlive = false;

    /**
     * 当前连接读写权限
     **/
    private int right = Right.RW;

    /**
     * 权限枚举值
     **/
    public static class Right {
        public static final int R = 1;
        public static final int W = 2;
        public static final int RW = 3;
    }

    /**
     * 当前维护的socket是否已经建立连接
     **/
    private boolean isConnect;

    /**
     * 当前连接的输入流
     **/
    private InputStream input;
    /**
     * 当前连接的输出流
     **/
    private OutputStream output;

    /**
     * 当前连接的处理器
     **/
    private TcpConnectHandle handle;

    /**
     * 是否在运行
     */
    private boolean isRunning;

    private static int index = 0;

    ///////////////////////////////////////////////////////////////////////////
    // 构造器
    //-------------------------------------------------------------------------
    public TcpConnect(Socket socket, TcpConnectHandle handle) throws Exception {
        init(socket, handle);
    }

    /**
     * 返回短连接，可读写的Tcp连接
     */
    private void init(Socket socket, TcpConnectHandle handle) throws Exception {
        if (socket == null || socket.isClosed())
            throw new Exception("IllegalArgument for socket,Maybe the socket is null or closed");
        this.socket = socket;
        isConnect = socket.isConnected();
        if (isConnect) {
            input = new BufferedInputStream(socket.getInputStream());
            output = new BufferedOutputStream(socket.getOutputStream());
        }
        this.handle = handle;
        setName(TaskName.AIRING_CONN + "-" + index++);
    }
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 关闭连接
     **/
    public void disconnect() {
        if (handle != null)
            handle.disconnect(this);
        else
            try {
                socket.close();
            } catch (IOException e) {

            }
    }

    /**
     * 发送数据
     */
    public boolean sendData(byte[] data) {
        if (handle != null)
            return handle.sendData(this, data);
        return false;
    }

    /**
     * 是否具有发送的权限
     */
    public boolean checkSend() {
        if (socket == null || socket.isClosed() || !socket.isConnected() || socket.isOutputShutdown() || right == Right.R)
            return false;
        return true;
    }

    @Override
    public void run() {
        handle.handleTcpConn(this);
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public OutputStream getOutput() {
        return output;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public InputStream getInput() {
        return input;
    }

    public Socket getSocket() {
        return socket;
    }

    public TcpConnectHandle getHandle() {
        return handle;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
