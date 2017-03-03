package com.sunmnet.j2ee.utils.net.handle;

import com.sunmnet.j2ee.utils.net.TcpConnect;
import com.sunmnet.j2ee.utils.net.TcpConnectHandle;

/**
 * DefaultHandel
 *
 * @author : skyco
 * @date : 2017/3/2
 */
public class DefaultHandel implements TcpConnectHandle {
    @Override
    public void handleTcpConn(TcpConnect conn) {

    }

    @Override
    public void disconnect(TcpConnect conn) {

    }

    @Override
    public boolean sendData(TcpConnect conn, byte[] data) {
        return false;
    }

    public void activate() {
    }
}
