package com.sunmnet.j2ee.utils.net;

import com.sunmnet.j2ee.entity.vo.TaskName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TcpServerProvider
 *
 * @author : skyco
 * @date : 2017/3/1
 */
public class TcpServerProvider implements TaskName {

    /**
     * 维护的多服务
     **/
    private static Map<String, TcpServer> serverMap = new HashMap<>();

    /**
     * 每个服务对应一个连接管理处理机制
     */
    private static Map<TcpServer, TcpConnectHandle> connManage = new HashMap<>();
    /**
     * 默认监听广播服务的端口
     **/
    private static int AiringPort = 4002;

    /**
     * 开启监听广播服务的连接
     */
    public static void listen() {
        // 创建 TcpServer 监听指定端口
        try {
            TcpServer server = serverMap.get(AIRING_LISTEN);
            if (server == null) {
                // 获得监听广播服务的端口配置
                int port = getAiringPort();
                server = new TcpServer(AIRING_LISTEN, port);
                serverMap.put(AIRING_LISTEN, server);
                // 为当前广播服务创建一套连接管理机制
                connManage.put(server, TcpConnectProvider.createConnManage(AIRING_LISTEN));
                // 开始监听
                server.start();
                // 激活连接管理机制
                TcpConnectProvider.activateConnManage(AIRING_LISTEN);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getAiringPort() {
        return AiringPort;
    }

    /**
     * 关闭广播服务的监听
     */
    public static void stopListen() {
        TcpServer server = serverMap.get(AIRING_LISTEN);
        if (server != null)
            server.stopListen();
    }

    /**
     * 获得指定任务的监听服务器
     */
    public static TcpServer getServer(String taskName) {
        return serverMap.get(taskName);
    }

}
