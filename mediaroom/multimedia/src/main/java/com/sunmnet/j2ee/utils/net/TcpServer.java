package com.sunmnet.j2ee.utils.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TcpServer
 *
 * @author : skyco
 * @date : 2017/3/1
 */
public class TcpServer extends Thread {

    private static final Logger log = LoggerFactory.getLogger(TcpServer.class);

    /**
     * 任务名
     **/
    private String taskName;

    /**
     * 监听端口
     **/
    private int port;

    /**
     * 维护的 SocketServer
     **/
    private ServerSocket server;

    /**
     * 是否开始监听
     **/
    private boolean isListen;

    /**
     * 维护获得的连接Socket
     **/
    private LinkedBlockingQueue<Socket> socketQueue = new LinkedBlockingQueue<>(50);

    /**
     * 线程索引
     **/
    private static int index = 0;

    public TcpServer(String taskName, int port) throws IOException {
        super(taskName + index++);
        init(taskName, port);
    }

    private void init(String taskName, int port) throws IOException {
        server = new ServerSocket(port);
        this.taskName = taskName;
        this.port = port;
    }

    /**
     * 获得连接Socket
     */
    public Socket takeFromQueue() throws InterruptedException {
        return socketQueue.take();
    }

    /**
     * 停止监听
     **/
    public void stopListen() {
        log.info(getName() + " stop listen");
        isListen = false;
        interrupt();
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void run() {
        log.info(getName() + " start listen");

        isListen = true;
        while (isListen) {
            try {
                Socket socket = server.accept();
                log.info(getName() + "获得连接");
                socketQueue.put(socket);
            } catch (IOException e) {
                log.error(getName() + " occur IOException, so exit the loop", e);
                isListen = false;
            } catch (InterruptedException e) {
                log.error(getName() + " occur InterruptedException, so exit the loot", e);
                isListen = false;
            }
        }
        log.info(getName() + " exit the loop suc");
    }

    public boolean isListen() {
        return isListen;
    }

    public void setListen(boolean listen) {
        isListen = listen;
    }

    public int getPort() {
        return port;
    }

    public String getInetAddress(){
        return server.getInetAddress().getHostAddress();
    }
    public ServerSocket getServer() {
        return server;
    }

    public LinkedBlockingQueue<Socket> getSocketQueue() {
        return socketQueue;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getAcceptedNum(){return socketQueue.size();}

}
