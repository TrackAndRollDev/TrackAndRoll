package com.neos.trackandroll.model.player.session;

import com.google.gson.annotations.SerializedName;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.utils.DateUtils;

import java.util.Date;

public class Session {

    public Session(String defaultName){
        this.sessionName = Constant.DEFAULT_SESSION_NAME;
        this.playerSessionData = new PlayerSessionData();
    }

    public Session(){
        this.playerSessionData = new PlayerSessionData();
    }

    /** Object ==> session date **/
    @SerializedName("session_date")
    private String sessionDate;

    public Date getSessionDate() {
        return DateUtils.getDateFromString(sessionDate,DateUtils.FORMAT_DATE_TRACK_AND_ROLL);
    }

    /** Object ==> session name **/
    @SerializedName("session_name")
    private String sessionName;

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    /** Object ==> session data **/
    @SerializedName("session_data")
    private PlayerSessionData playerSessionData;

    public PlayerSessionData getPlayerSessionData() {
        return playerSessionData ==null
                ? new PlayerSessionData()
                : playerSessionData;
    }

    public void setPlayerSessionData(PlayerSessionData playerSessionData) {
        this.playerSessionData = playerSessionData;
    }

}
