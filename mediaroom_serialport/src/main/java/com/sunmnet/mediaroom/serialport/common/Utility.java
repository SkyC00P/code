package com.sunmnet.mediaroom.serialport.common;

import com.sunmnet.mediaroom.serialport.common.Exception.UtilityException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Utility
 *
 * @author : skyco
 * @date : 2017/2/15
 */
public class Utility {

    public static final byte integerToOneByte(int value) throws UtilityException {
        if ((value > Math.pow(2, 15)) || (value < 0)) {
            throw new UtilityException("Integer value " + value + " is larger than 2^15");
        }
        return (byte) (value & 0xFF);
    }

    // int 转 16位，小端
    public static final byte[] integerToTwoBytes(int value) throws UtilityException {
        byte[] result = new byte[2];
        if ((value > Math.pow(2, 31)) || (value < 0)) {
            throw new UtilityException("Integer value " + value + " is larger than 2^31");
        }
        result[0] = (byte) ((value >>> 8) & 0xFF);
        result[1] = (byte) (value & 0xFF);
        return result;
    }

    public static final byte[] integerToTwoBytes_Big(int value) throws UtilityException{
        byte[] result = new byte[2];
        if ((value > Math.pow(2, 31)) || (value < 0)) {
            throw new UtilityException("Integer value " + value + " is larger than 2^31");
        }
        result[1] = (byte) ((value >>> 8) & 0xFF);
        result[0] = (byte) (value & 0xFF);
        return result;
    }

    public static final byte[] integerToFourBytes(int value) throws UtilityException {
        byte[] result = new byte[4];
        if ((value > Math.pow(2, 63)) || (value < 0)) {
            throw new UtilityException("Integer value " + value + " is larger than 2^63");
        }
        result[0] = (byte) ((value >>> 24) & 0xFF);
        result[1] = (byte) ((value >>> 16) & 0xFF);
        result[2] = (byte) ((value >>> 8) & 0xFF);
        result[3] = (byte) (value & 0xFF);
        return result;
    }

    public static final int oneByteToInteger(byte value) throws UtilityException {
        return (int) value & 0xFF;
    }

    public static final int twoBytesToInteger(byte[] value) throws UtilityException {
        if (value.length < 2) {
            throw new UtilityException("Byte array too short!");
        }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        return ((temp0 << 8) + temp1);
    }

    public static final long fourBytesToLong(byte[] value) throws UtilityException {
        if (value.length < 4) {
            throw new UtilityException("Byte array too short!");
        }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return (((long) temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    public static void main(String[] args) {
        int len = 1000;
        short sLen = 1000;
        byte[] data = new byte[]{3, -24};
        byte[] data1 = new byte[]{-24, 3};

        byte[] temp;

        try {
            System.out.println(Arrays.toString(temp = integerToTwoBytes(len)));
            System.out.println(twoBytesToInteger(temp));

            System.out.println(Arrays.toString(temp = integerToTwoBytes_Big(len)));

            System.out.println(twoBytesToInteger(temp));

            System.out.println(integerToOneByte(len));

            System.out.println(oneByteToInteger(integerToOneByte(len)));
            try {
                System.out.println(InetAddress.getByName("192.168.18.55"));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } catch (UtilityException e) {
            e.printStackTrace();
        }
    }
}
