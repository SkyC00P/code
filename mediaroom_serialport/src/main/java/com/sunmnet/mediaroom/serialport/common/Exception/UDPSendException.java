package com.sunmnet.mediaroom.serialport.common.Exception;

/**
 * UDPSendException
 *
 * @author : skyco
 * @date : 2017/2/20
 */
public class UDPSendException extends Exception {
    public UDPSendException() {
    }

    public UDPSendException(Throwable cause) {
        super(cause);
    }

    public UDPSendException(String message) {
        super(message);
    }

    public UDPSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public UDPSendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
