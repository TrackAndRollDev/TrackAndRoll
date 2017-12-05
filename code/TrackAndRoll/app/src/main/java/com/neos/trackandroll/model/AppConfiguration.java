package com.neos.trackandroll.model;

import com.google.gson.annotations.SerializedName;
import com.neos.trackandroll.model.account.Account;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.model.sensor.TrackAndRollSensor;

import java.util.ArrayList;
import java.util.HashMap;

public class AppConfiguration {

    /**
     * Main constructor of the app configuration
     */
    public AppConfiguration() {
        this.getSessionNameList().add(Constant.DEFAULT_SESSION_NAME);
    }

    /**
     * Object ==> PlayerMap
     **/
    @SerializedName("player_map")
    private HashMap<String, Player> playerMap;

    /**
     * Getter of the player map
     * @return : the player map
     */
    public HashMap<String, Player> getPlayerMap() {
        return playerMap == null
                ? playerMap = new HashMap<String, Player>()
                : playerMap;
    }

    /**
     * Object ==> session name list
     **/
    @SerializedName("session_name_list")
    private ArrayList<String> sessionNameList;

    /**
     * Getter of the session name list
     * @return the session name list
     */
    public ArrayList<String> getSessionNameList() {
        return sessionNameList == null
                ? sessionNameList = new ArrayList<String>()
                : sessionNameList;
    }

    /**
     * Object ==> sensor list
     **/
    @SerializedName("sensor_list")
    private ArrayList<TrackAndRollSensor> trackAndRollSensorList;

    /**
     * Getter of the sensor list
     * @return the sensor list
     */
    public ArrayList<TrackAndRollSensor> getTrackAndRollSensorList() {
        return trackAndRollSensorList == null
                ? trackAndRollSensorList = new ArrayList<TrackAndRollSensor>()
                : trackAndRollSensorList;
    }

    /**
     * Object ==> application state
     **/
    @SerializedName("application_state")
    private String applicationState;

    /**
     * Getter of the application state
     * @return the application state
     */
    public String getApplicationState() {
        return applicationState == null
                ? applicationState = Constant.APP_STATE_IDLE
                : applicationState;
    }

    /**
     * Setter of the application state
     * @param applicationState : the application state
     */
    public void setApplicationState(String applicationState) {
        this.applicationState = applicationState;
    }


    /**
     * Object ==> account
     **/
    @SerializedName("account")
    private Account account;

    /**
     * Getter of the account
     * @return the account
     */
    public Account getAccount() {
        return account == null
                ? account = new Account()
                : account;
    }

    /**
     * Setter of the account
     * @param account : the account
     */
    public void setAccount(Account account) {
        this.account = account;
    }






}
