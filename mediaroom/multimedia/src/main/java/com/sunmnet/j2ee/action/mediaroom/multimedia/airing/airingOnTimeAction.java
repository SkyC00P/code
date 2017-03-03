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

@Controller("/mediaroom/multimedia/airingOnTime")
@RequestMapping("/mediaroom/multimedia/airingOnTime")
public class airingOnTimeAction {

    private static final Logger LOG = LogManager.getLogger(airingOnTimeAction.class.getName());

    @MethodName(name = "定时直播添加")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult add(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "定时直播运行时操作")
    @RequestMapping(value = "/exec", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult exec(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "定时直播编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult edit(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "加载广播规则")
    @RequestMapping(value = "/loadRule", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult loadRule(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "定时直播删除")
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult del(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "加载广播历史")
    @RequestMapping(value = "/loadHistory", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult loadHistory(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "加载定时广播教室")
    @RequestMapping(value = "/loadClassRoom", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult loadClassRoom(){
        return ResultUtil.SUC;
    }

    @MethodName(name = "加载定时广播使用音源")
    @RequestMapping(value = "/loadMusic", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult loadMusic(){
        return ResultUtil.SUC;
    }
}
