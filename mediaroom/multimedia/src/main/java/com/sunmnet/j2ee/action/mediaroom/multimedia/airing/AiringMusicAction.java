package com.sunmnet.j2ee.action.mediaroom.multimedia.airing;

import com.sunmnet.j2ee.action.mediaroom.common.IBaseNewAction;
import com.sunmnet.j2ee.action.mediaroom.common.IBaseWebModel;
import com.sunmnet.j2ee.bean.pub.action.JsonResult;
import com.sunmnet.j2ee.core.pub.annotation.MethodName;
import com.sunmnet.j2ee.entity.mediaroom.multimedia.airing.AiringMusic;
import com.sunmnet.j2ee.entity.mediaroom.multimedia.airing.AiringRoomState;
import com.sunmnet.j2ee.entity.mediaroom.multimedia.airing.AiringState;
import com.sunmnet.j2ee.utils.ResultUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller("/mediaroom/multimedia/airingMusic")
@RequestMapping("/mediaroom/multimedia/airingMusic")
public class AiringMusicAction extends IBaseNewAction<AiringMusic, Long> {

    private static final Logger LOG = LogManager.getLogger(AiringMusicAction.class.getName());

    @MethodName(name = "分页加载音乐实体")
    @RequestMapping(value = "/loadAllMusic", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult loadAllMusic(IBaseWebModel pa) {

        JsonResult r = new JsonResult();
        r.setSuccess(true);
        r.setMsg("语音直播更新状态成功");
        AiringState state = new AiringState();
        state.setAiringCode("Airing_Code_001");
        state.setAirngName("英语四级考试");
        List<AiringRoomState> roomStates = new ArrayList<>();
        AiringRoomState a1 = new AiringRoomState();
        a1.setClassCode("T1-0101");
        a1.setAiringState("异常");
        a1.setNumber("1008");
        AiringRoomState a2 = new AiringRoomState();
        a2.setClassCode("T1-0102");
        a2.setAiringState("在线");
        a2.setNumber("1007");
        roomStates.add(a1);
        roomStates.add(a2);
        state.setRoomStates(roomStates);
        state.setCurMusicName("英语四级考试");
        state.setNextMusicName("英语六级考试");
        state.setPreMusicName("考试须知");
        state.setState("Running");
        state.setPosition("120000");
        state.setMusicLenth("3600000");
        state.setVolume("6");
        state.setPlayMode("1");
        List<String> codes = new ArrayList<>();
        codes.add("1");
        codes.add("2");
        codes.add("3");
        state.setPlayMusicList(codes);
        r.setObj(state);
//        r.setObj(maps);
//        r.setErrorCode(400);
//        return super.findPages(pa);
        return r;
    }

    @MethodName(name = "音乐试听")
    @RequestMapping(value = "/tryListen", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult tryListen() {
        return ResultUtil.SUC;
    }
}
