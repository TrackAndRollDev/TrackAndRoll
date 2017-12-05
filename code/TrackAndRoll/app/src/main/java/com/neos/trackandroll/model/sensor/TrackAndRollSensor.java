package com.neos.trackandroll.model.sensor;

import com.google.gson.annotations.SerializedName;
import com.neos.trackandroll.model.constant.Constant;

public class TrackAndRollSensor {

    @SerializedName("sensor_number")
    private int sensorNumber;

    @SerializedName("sensor_id")
    private String sensorId;

    @SerializedName("sensor_allocated_player_key")
    private String allocatedPlayerKey;

    @SerializedName("sensor_state")
    private String sensorState;

    public TrackAndRollSensor(int sensorNumber, String sensorId) {
        this.sensorNumber = sensorNumber;
        this.sensorId = sensorId;
        this.allocatedPlayerKey = Constant.SENSOR_NON_ASSIGNED_NAME;
        this.sensorState = Constant.SENSOR_STATE_CONNECTION_PROGRESS;
    }

    public int getSensorNumber() {
        return sensorNumber;
    }

    public void setSensorNumber(int sensorNumber) {
        this.sensorNumber = sensorNumber;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getAllocatedPlayerKey() {
        return allocatedPlayerKey;
    }

    public void setAllocatedPlayerKey(String allocatedPlayerKey) {
        this.allocatedPlayerKey = allocatedPlayerKey;
    }

    public String getSensorState() {
        return sensorState;
    }

    public void setSensorState(String sensorState) {
        this.sensorState = sensorState;
    }
}
