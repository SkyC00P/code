package com.sunmnet.mediaroom.serialport.bean;

/**
 * SerialPortMSGInterface
 *
 * @author : skyco
 * @date : 2017/2/15
 */
public interface SerialPortMSGInterface {

    int MIN_MSG_LEN = 8;
    int NO_APP_DATA = 0;
    int NO_SEQNO = 0;

    ///////////////////////////////////////////////////////////////////////////
    // 收发消息的组件的枚举定义
    enum SU_TASK_TYPE {
        APP, PLATF, PA, SP, PROJECTOR, CARD, PA2, PA3
    }

    int TASK_TYPE_APP = 1;
    int TASK_TYPE_PLATF = 2;
    int TASK_TYPE_PA = 3;
    int TASK_TYPE_SP = 4;
    int TASK_TYPE_PROJECTOR = 5;
    int TASK_TYPE_CARD = 6;
    int TASK_TYPE_PA2 = 7;
    int TASK_TYPE_PA3 = 8;
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // 消息类型枚举定义
    enum SU_MSG_TYPE {
        DATA, CMD, DATA_UNRELIABLE, ACK, NAK, ECHO_REQ, ECHO_RESP, INIT_REQ, INIT_RESP
    }

    int MSG_DATA = 1;
    int MSG_CMD = 2;
    int MSG_DATA_UNRELIABLE = 3;
    int MSG_ACK = 4;
    int MSG_NAK = 5;
    int MSG_ECHO_REQ = 6;
    int MSG_ECHO_RESP = 7;
    int MSG_INIT_REQ = 8;
    int MSG_INIT_RESP = 9;

    ///////////////////////////////////////////////////////////////////////////
}
