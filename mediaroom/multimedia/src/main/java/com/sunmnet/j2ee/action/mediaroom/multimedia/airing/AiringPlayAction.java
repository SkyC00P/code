package com.sunmnet.j2ee.action.mediaroom.multimedia.airing;

import com.sunmnet.j2ee.action.mediaroom.common.IBaseWebModel;
import com.sunmnet.j2ee.bean.pub.action.JsonResult;
import com.sunmnet.j2ee.core.pub.annotation.MethodName;
import com.sunmnet.j2ee.entity.vo.AiringEvent;
import com.sunmnet.j2ee.entity.vo.TaskName;
import com.sunmnet.j2ee.utils.EventFactory;
import com.sunmnet.j2ee.utils.ResultUtil;
import com.sunmnet.j2ee.utils.net.TcpConnectProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller("/mediaroom/multimedia/airingPlay")
@RequestMapping("/mediaroom/multimedia/airingPlay")
public class AiringPlayAction {

    private static final Logger LOG = LogManager.getLogger(AiringPlayAction.class.getName());

    @MethodName(name = "语音直播开启")
    @RequestMapping(value = "/startAiring", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult startAiring(IBaseWebModel model){
        Map<String, Object> params = model.getParams();
        System.out.println("Get the object");
        System.out.println(params);
        // 封装成事件对象
        AiringEvent event = EventFactory.createEvent(TaskName.EVENT_START_AIRING, params);
        // 发送事件到服务
        TcpConnectProvider.sendEvent(event);
        return ResultUtil.SUC;
    }

    @MethodName(name = "语音直播操作")
    @RequestMapping(value = "/exec", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult exec(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "语音直播编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult edit(){
        return ResultUtil.SUC;
    }
}
