package com.sunmnet.mediaroom.serialport.common.net;

/**
 * UdpProviderListener
 * UdpProvider 事件的监听器
 * @author : skyco
 * @date : 2017/2/15
 */
public interface UdpProviderListener {

    /** 接收到 Udp 数据报文的回调函数 */
    void onReceivedPacket(UdpProvider udp, UdpPacket packet);

    /** UdpProvider 停止时的回调函数 */
    void onServiceTerminated(UdpProvider udp, Exception error);

    /** UdpProvider 收到中断信号时的处理回调函数 **/
    void onServiceInterrupted(UdpProvider udp, Exception error);
}
