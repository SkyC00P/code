package com.sunmnet.j2ee.core.init;

import com.sunmnet.j2ee.utils.net.TcpServerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * InitTcpServer
 *
 * @author : skyco
 * @date : 2017/3/1
 */
public class InitTcpServer implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(InitTcpServer.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("监听广播服务连接");
        TcpServerProvider.listen();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("项目关闭，退出监听");
        TcpServerProvider.stopListen();
    }
}
