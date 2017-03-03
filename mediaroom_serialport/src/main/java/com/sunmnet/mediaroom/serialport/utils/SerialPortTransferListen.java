package com.sunmnet.mediaroom.serialport.utils;

import com.sunmnet.mediaroom.serialport.bean.SerialPortCMD;
import com.sunmnet.mediaroom.serialport.bean.SerialPortMSG;
import com.sunmnet.mediaroom.serialport.bean.SerialPortMSGInterface;
import com.sunmnet.mediaroom.serialport.common.Exception.UDPSendException;
import com.sunmnet.mediaroom.serialport.common.net.UdpPacket;
import com.sunmnet.mediaroom.serialport.common.net.UdpProvider;
import com.sunmnet.mediaroom.serialport.common.net.UdpProviderListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * SerialPortTransferListen
 *
 * @author : skyco
 * @date : 2017/2/20
 */
public class SerialPortTransferListen extends Thread implements UdpProviderListener, SerialPortMSGInterface {

    private SerialPortHandle handle;
    private final static String TYPE_Provider = "UdpProvider";
    private final static String TYPE_Data = "DATA";

    // 缓存所有需要解析的数据队列
    private LinkedBlockingQueue<Map<String, Object>> dataQueue = new LinkedBlockingQueue<>();

    // 是否已经停止
    private boolean stoped = true;

    // 线程标记名字
    private static int index = 0;

    // Udp接收服务是否处于重连机制
    private static boolean isReconnect;

    ///////////////////////////////////////////////////////////////////////////
    // SerialPortTransferListen 构造方法
    public SerialPortTransferListen(SerialPortHandle handle, boolean isStart) {
        super("[SerialPortTransferListen - " + index++ + "]");
        init(handle, isStart);
    }

    public SerialPortTransferListen(SerialPortHandle handle) {
        super("[SerialPortTransferListen - " + index++ + "]");
        init(handle, false);
    }

    private void init(SerialPortHandle handle, boolean isStart){
        isReconnect = false;
        this.handle = handle;
        if (isStart){
            stoped = false;
            start();
        }
        LogManager.info(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " create suc");
    }
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onReceivedPacket(UdpProvider udp, UdpPacket packet) {
        // 获取缓冲数据的buf区域
        byte[] bufData = packet.getData();
        // 获取真实数据
        byte[] recData = new byte[packet.getLength()];
        System.arraycopy(bufData, packet.getOffset(), recData, 0, packet.getLength());
        LogManager.info(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + "Get the package：" + packet.getIpAddress().getHostAddress() + ":" + packet.getPort() + ":" + Arrays.toString(recData));
        // 放入缓存队列上等待处理
        try {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put(TYPE_Provider, udp);
            dataMap.put(TYPE_Data, recData);
            dataQueue.put(dataMap);
        } catch (InterruptedException e) {
            LogManager.exceptionInfo(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " onReceivedPacket error", e);
        }

    }

    /**
     * 停止分发监听
     */
    public void halt() {
        LogManager.info(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " 停止分发监听，退出线程");
        stoped = true;
        interrupt();
        dataQueue.clear();
    }

    @Override
    public void onServiceTerminated(UdpProvider udp, Exception error) {
        LogManager.info(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " start handle onServiceTerminated");
        // 如果遇到不可知原因，退出业务逻辑循环体，则调用UdpProvider方法回收资源
        halt();
        udp.halt();
        LogManager.exceptionInfo(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + udp.getName() + " 遇见不可预知错误，退出线程，回收资源", error);
    }

    /**
     * 如果遇见超过8秒的没接收到消息的异常，或是遇见由外部函数调用了InterruptSign引发UDPInterruptException异常
     * 启动重连机制。重复发送 echo_req 包到底层，只要接到一个数据，就可以退出重连机制，重新开启正常Provider服务
     * 处理逻辑
     * 1. 设置当前服务失去发送UDP包的权限
     * 2. 激活当前重连机制
     * 3. 激活轮询发送线程，发送心跳包
     */
    @Override
    public void onServiceInterrupted(UdpProvider udp, Exception error) {
        LogManager.info("SerialPortTransferListen.class", Thread.currentThread().getName() + " - " + getName() + " start handle onServiceInterrupted");
        udp.setSendable(false);
        udp.setLoopSendable(true);
        isReconnect = true;
        LogManager.info(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " 处理完 " + udp.getName() + " onServiceInterrupted事件，Error : " + error);
    }

    @Override
    public void run() {
        LogManager.info(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " start listen");
        try {
            stoped = false;
            while (!stoped) {
                Map<String, Object> dataMap = dataQueue.take();
                byte[] data = (byte[]) dataMap.get(TYPE_Data);
                UdpProvider provider = (UdpProvider) dataMap.get(TYPE_Provider);

                // 封装成串口协议对象
                SerialPortMSG spMsg = SerialPortMessageFactory.parse(data);
                if (spMsg == null) {
                    LogManager.exceptionInfo(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " 解析数据异常 - data : " + Arrays.toString(data));
                    continue;
                }

                // 收到有效消息，判断当前是否是处于重连机制中,如果是，退出重连机制
                if (isReconnect) {
                    LogManager.info(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " 退出重连机制");
                    isReconnect = false;
                    provider.setSendable(true);
                    provider.setLoopSendable(false);
                }

                if (SerialPortCMD.class.getName().equals(spMsg.getClass().getName())) {
                    if (handle != null)
                        handle.handle((SerialPortCMD) spMsg, provider);
                } else {
                    // 如果收到底层发来的心跳请求，则发响应回去
                    if (spMsg.getMsg_type_u8() == MSG_ECHO_REQ && spMsg.getSrc_task_type_u8() == TASK_TYPE_PLATF) {
                        provider.sendIgnore(SerialPortMessageFactory.getRespPackget());
                    } else if (spMsg.getMsg_type_u8() == MSG_ECHO_RESP && spMsg.getSrc_task_type_u8() == TASK_TYPE_PLATF) {
                        // 收到响应，不作处理
                    } else{
                        if (handle != null)
                            handle.handle(spMsg, provider);
                    }
                }
            }
        } catch (InterruptedException e) {
            LogManager.exceptionInfo(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " Loop error for InterruptedException", e);
        } catch (UDPSendException e) {
            LogManager.exceptionInfo(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " Loop error for UDPSendException", e);
        } finally {
            LogManager.info(SerialPortTransferListen.class, Thread.currentThread().getName() + " - " + getName() + " Loop exit");
        }
    }

    public SerialPortHandle getHandle() {
        return handle;
    }

    public void setHandle(SerialPortHandle handle) {
        this.handle = handle;
    }

    public LinkedBlockingQueue<Map<String, Object>> getDataQueue() {
        return dataQueue;
    }

    public void setDataQueue(LinkedBlockingQueue<Map<String, Object>> dataQueue) {
        this.dataQueue = dataQueue;
    }

    public static boolean isReconnect() {
        return isReconnect;
    }

    public static void setIsReconnect(boolean isReconnect) {
        SerialPortTransferListen.isReconnect = isReconnect;
    }

    public boolean isStoped() {
        return stoped;
    }

    public void setStoped(boolean stoped) {
        this.stoped = stoped;
    }
}
