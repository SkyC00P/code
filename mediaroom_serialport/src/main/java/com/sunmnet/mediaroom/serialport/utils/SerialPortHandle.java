package com.sunmnet.mediaroom.serialport.utils;

import com.sunmnet.mediaroom.serialport.bean.SerialPortCMD;
import com.sunmnet.mediaroom.serialport.bean.SerialPortMSG;
import com.sunmnet.mediaroom.serialport.common.net.UdpProvider;

/**
 * SerialPortHandle
 *
 * @author : skyco
 * @date : 2017/2/20
 */
public interface SerialPortHandle {

    /**
     * 处理 SerialPortCMD 的回调函数
     * @param spCMD
     */
    void handle(SerialPortCMD spCMD, UdpProvider provider);

    /**
     * 处理 SerialPortMSG 的回调函数
     * @param spMSG
     */
    void handle(SerialPortMSG spMSG, UdpProvider provider);
}
