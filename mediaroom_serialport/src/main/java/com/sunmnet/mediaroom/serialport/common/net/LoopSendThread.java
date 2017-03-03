package com.sunmnet.mediaroom.serialport.common.net;

import com.sunmnet.mediaroom.serialport.common.Exception.UDPSendException;
import com.sunmnet.mediaroom.serialport.utils.LogManager;

import java.util.concurrent.LinkedBlockingQueue;
/**
 * LoopSendThread
 *
 * @author : skyco
 * @date : 2017/2/20
 */
public class LoopSendThread extends Thread {

    /**
     * 环形发送队列
     **/
    private LinkedBlockingQueue<UdpPacket> packageQueue;
    /**
     * 权限控制标志
     **/
    private boolean isIgnore;
    /**
     * 定时时间
     **/
    private int waitTime;
    /**
     * 线程标记
     **/
    private static int index = 0;
    /**
     * 绑定的 UdpProvider 服务
     **/
    private UdpProvider provider;

    /**
     * 是否在运行
     */
    private boolean isRuning = false;
    ///////////////////////////////////////////////////////////////////////////
    // LoopSendThread 构造方法

    /**
     * 构造定时5s, 无限制发送，自启动的 LoopSendThread
     *
     * @param provider - 绑定的 UdpProvider 服务
     */
    public LoopSendThread(UdpProvider provider) {
        super("[LoopSendThread - " + index++ + "]");
        init(provider, true, 5, true);
        LogManager.info(LoopSendThread.class, Thread.currentThread().getName() + " - " + getName() + " create suc");
    }

    /**
     * 构造定时5s, 无限制发送，指定自启动方式的 LoopSendThread
     *
     * @param provider  - 绑定的 UdpProvider 服务
     * @param isStartUp - 是否自启动 true : 自启动，false : 不自动启动
     */
    public LoopSendThread(UdpProvider provider, boolean isStartUp) {
        super("[LoopSendThread - " + index++ + "]");
        init(provider, true, 5, isStartUp);
        LogManager.info(LoopSendThread.class, Thread.currentThread().getName() + " - " + getName() + " create suc");
    }

    /**
     * 构造指定定时间隔, 自定义发送权限，指定自启动方式的 LoopSendThread
     *
     * @param provider  - 绑定的 UdpProvider 服务
     * @param isIgnore  - 是否受 Provider 的 sendable 变量控制 true : 忽略， false : 受控制
     * @param waitTime  - 指定定时间隔, 以秒为单位
     * @param isStartUp - 是否自启动 true : 自启动，false : 不自动启动
     */
    public LoopSendThread(UdpProvider provider, boolean isIgnore, int waitTime, boolean isStartUp) {
        super("[LoopSendThread - " + index++ + "]");
        init(provider, isIgnore, waitTime, isStartUp);
        LogManager.info(LoopSendThread.class, Thread.currentThread().getName() + " - " + getName() + " create suc");
    }

    private void init(UdpProvider provider, boolean isIgnore, int waitTime, boolean isStartUp) {
        packageQueue = new LinkedBlockingQueue<>();
        this.isIgnore = isIgnore;
        this.waitTime = waitTime;
        this.provider = provider;
        if (isStartUp){
            isRuning = true;
            start();
        }
    }
    ///////////////////////////////////////////////////////////////////////////

    public void removePackage(UdpPacket packet) {
        packageQueue.remove(packet);
    }

    public void putPackage(UdpPacket packets) throws UDPSendException {
        try {
            packageQueue.put(packets);
        } catch (InterruptedException e) {
            throw new UDPSendException("Can't put the UdpPacket into LinkedBlockingQueue", e);
        }
    }

    public void halt() {
        isRuning = false;
        interrupt();
        packageQueue.clear();
    }

    @Override
    public void run() {
        LogManager.info(LoopSendThread.class, Thread.currentThread().getName() + " - " + getName() + " start listen");
        isRuning = true;
        while (!provider.isStoped()) {
            try {
                if (waitTime > 0)
                    Thread.sleep(waitTime * 1000);

                if (!provider.isLoopSendable()) {
                    continue;
                }

                UdpPacket sendPacket = packageQueue.take();
                if (isIgnore)
                    provider.sendIgnore(sendPacket);
                else
                    provider.send(sendPacket);
                packageQueue.put(sendPacket);

            } catch (InterruptedException e) {
                LogManager.exceptionInfo(LoopSendThread.class, Thread.currentThread().getName() + " - " + getName() + " Loop Send the udp packet occur the InterruptedException", e);
            } catch (UDPSendException e1) {
                LogManager.exceptionInfo(LoopSendThread.class, Thread.currentThread().getName() + " - " + getName() + " Loop Send the udp packet occur the IOException", e1);
            }
        }
        LogManager.info(LoopSendThread.class, Thread.currentThread().getName() + " - " + getName() + " Loop exit");
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public void setIgnore(boolean ignore) {
        isIgnore = ignore;
    }

    public boolean isRuning() {
        return isRuning;
    }

    public void setRuning(boolean runing) {
        isRuning = runing;
    }
}
