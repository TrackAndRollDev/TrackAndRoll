package com.neos.trackandroll.model;

import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.model.sensor.TrackAndRollSensor;
import com.neos.trackandroll.utils.DataUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class DataStore {

    /**
     * The instance of the DataStore
     */
    private static DataStore instance;

    /**
     * Getter of the instance DataStore
     * @return the DataStore instance
     */
    public static DataStore getInstance() {
        if (instance == null) {
            synchronized (DataStore.class) {
                if (instance == null) {
                    instance = new DataStore();
                }
            }
        }
        return instance;
    }

    /**
     * Object ==> PlayerList
     **/
    private AppConfiguration appConfiguration;

    public AppConfiguration getAppConfiguration() {
        return appConfiguration;
    }

    public void setAppConfiguration(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    /**
     * Object ==> runningSessionSensorList
     **/
    private ArrayList<TrackAndRollSensor> runningSessionSensorList;

    public ArrayList<TrackAndRollSensor> getRunningSessionSensorList() {
        return runningSessionSensorList;
    }

    public void setRunningSessionSensorList(ArrayList<TrackAndRollSensor> runningSessionSensorList) {
        this.runningSessionSensorList = runningSessionSensorList;
    }

    /**
     * Object ==> beginSessionTime
     **/
    private Calendar beginSessionTime;

    public Calendar getBeginSessionTime() {
        return beginSessionTime;
    }

    public void setBeginSessionTime(Calendar beginSessionTime) {
        this.beginSessionTime = beginSessionTime;
    }

    /**
     * Object ==> endSessionTime
     **/
    private Calendar endSessionTime;

    public Calendar getEndSessionTime() {
        return endSessionTime;
    }

    public void setEndSessionTime(Calendar endSessionTime) {
        this.endSessionTime = endSessionTime;
    }

    /**
     * Object ==> player List
     **/
    private ArrayList<Player> playerList;

    public ArrayList<Player> getPlayerList() {
        return playerList == null
                ? DataUtils.convertMapToPlayerList(DataStore.getInstance().getAppConfiguration().getPlayerMap())
                : DataUtils.sortPlayerList(playerList);
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }
}
