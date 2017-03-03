package com.sunmnet.j2ee.action.mediaroom.multimedia.airing;

import com.sunmnet.j2ee.bean.pub.action.JsonResult;
import com.sunmnet.j2ee.core.pub.annotation.MethodName;
import com.sunmnet.j2ee.utils.ResultUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("/mediaroom/multimedia/airingSpeak")
@RequestMapping("/mediaroom/multimedia/airingSpeak")
public class AiringSpeakAction {

    private static final Logger LOG = LogManager.getLogger(AiringSpeakAction.class.getName());

    @MethodName(name = "话筒直播开启")
    @RequestMapping(value = "/startSpeak", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult startAiring(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "话筒直播操作")
    @RequestMapping(value = "/exec", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult exec(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "话筒直播编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult edit(){
        return ResultUtil.SUC;
    }
}
