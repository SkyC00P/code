package com.sunmnet.mediaroom.serialport.utils;

import com.sunmnet.mediaroom.serialport.bean.SerialPortCMD;
import com.sunmnet.mediaroom.serialport.bean.SerialPortCMDInterface;
import com.sunmnet.mediaroom.serialport.bean.SerialPortMSG;
import com.sunmnet.mediaroom.serialport.bean.SerialPortMSGInterface;
import com.sunmnet.mediaroom.serialport.common.Exception.UDPCreateException;
import com.sunmnet.mediaroom.serialport.common.Exception.UDPSendException;
import com.sunmnet.mediaroom.serialport.common.Exception.UtilityException;
import com.sunmnet.mediaroom.serialport.common.Utility;
import com.sunmnet.mediaroom.serialport.common.net.*;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * SerialPortMessageFactory
 *
 * @author : skyco
 * @date : 2017/2/16
 */
public class SerialPortMessageFactory implements SerialPortMSGInterface, SerialPortCMDInterface {

    private static InetAddress LOCAL_ADDRESS;
    private static int Port_S;
    private static int Port_R;
    private static int Alive_Time;

    public static final byte[] data_echo_req = initData(SU_TASK_TYPE.PLATF, SU_TASK_TYPE.APP, SU_MSG_TYPE.ECHO_REQ, null);
    public static final byte[] data_echo_resp = initData(SU_TASK_TYPE.PLATF, SU_TASK_TYPE.APP, SU_MSG_TYPE.ECHO_RESP, null);
    public static UdpProvider provider;

    static {
        try {
            LOCAL_ADDRESS = getAddress(null);
            Port_S = 12150;
            Port_R = 12151;
            Alive_Time = 5 * 1000;
        } catch (UnknownHostException e) {
            LogManager.exceptionInfo(SerialPortMessageFactory.class, "static init failed. can't get the local ip localaddress");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 串口数据生成

    /**
     * 封装交互命令
     *
     * @param dest     8位目的模块号
     * @param src      8位源模块号
     * @param msg_type 8位消息类型
     * @param app_data 8位预留字段
     * @param len      16位总长度
     * @param seqno    总长度
     * @param cmd_type 命令类型
     * @param cmd_leng 命令消息长度
     * @param data     数据
     */
    public static byte[] initData(SU_TASK_TYPE dest, SU_TASK_TYPE src, SU_MSG_TYPE msg_type, int app_data, int len, int seqno, SU_CMD_TYPE cmd_type, int cmd_leng, byte[] data) {
        byte[] msgData = new byte[len];
        msgData[0] = getSuTaskType(dest);
        msgData[1] = getSuTaskType(src);
        msgData[2] = getSuMsgType(msg_type);
        try {
            msgData[3] = Utility.integerToOneByte(app_data);
            msgData[4] = Utility.integerToTwoBytes(len)[1];
            msgData[5] = Utility.integerToTwoBytes(len)[0];
            msgData[6] = Utility.integerToTwoBytes(seqno)[1];
            msgData[7] = Utility.integerToTwoBytes(seqno)[0];
            msgData[8] = getSuCmdType(cmd_type);
            msgData[9] = Utility.integerToOneByte(cmd_leng);
            if (data != null && data.length != 0)
                System.arraycopy(data, 0, msgData, 10, data.length);
        } catch (UtilityException e) {
            LogManager.exceptionInfo(SerialPortMessageFactory.class, "Can't parse Integer to byte[]", e);
            msgData = null;
        }
        return msgData;
    }

    /**
     * 生成通信命令
     *
     * @param dest     8位目的模块号
     * @param src      8位源模块号
     * @param msg_type 8位消息类型
     * @param app_data 8位预留字段
     * @param len      16位总长度
     * @param seqno    总长度
     * @param data     数据
     */
    public static byte[] initData(SU_TASK_TYPE dest, SU_TASK_TYPE src, SU_MSG_TYPE msg_type, int app_data, int len, int seqno, byte[] data) {
        byte[] msgData = new byte[len];
        msgData[0] = getSuTaskType(dest);
        msgData[1] = getSuTaskType(src);
        msgData[2] = getSuMsgType(msg_type);
        try {
            msgData[3] = Utility.integerToOneByte(app_data);
            msgData[4] = Utility.integerToTwoBytes(len)[1];
            msgData[5] = Utility.integerToTwoBytes(len)[0];
            msgData[6] = Utility.integerToTwoBytes(seqno)[1];
            msgData[7] = Utility.integerToTwoBytes(seqno)[0];
            if (data != null && data.length != 0)
                System.arraycopy(data, 0, msgData, 8, data.length);
        } catch (UtilityException e) {
            LogManager.exceptionInfo(SerialPortMessageFactory.class, "Can't parse Integer to byte[]", e);
            msgData = null;
        }
        return msgData;
    }

    public static byte[] initData(SU_TASK_TYPE dest, SU_TASK_TYPE src, SU_MSG_TYPE msg_type, byte[] data) {
        int length = data == null ? 0 : data.length;
        return initData(dest, src, msg_type, NO_APP_DATA, MIN_MSG_LEN + length, NO_SEQNO, data);
    }

    public static byte getSuCmdType(SU_CMD_TYPE cmd_type) {
        try {
            switch (cmd_type) {

                case GET_STATS:
                    return Utility.integerToOneByte(CMD_GET_STATS);
                case MAX_TYPE:
                    return Utility.integerToOneByte(CMD_MAX_TYPE);
            }

        } catch (UtilityException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static byte getSuTaskType(SU_TASK_TYPE dest) {
        try {
            switch (dest) {
                case APP:
                    return Utility.integerToOneByte(TASK_TYPE_APP);
                case PLATF:
                    return Utility.integerToOneByte(TASK_TYPE_PLATF);
                case PA:
                    return Utility.integerToOneByte(TASK_TYPE_PA);
                case SP:
                    return Utility.integerToOneByte(TASK_TYPE_SP);
                case PROJECTOR:
                    return Utility.integerToOneByte(TASK_TYPE_PROJECTOR);
                case CARD:
                    return Utility.integerToOneByte(TASK_TYPE_CARD);
                case PA2:
                    return Utility.integerToOneByte(TASK_TYPE_PA2);
                case PA3:
                    return Utility.integerToOneByte(TASK_TYPE_PA3);
            }
        } catch (UtilityException e) {
        }
        return -1;
    }

    public static byte getSuMsgType(SU_MSG_TYPE msg_type) {
        try {
            switch (msg_type) {
                case DATA:
                    return Utility.integerToOneByte(MSG_DATA);
                case CMD:
                    return Utility.integerToOneByte(MSG_CMD);
                case DATA_UNRELIABLE:
                    return Utility.integerToOneByte(MSG_DATA_UNRELIABLE);
                case ACK:
                    return Utility.integerToOneByte(MSG_ACK);
                case NAK:
                    return Utility.integerToOneByte(MSG_NAK);
                case ECHO_REQ:
                    return Utility.integerToOneByte(MSG_ECHO_REQ);
                case ECHO_RESP:
                    return Utility.integerToOneByte(MSG_ECHO_RESP);
                case INIT_REQ:
                    return Utility.integerToOneByte(MSG_INIT_REQ);
                case INIT_RESP:
                    return Utility.integerToOneByte(MSG_INIT_RESP);
            }

        } catch (UtilityException e) {
            e.printStackTrace();
        }
        return -1;
    }
    ///////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////
    // Udp 包生成
    public static UdpPacket createUdpPacket(byte[] data, int length, InetAddress ipaddr, int port) {
        return createUdpPacket(data, 0, length, ipaddr, port);
    }

    public static UdpPacket createUdpPacket(byte[] data, int offset, int length, InetAddress ipaddr, int port) {
        return new UdpPacket(data, offset, length, ipaddr, port);
    }

    public static UdpPacket createUdpPacket(byte[] data) {
        return createUdpPacket(data, 0, data.length, LOCAL_ADDRESS, Port_S);
    }

    public static UdpPacket createUdpPacket(SU_TASK_TYPE dest, SU_TASK_TYPE src, SU_MSG_TYPE msg_type, byte[] dataStr) {
        byte[] data = initData(dest, src, msg_type, dataStr);
        return createUdpPacket(data, 0, data.length, LOCAL_ADDRESS, Port_S);
    }

    /**
     * 获得心跳包
     */
    public static UdpPacket getHeartBeatPacket() {
        return createUdpPacket(data_echo_req, data_echo_req.length, LOCAL_ADDRESS, Port_S);
    }

    /**
     * 获得响应包
     */
    public static UdpPacket getRespPackget() {
        return createUdpPacket(data_echo_resp, data_echo_resp.length, LOCAL_ADDRESS, Port_S);
    }
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 将数据解析成对象
     *
     * @param data
     * @return
     */
    public static SerialPortMSG parse(byte[] data) {

        if (data == null || data.length < MIN_MSG_LEN) {
            return null;
        }
        // 优先解析出所需数据
        try {
            int dest_task_type_u8 = Utility.oneByteToInteger(data[0]);
            int src_task_type_u8 = Utility.oneByteToInteger(data[1]);
            int msg_type_u8 = Utility.oneByteToInteger(data[2]);
            int app_data_u8 = Utility.oneByteToInteger(data[3]);
            int len_u16 = Utility.twoBytesToInteger(new byte[]{data[5], data[4]});
            int seqno_u16 = Utility.twoBytesToInteger(new byte[]{data[7], data[6]});

            // 判断要生成哪个消息类型
            if (msg_type_u8 == MSG_CMD) {
                int len_8 = Utility.oneByteToInteger(data[8]);
                int cmd_type_u8 = Utility.oneByteToInteger(data[9]);
                byte[] data_rec = Arrays.copyOfRange(data, 10, data.length);

                SerialPortCMD resultCMD = new SerialPortCMD();
                resultCMD.setDest_task_type_u8(dest_task_type_u8);
                resultCMD.setSrc_task_type_u8(src_task_type_u8);
                resultCMD.setMsg_type_u8(msg_type_u8);
                resultCMD.setApp_data_u8(app_data_u8);
                resultCMD.setLen_u16(len_u16);
                resultCMD.setSeqno_u16(seqno_u16);
                resultCMD.setLen_8(len_8);
                resultCMD.setCmd_type_u8(cmd_type_u8);
                resultCMD.setData(data_rec);
                return resultCMD;
            } else {
                byte[] data_rec = Arrays.copyOfRange(data, 8, data.length);

                SerialPortMSG resultMsg = new SerialPortMSG();
                resultMsg.setDest_task_type_u8(dest_task_type_u8);
                resultMsg.setSrc_task_type_u8(src_task_type_u8);
                resultMsg.setMsg_type_u8(msg_type_u8);
                resultMsg.setApp_data_u8(app_data_u8);
                resultMsg.setLen_u16(len_u16);
                resultMsg.setSeqno_u16(seqno_u16);
                resultMsg.setData(data_rec);
                return resultMsg;
            }

        } catch (UtilityException e) {
            return null;
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    // 创建 Provider 服务

    /**
     * <pre>
     * 建立 UdpProvider 服务，每次建立都会返回一个全新的服务对象
     * 默认监听端口12151，超时时间5s, 采用默认的分发监听器 SerialPortTransferListen 处理服务udp包分发，服务中断，服务停止事件
     * </pre>
     *
     * @throws UDPCreateException
     */
    public static UdpProvider createProvider(SerialPortHandle handle) throws UDPCreateException {
        return createProvider(handle, true);
    }

    /**
     * <pre>
     * 建立 UdpProvider 服务，每次建立都会返回一个全新的服务对象
     * 默认监听端口12151，超时时间5s, 采用默认的分发监听器 SerialPortTransferListen 处理服务udp包分发，服务中断，服务停止事件
     * </pre>
     *
     * @throws UDPCreateException
     */
    public static UdpProvider createProvider(SerialPortHandle handle, boolean isStart) throws UDPCreateException {

        Exception error = null;

        try {
            UdpSocket socket = new UdpSocket(Port_R);
            SerialPortTransferListen listen = new SerialPortTransferListen(handle);
            provider = new UdpProvider(socket, Alive_Time, listen);
            provider.send(getHeartBeatPacket());
            provider.setLoopSendable(false);
            provider.getLoopSendThread().putPackage(getHeartBeatPacket());

            if (isStart) {
                if (listen.getState() == Thread.State.NEW)
                    listen.start();
                if (provider.getLoopSendThread().getState() == Thread.State.NEW)
                    provider.getLoopSendThread().start();
                if (provider.getState() == Thread.State.NEW)
                    provider.start();
            }
        } catch (UDPSendException | SocketException e1) {
            error = e1;
        } finally {
            if (error != null) {
                provider = null;
                throw new UDPCreateException("Can't create the Udpsocket", error);
            }
        }

        return provider;
    }

    public static UdpProvider createProvider(int port_R, int alive_Time, UdpProviderListener listen, LoopSendThread loopSendThread, boolean isStart) throws UDPCreateException {

        Exception error = null;

        try {
            provider = new UdpProvider(port_R, alive_Time, listen, loopSendThread);
            provider.send(getHeartBeatPacket());
            provider.setLoopSendable(false);
            provider.getLoopSendThread().putPackage(getHeartBeatPacket());

            if (isStart) {
                if (listen instanceof Thread && ((Thread) listen).getState() == Thread.State.NEW)
                    ((Thread) listen).start();
                if (provider.getLoopSendThread().getState() == Thread.State.NEW)
                    provider.getLoopSendThread().start();
                if (provider.getState() == Thread.State.NEW)
                    provider.start();
            }
        } catch (UDPSendException e) {
            error = e;
        } finally {
            if (error != null) {
                provider = null;
                throw new UDPCreateException("Can't put the HeartBeatPacket into LoopThread", error);
            }
        }

        return provider;
    }
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获得InetAddress实例
     *
     * @param ipAddress
     * @return
     * @throws UnknownHostException
     */
    private static InetAddress getAddress(String ipAddress) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        if (ipAddress == null || "".equals(ipAddress.trim())) {
            return address;
        }
        return InetAddress.getByName(ipAddress);
    }

}
