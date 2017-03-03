package com.sunmnet.mediaroom.serialport.common.Exception;

/**
 * UDPInterruptException
 *
 * @author : skyco
 * @date : 2017/2/20
 */
public class UDPInterruptException extends Exception {

    public UDPInterruptException() {
    }

    public UDPInterruptException(Throwable cause) {
        super(cause);
    }

    public UDPInterruptException(String message) {
        super(message);
    }

    public UDPInterruptException(String message, Throwable cause) {
        super(message, cause);
    }

    public UDPInterruptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
