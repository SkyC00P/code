package com.sunmnet.j2ee.utils;

import com.sunmnet.j2ee.entity.vo.AiringEvent;
import com.sunmnet.j2ee.entity.vo.TaskName;
import com.sunmnet.j2ee.utils.net.TcpServer;
import com.sunmnet.j2ee.utils.net.TcpServerProvider;

import java.util.Map;

/**
 * EventFactory
 *
 * @author : skyco
 * @date : 2017/3/3
 */
public class EventFactory implements TaskName {

    /**
     * 生成 AiringEvent 对象
     * @param EventName
     * @param params
     * @return
     */
    public static AiringEvent createEvent(String EventName, Map<String, Object> params) {
        AiringEvent event = new AiringEvent();
        event.setEventName(EventName);
        event.setUrl(getPlatformIP());
        for (Map.Entry<String, Object> entry : params.entrySet()){
            event.putParam(entry.getKey(), (String) entry.getValue());
        }
        return event;
    }

    /**
     * 获得平台的广播监听地址
     * @return
     */
    public static String getPlatformIP() {
        TcpServer server = TcpServerProvider.getServer(TaskName.AIRING_LISTEN);
        return server.getInetAddress()+":"+ server.getPort();
    }

//    public static
}
