package com.sunmnet.j2ee.utils.net;

import com.sunmnet.j2ee.entity.vo.AiringEvent;
import com.sunmnet.j2ee.entity.vo.TaskName;
import com.sunmnet.j2ee.utils.net.handle.AiringListenHandler;
import com.sunmnet.j2ee.utils.net.handle.DefaultHandel;

/**
 * TcpConnectProvider
 *
 * @author : skyco
 * @date : 2017/3/2
 */
public class TcpConnectProvider implements TaskName {

    private static class Manage {
        public static AiringListenHandler airingListen = new AiringListenHandler();
        public static DefaultHandel defaultHandel = new DefaultHandel();
    }

    public static TcpConnectHandle createConnManage(String taskName) {
        if (AIRING_LISTEN.equals(taskName)) {
            return Manage.airingListen;
        } else {
            return Manage.defaultHandel;
        }
    }

    public static void sendEvent(AiringEvent event) {
        Manage.airingListen.sendEvent(event);
    }

    /**
     * 激活管理机制
     */
    public static boolean activateConnManage(String airingListen) {
        try{
            if (AIRING_LISTEN.equals(airingListen)){
                Manage.airingListen.activate();
            } else {
                Manage.defaultHandel.activate();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
