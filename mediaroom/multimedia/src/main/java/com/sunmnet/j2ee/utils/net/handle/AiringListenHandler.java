package com.sunmnet.j2ee.utils.net.handle;

import com.sunmnet.j2ee.entity.vo.AiringEvent;
import com.sunmnet.j2ee.entity.vo.TaskName;
import com.sunmnet.j2ee.utils.EventFactory;
import com.sunmnet.j2ee.utils.net.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AiringListenHandler
 * 监听广播服务独有的连接机制
 * 1. 对于每个广播服务平台仅维护一个长连接，平台有配置有多少个服务，就维护多少条连接
 * 2. 在预定的时间里检测平台配置的服务的连接数是否达标，如果当从Tcp服务里收到的socket连接没有满足平台配置的服务的连接数，则向服务发起连接请求
 * 3. 每当连接建立，向服务发请求命令，服务应答后，连接才正式建立，双方在一来一回中同步状态
 * 4. 每当任意一方断开都要重新检查平台关于广播配置，如果该断开是允许的，则修改当前状态，释放该连接，如果不是，则进入重连机制
 * 5. 在建立完成连接后，执行TcpConn
 *
 * @author : skyco
 * @date : 2017/3/2
 */
public class AiringListenHandler implements TcpConnectHandle {

    /**
     * 连接的来源
     **/
    private TcpServer airingServer;
    /**
     * 当前维护的连接数
     **/
    private static AtomicInteger connNum;
    /**
     * 是否激活
     **/
    private boolean isActive = false;
    /**
     * 连接的广播服务地址
     **/
    private List<String> airingUrl = new ArrayList<>();
    /**
     * 广播服务有效连接数
     **/
    private int effectConn;

    /**
     * 当前管理的有效连接
     **/
    private final static Map<String, TcpConnect> connectMap = new HashMap<>();

    /**
     * TcpConnect 构造器
     **/
    private TcpConnectBuild build;

    /**
     * 监听等待时间
     **/
    private int monitorTime;

    /**
     * 同步锁
     **/
    private static final Object lock = new Object();

    // 激活该功能
    public void activate() throws Exception {
        isActive = true;
        // 获取 Tcp服务
        airingServer = TcpServerProvider.getServer(TaskName.AIRING_LISTEN);
        if (airingServer == null || !airingServer.isListen()) {
            throw new Exception("The airingServer don't listen, you can't active the manage");
        }
        // 初始化连接数
        connNum = new AtomicInteger(0);

        // 初始化当前广播服务配置
        initAiringConfig();

        // 启动 TcpConnect 构造器，为 TcpServer 收到的每一个 socket 构造连接管理 TcpConnect
        if (build != null)
            build.halt();
        build = new TcpConnectBuild(airingServer, monitorTime, this);
    }

    /**
     * 验证是否允许该Socket通过
     *
     * @param socket
     * @return
     */
    private boolean checkEffectConn(Socket socket) {

        if (socket == null || socket.isClosed()) {
            return false;
        }

        // 平台有要求，且平台尚未维护该连接
        String url = transformIP(socket);
        synchronized (lock) {
            if (connectMap.get(url) != null || !airingUrl.contains(url)) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }else
                return true;
        }
    }

    private String transformIP(Socket c) {
        return c == null ? null : c.getInetAddress().getHostAddress() + ":" + c.getPort();
    }

    /**
     * 以短连接的方式发送连接通知到尚未连接的广播服务，通知服务来连接
     **/
    private void notifyAiringConn() {
        // TODO: 2017/3/3
        for (String url : airingUrl){
            synchronized (lock){
                if (connectMap.get(url) == null)
                    System.out.println("send");
//                    TcpConnectProvider.send
            }
        }
    }

    private void addConnNum() {
        connNum.incrementAndGet();
    }
    private void decrementConnNum() {
        connNum.decrementAndGet();
    }
    /**
     * 检查是否已经达到平台配置的有效广播服务数
     */
    private synchronized boolean checkConnNum() {
        return effectConn == connNum.get();
    }

    private synchronized void initAiringConfig() {
        airingUrl.clear();
        // TODO: 2017/3/2 这里后面修改为去配置文件或是数据库里加载配置信息
        airingUrl.add("127.0.0.1:4002");
        airingUrl.add("192.168.18.54:4002");
        monitorTime = 10;
        effectConn = airingUrl.size();
    }

    /**
     * 每个连接的处理
     * @param conn
     */
    @Override
    public void handleTcpConn(TcpConnect conn) {
        conn.setRunning(true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInput()));
        while (conn.isRunning()){
            StringBuilder sb = new StringBuilder();
            String tmp = "";
            try {
                while ( (tmp = reader.readLine()) != null){
                    if (tmp.isEmpty()){
                        tmp = reader.readLine();
                        if (tmp.startsWith("Start")){
                            // 添加事件名
                            String name = reader.readLine().replace("EventName:", "");
                            // 添加Url
                            String url = reader.readLine().replace("Url:", "");
                            // 添加参数大小
                            String size = reader.readLine().replace("");
                            // 添加所有参数
                        }
                        if (tmp.startsWith("End")){
                            AiringEvent event = EventFactory.createEvent()
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void disconnect(TcpConnect conn) {
        // 断开连接时，计数减一
        decrementConnNum();
        // 有效连接缓存清除连接
        String url = transformIP(conn.getSocket());
        synchronized (lock){
            connectMap.remove(url);
            // 重新加载广播配置
            initAiringConfig();
        }
    }

    /**
     * 发送消息
     */
    @Override
    public boolean sendData(TcpConnect conn, byte[] data) {
        // TODO: 2017/3/3 这里会不会有问题，对于每一个TCP 连接都创建了一个，但是又不关闭它，再次创建时是否能正常
        BufferedOutputStream outputStream = new BufferedOutputStream(conn.getOutput());
        try {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 对所有当前有效连接发送事件
     * @param event
     */
    public void sendEvent(AiringEvent event) {
        synchronized (lock){
            for (TcpConnect conn : connectMap.values()){
                conn.sendData(event.toString().getBytes());
            }
        }
    }

    /**
     * 实时监听Tcp的连接与恢复
     */
    private static class TcpConnectBuild extends Thread {

        private TcpServer server;
        private int monitorTime;
        private boolean stoped;
        private static int index = 0;
        private AiringListenHandler handle;

        public TcpConnectBuild(TcpServer server, int time, AiringListenHandler handle) {
            this.server = server;
            monitorTime = time;
            setName("[TcpConnectBuild-" + index++ + "]");
            this.handle = handle;
            start();
        }

        @Override
        public void run() {
            stoped = false;
            while (!stoped) {
                Socket socket = null;
                try {
                    // 获得 socket
                    socket = server.getSocketQueue().poll(monitorTime, TimeUnit.SECONDS);
                    if (socket == null) {
                        // 如果指定时间内没有获得Socket，检查是否已经达到平台配置的有效广播服务数，如果达到，则跳过；否则进行以短连接的方式发送连接通知到尚未连接的广播服务，通知服务来连接
                        if (!handle.checkConnNum()) {
                            handle.initAiringConfig();
                            handle.notifyAiringConn();
                        }
                    } else {
                        // 检测是否是有效连接，如果是有效连接，为其构建 TcpConnect 连接管理，如果不是，关掉该socket
                        if (handle.checkEffectConn(socket)) {
                            // 构建 TcpConnect 连接
                            TcpConnect conn = new TcpConnect(socket, handle);
                            conn.setKeepAlive(true);
                            conn.setRight(TcpConnect.Right.RW);
                            // 启动 TcpConnect 管理
                            conn.start();
                            // 放入缓存里
                            connectMap.put(socket.getInetAddress().getHostAddress(), conn);
                            // 计数 + 1
                            handle.addConnNum();
                        }
                    }
                } catch (Exception e) {
                    stoped = true;
                    if (socket != null)
                        try {
                            socket.close();
                        } catch (IOException e1) {

                        }
                }
            }
        }

        public void halt() {
            stoped = true;
            interrupt();
        }
    }
}
