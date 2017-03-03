package com.sunmnet.j2ee.entity.mediaroom.multimedia.airing;

/**
 * AiringRoomState
 *
 * @author : skyco
 * @date : 2017/2/27
 */
public class AiringRoomState {

    private String classCode;
    private String airingState;
    private String number;

    public String getAiringState() {
        return airingState;
    }

    public void setAiringState(String airingState) {
        this.airingState = airingState;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
