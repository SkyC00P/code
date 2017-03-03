package com.sunmnet.mediaroom.serialport.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogManager {

    private static final Logger log = LoggerFactory.getLogger("UDP_DEBUG");
    private static boolean isDebug = false;

    public static void info(String info) {
        if (!isDebug)
            return;
        log.info(info);
    }

    public static void info(String tag, String info) {
        if (!isDebug)
            return;
        log.info("[" + tag + "] " + info);
    }

    public static void info(Class<?> c, String info) {
        if (!isDebug)
            return;
        log.info("[" + c.getName() + "] " +info);
    }

    public static void exceptionInfo(String info, Exception e) {
        if (!isDebug)
            return;
        log.error(info, e);
    }

    public static void exceptionInfo(String tag, String info, Exception e) {
        if (!isDebug)
            return;
        log.error("[" + tag + "] " + info, e);
    }

    public static void exceptionInfo(Class<?> c, String info, Exception e) {
        if (!isDebug)
            return;
        log.error( "[" + c.getName() + "] " + info, e);
    }

    public static void exceptionInfo(Class<?> c, String info) {
        if (!isDebug)
            return;
        log.error("[" + c.getName() + "] " + info);
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setIsDebug(boolean isDebug) {
        LogManager.isDebug = isDebug;
    }
}
