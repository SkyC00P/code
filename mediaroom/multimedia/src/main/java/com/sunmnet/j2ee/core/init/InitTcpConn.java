package com.sunmnet.j2ee.core.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * InitTcpConn
 *
 * @author : skyco
 * @date : 2017/3/1
 */
public class InitTcpConn implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(InitTcpConn.class.getName());


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("平台主动加载广播服务");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("平台关闭，断开已有的广播服务");
    }
}
