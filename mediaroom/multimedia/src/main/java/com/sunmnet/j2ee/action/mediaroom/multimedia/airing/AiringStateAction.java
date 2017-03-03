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

@Controller("/mediaroom/multimedia/airingState")
@RequestMapping("/mediaroom/multimedia/airingState")
public class AiringStateAction {

    private static final Logger LOG = LogManager.getLogger(AiringStateAction.class.getName());

    @MethodName(name = "状态更新")
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult startAiring() {
        return ResultUtil.SUC;
    }

}
