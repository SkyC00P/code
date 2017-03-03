package com.sunmnet.j2ee.entity.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * AiringEvent
 *
 * @author : skyco
 * @date : 2017/3/3
 */
public class AiringEvent {
    /**
     * 事件名
     **/
    private String EventName;

    /**
     * 发起地址
     **/
    private String Url;

    /**
     * 事件长度
     **/
    private String EventSize = "0";

    /**
     * 参数列表
     **/
    private Map<String, String> params = new HashMap<>();

    public void putParam(String key, String value) {
        params.put(key, value);
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String srcUrl) {
        this.Url = srcUrl;
    }

    private static final String Enter = "\n";

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(Enter);
        sb.append("Start" + Enter);
        sb.append("EventName:" + EventName + Enter);
        sb.append("Url:" + Url + Enter);
        if (params.isEmpty()){
            sb.append("EventSize:0" + Enter);
        } else {
            sb.append("EventSize:" + params.size() + Enter);
            for (Map.Entry<String, String> entry : params.entrySet()){
                sb.append(entry.getKey() + ":" + entry.getValue());
                sb.append(Enter);
            }
        }
        sb.append("End");
        return sb.toString();
    }
}
