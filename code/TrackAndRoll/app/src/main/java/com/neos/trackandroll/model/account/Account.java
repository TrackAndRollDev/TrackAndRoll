package com.neos.trackandroll.model.account;

import com.google.gson.annotations.SerializedName;
import com.neos.trackandroll.model.constant.Constant;

import java.util.HashMap;

public class Account {
    @SerializedName("player_last_name")
    private String lastName;

    @SerializedName("player_first_name")
    private String firstName;

    @SerializedName("player_username")
    private String username;

    @SerializedName("player_email")
    private String email;

    @SerializedName("player_password")
    private String password;

    @SerializedName("player_path_photo")
    private String pathPhoto;

    public Account(String firstName, String lastName, String username, String email, String password, String pathPhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.pathPhoto = pathPhoto;
    }

    public Account(){
        this.firstName = Constant.ACCOUNT_FIRST_NAME;
        this.lastName = Constant.ACCOUNT_LAST_NAME;
        this.username = Constant.ACCOUNT_USERNAME;
        this.email = Constant.ACCOUNT_EMAIL;
        this.password = Constant.ACCOUNT_PASSWORD;
        this.pathPhoto = "";
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPathPhoto() {
        return pathPhoto == null
                ? ""
                : pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }

}
