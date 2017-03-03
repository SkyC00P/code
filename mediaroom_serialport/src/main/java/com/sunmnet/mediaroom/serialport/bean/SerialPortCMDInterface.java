package com.sunmnet.mediaroom.serialport.bean;

/**
 * SerialPortCMDInterface
 *
 * @author : skyco
 * @date : 2017/2/15
 */
public interface SerialPortCMDInterface {

    enum SU_CMD_TYPE {
        GET_STATS, MAX_TYPE
    }

    int CMD_GET_STATS = 1;
    int CMD_MAX_TYPE = 2;
}
