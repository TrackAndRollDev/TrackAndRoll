package com.neos.trackandroll.model.session;

import com.google.gson.annotations.SerializedName;

public class SportData {

    @SerializedName("player_speed")
    private String playerSpeed;

    @SerializedName("player_distance")
    private String playerDistance;

    @SerializedName("player_time")
    private String playerTime;

    @SerializedName("player_heart_beat")
    private String playerHeartBeat;

    @SerializedName("player_energy")
    private String playerEnergy;

    public SportData() {
        this.playerSpeed = "0";
        this.playerDistance = "0";
        this.playerTime = "0";
        this.playerHeartBeat = "0";
        this.playerEnergy = "0";
    }

    public Integer getPlayerSpeed() {
        return Integer.valueOf(playerSpeed);
    }

    public void setPlayerSpeed(String playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public Integer getPlayerDistance() {
        return Integer.valueOf(playerDistance);
    }

    public void setPlayerDistance(String playerDistance) {
        this.playerDistance = playerDistance;
    }

    public Integer getPlayerTime() {
        return Integer.valueOf(playerTime);
    }

    public void setPlayerTime(String playerTime) {
        this.playerTime = playerTime;
    }

    public Integer getPlayerHeartBeat() {
        return Integer.valueOf(playerHeartBeat);
    }

    public void setPlayerHeartBeat(String playerHeartBeat) {
        this.playerHeartBeat = playerHeartBeat;
    }

    public Integer getPlayerEnergy() {
        return Integer.valueOf(playerEnergy);
    }

    public void setPlayerEnergy(String playerEnergy) {
        this.playerEnergy = playerEnergy;
    }
}
