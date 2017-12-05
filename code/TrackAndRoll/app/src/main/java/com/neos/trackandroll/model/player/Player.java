package com.neos.trackandroll.model.player;

import com.google.gson.annotations.SerializedName;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.model.player.session.Session;

import java.util.HashMap;

public class Player {

    @SerializedName("player_first_name")
    private String firstName;

    @SerializedName("player_last_name")
    private String lastName;

    @SerializedName("player_associated_captor")
    private String associatedCaptor;

    @SerializedName("player_path_photo")
    private String pathPhoto;

    @SerializedName("player_post_name")
    private String postName;

    @SerializedName("player_number")
    private int playerNumber;

    @SerializedName("player_age")
    private int age;

    @SerializedName("player_key")
    private String playerKey;

    @SerializedName("player_session_map")
    private HashMap<String,Session> playerSessionMap;

    private boolean selected;

    public Player(String firstName, String lastName, int age, String postName, int playerNumber, String pathPhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.postName = postName;
        this.playerNumber = playerNumber;
        this.pathPhoto = pathPhoto;
        this.playerKey = getThePlayerKey();
        this.playerSessionMap = new HashMap<>();
        this.playerSessionMap.put(Constant.DEFAULT_SESSION_NAME,new Session(Constant.DEFAULT_SESSION_NAME));
        selected = false;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAssociatedCaptor() {
        return associatedCaptor;
    }

    public void setAssociatedCaptor(String associatedCaptor) {
        this.associatedCaptor = associatedCaptor;
    }

    public String getPathPhoto() {
        return pathPhoto == null
                ? ""
                : pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public HashMap<String,Session> getPlayerSessionMap() {
        return playerSessionMap;
    }

    public void setPlayerSessionMap(HashMap<String,Session> playerSessionMap) {
        this.playerSessionMap = playerSessionMap;
    }

    public String getPlayerKey() {
        return playerKey;
    }

    public void setPlayerKey() {
        this.playerKey = getThePlayerKey();
    }

    private String getThePlayerKey(){
        return lastName+"____"+firstName;
    }
}
