package com.sunmnet.j2ee.entity.mediaroom.multimedia.airing;

import java.util.List;

/**
 * AiringState
 *
 * @author : skyco
 * @date : 2017/2/27
 */
public class AiringState {

    // 广播编号
    private String airingCode;
    // 广播名称
    private String airngName;
    // 教室状态
    private List<AiringRoomState> roomStates;
    // 当前播放音乐名称
    private String curMusicName;
    // 下一首播放音乐名称
    private String nextMusicName;
    // 上一首播放音乐名称
    private String preMusicName;
    // 当前状态：播放，暂停，无
    // Runnig , stop, pause
    private String state;
    // 音乐进度
    private String position;
    // 音乐总长度
    private String musicLenth;
    // 音量大小
        private String volume;
    // 播放模式
    private String playMode;
    // 播放列表, 以AiringMusic 的 Code 为排列
    private List<String> playMusicList;

    public String getAiringCode() {
        return airingCode;
    }

    public void setAiringCode(String airingCode) {
        this.airingCode = airingCode;
    }

    public String getAirngName() {
        return airngName;
    }

    public void setAirngName(String airngName) {
        this.airngName = airngName;
    }

    public String getCurMusicName() {
        return curMusicName;
    }

    public void setCurMusicName(String curMusicName) {
        this.curMusicName = curMusicName;
    }

    public String getMusicLenth() {
        return musicLenth;
    }

    public void setMusicLenth(String musicLenth) {
        this.musicLenth = musicLenth;
    }

    public String getNextMusicName() {
        return nextMusicName;
    }

    public void setNextMusicName(String nextMusicName) {
        this.nextMusicName = nextMusicName;
    }

    public String getPlayMode() {
        return playMode;
    }

    public void setPlayMode(String playMode) {
        this.playMode = playMode;
    }

    public List<String> getPlayMusicList() {
        return playMusicList;
    }

    public void setPlayMusicList(List<String> playMusicList) {
        this.playMusicList = playMusicList;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPreMusicName() {
        return preMusicName;
    }

    public void setPreMusicName(String preMusicName) {
        this.preMusicName = preMusicName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public List<AiringRoomState> getRoomStates() {
        return roomStates;
    }

    public void setRoomStates(List<AiringRoomState> roomStates) {
        this.roomStates = roomStates;
    }
}
