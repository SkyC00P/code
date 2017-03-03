package com.sunmnet.mediaroom.serialport.common.net;

import com.sunmnet.mediaroom.serialport.common.Exception.UDPCreateException;
import com.sunmnet.mediaroom.serialport.common.Exception.UDPInterruptException;
import com.sunmnet.mediaroom.serialport.common.Exception.UDPSendException;
import com.sunmnet.mediaroom.serialport.utils.LogManager;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.util.Arrays;

/**
 * UdpProvider <p>
 * 提供 发送/接受 UDP 数据包的外部接口。
 * 阻塞监听指定端口是否有数据过来，如果一旦发现接收到UDP数据报文，调用相应的 UdpProviderListener 处理该数据包
 * <p/>
 * 如果 <code>alive_time<code/> 大于零，则 UdpProvider 会在 <code>alive_time<code/> 秒无接收到任何数据后触发onServiceInterrupted（UdpProvider, Exception）方法
 * <p/>
 * 有新的数据包到达，会触发 <code>onReceivedPacket(UdpProvider,DatagramPacket<code/> 方法
 * <p/>
 * 停止接收数据包，会触发 <code>onServiceTerminated(UdpProvider)</code> 方法
 * <p/>
 *
 * @author : skyco
 * @date : 2017/2/15
 */
public class UdpProvider extends Thread {

    /**
     * 读取UDP数据包的缓存区域大小
     **/
    private static final int BUFFER_SIZE = 65535;

    /**
     * UDP socket
     **/
    private UdpSocket socket;

    /**
     * socket等待消息阻塞时间
     **/
    private long alive_time;

    /**
     * 接收的数据报文的最小长度，低于该值的数据报文全部舍弃
     **/
    private int minimum_length;

    /**
     * 是否停止
     **/
    private boolean stoped = true;

    /**
     * 是否允许发送Udp数据包
     */
    private boolean sendable;

    /**
     * 是否允许循环发包
     */
    private boolean loopSendable;

    /**
     * UdpProvider 监听器
     **/
    private UdpProviderListener listener;

    // 线程标记
    private static int index = 0;

    // 循环发送包的工作线程
    private LoopSendThread loopSendThread;

    ///////////////////////////////////////////////////////////////////////////
    // UdpProvider 构造方法

    /**
     * 构造无超时限制，无限制定时5s发送的UdpProvider
     */
    public UdpProvider(UdpSocket socket, UdpProviderListener listener) {
        super("[UdpProvider - " + index++ + "]");
        init(socket, 0, listener, new LoopSendThread(this, true, 5, false));
        LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + " create suc");
    }

    public UdpProvider(UdpSocket socket, long alive_time, UdpProviderListener listener, LoopSendThread loopSendThread) {
        super("[UdpProvider - " + index++ + "]");
        init(socket, alive_time, listener, loopSendThread);
        LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + " create suc");
    }

    public UdpProvider(int port, long alive_time, UdpProviderListener listener, LoopSendThread loopSendThread) throws UDPCreateException {
        super("[UdpProvider - " + index++ + "]");
        UdpSocket socket = null;
        try {
            socket = new UdpSocket(port);
        } catch (SocketException e) {
            throw new UDPCreateException("Error : Can't create the udpSocket", e);
        }
        init(socket, alive_time, listener, loopSendThread);
        LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + " create suc");
    }

    public UdpProvider(UdpSocket socket, long alive_time, UdpProviderListener listener) {
        super("[UdpProvider - " + index++ + "]");
        init(socket, alive_time, listener, new LoopSendThread(this, true, 5, false));
        LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + " create suc");
    }

    private void init(UdpSocket socket, long alive_time, UdpProviderListener listener, LoopSendThread loopSendThread) {
        this.listener = listener;
        this.socket = socket;
        this.alive_time = alive_time;
        this.minimum_length = 0;
        this.stoped = false;
        this.sendable = true;
        this.loopSendable = true;
        this.loopSendThread = loopSendThread;
    }
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 受控制的发送 UdpPacket方法
     */
    public boolean send(UdpPacket packet) throws UDPSendException {
        if (!stoped && sendable) {
            LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + " 发出的包数据：" + Arrays.toString(packet.getData()));
            try {
                socket.send(packet);
            } catch (IOException e) {
                String errorMsg = packet == null ? "packet is null" : packet.getIpAddress() + " | " + packet.getPort() + " | " + Arrays.toString(packet.getData());
                throw new UDPSendException("send packet error " + errorMsg, e);
            }
            return true;
        }
        return false;
    }

    /**
     * 忽略 sendable 的控制直接发包
     *
     * @param packet
     * @throws IOException
     */
    public void sendIgnore(UdpPacket packet) throws UDPSendException {
        if (!stoped) {
            LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + " 发出的包数据：" + Arrays.toString(packet.getData()));
            try {
                socket.send(packet);
            } catch (IOException e) {
                String errorMsg = packet == null ? "packet is null" : packet.getIpAddress() + " | " + packet.getPort() + " | " + Arrays.toString(packet.getData());
                throw new UDPSendException("send packet error " + errorMsg, e);
            }
        }
    }

    /**
     * 停止运行，释放资源
     */
    public void halt() {
        LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + "停止运行，释放资源");
        stoped = true;
        sendable = false;
        loopSendable = false;
        loopSendThread.halt();
        socket.close(); // modified
    }

    /**
     * 由外部触发UDP中断事件
     */
    public void InterruptSign() {
        LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + "外部触发UDP中断事件");
        if (listener != null)
            listener.onServiceInterrupted(this, new UDPInterruptException());
    }

    /**
     * 接收Udp数据报文的主要处理逻辑
     */
    @Override
    public void run() {

        LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + " start listen");

        // 定义接收Udp数据报文的UDP包
        byte[] buf = new byte[BUFFER_SIZE];
        UdpPacket packet = new UdpPacket(buf, buf.length);

        // 定义心跳维持时间区间
        long expire = 0;
        if (alive_time > 0)
            expire = System.currentTimeMillis() + alive_time;

        Exception error = null;
        try {
            // 设置超时时间
            socket.setSoTimeout((int) alive_time);
            // loop
            stoped = false;
            while (!stoped) {
                try {
                    // 开始在 alive_time 时间内监听 Udp 数据包
                    socket.receive(packet);
                } catch (InterruptedIOException ie) {
                    // 如果超时，立即处理
                    if (alive_time > 0 && System.currentTimeMillis() > expire)
                        if (listener != null)
                            listener.onServiceInterrupted(this, ie);
                    continue;
                }
                if (packet.getLength() >= minimum_length) {
                    if (listener != null)
                        // 分发数据包
                        listener.onReceivedPacket(this, packet);
                    if (alive_time > 0)
                        // 刷新心跳区间
                        expire = System.currentTimeMillis() + alive_time;
                }
                // 刷新 udp 包重新接收
                packet = new UdpPacket(buf, buf.length);
            }
        } catch (Exception e) {
            error = e;
            stoped = true;
        }

        LogManager.info(UdpProvider.class, Thread.currentThread().getName() + " - " + getName() + " Loop exit");
        // Loop 退出，线程结束，调用onServiceTerminated回收资源
        if (listener != null)
            listener.onServiceTerminated(this, error);
        listener = null;
    }

    /**
     * 设置接收的数据报文的最小长度，低于该值的数据报文全部舍弃
     */
    public void setMinimumReceivedDataLength(int len) {
        minimum_length = len;
    }

    /**
     * 返回接收的数据报文的最小长度，低于该值的数据报文全部舍弃
     */
    public int getMinimumReceivedDataLength() {
        return minimum_length;
    }

    public static int getBufferSize() {
        return BUFFER_SIZE;
    }

    public long getAlive_time() {
        return alive_time;
    }

    public void setAlive_time(long alive_time) {
        this.alive_time = alive_time;
    }

    public boolean isLoopSendable() {
        return loopSendable;
    }

    public void setLoopSendable(boolean loopSendable) {
        this.loopSendable = loopSendable;
    }

    public UdpProviderListener getListener() {
        return listener;
    }

    public void setListener(UdpProviderListener listener) {
        this.listener = listener;
    }

    public UdpSocket getSocket() {
        return socket;
    }

    public void setSocket(UdpSocket socket) {
        this.socket = socket;
    }

    public boolean isStoped() {
        return stoped;
    }

    public void setStoped(boolean stoped) {
        this.stoped = stoped;
    }

    public boolean isSendable() {
        return sendable;
    }

    public void setSendable(boolean sendable) {
        this.sendable = sendable;
    }

    public LoopSendThread getLoopSendThread() {
        return loopSendThread;
    }

    public void setLoopSendThread(LoopSendThread loopSendThread) {
        this.loopSendThread = loopSendThread;
    }

    public String toString() {
        return "udp:" + socket.getLocalAddress() + ":" + socket.getLocalPort();
    }

}
