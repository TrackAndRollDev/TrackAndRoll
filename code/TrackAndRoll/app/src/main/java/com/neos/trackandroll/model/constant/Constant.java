package com.neos.trackandroll.model.constant;

public class Constant {

    // Constant use for the displayed value of session and non assigned sensor
    public static final String DEFAULT_SESSION_NAME = "GLOBAL";
    public static final String SENSOR_NON_ASSIGNED_NAME = "Non-Assigné";

    // Account default values
    public static final String ACCOUNT_LAST_NAME               = "LAST_NAME";
    public static final String ACCOUNT_FIRST_NAME              = "FIRST_NAME";
    public static final String ACCOUNT_EMAIL                   = "EMAIL";
    public static final String ACCOUNT_USERNAME                = "ADMIN";
    public static final String ACCOUNT_PASSWORD                = "ADMIN";

    // The different state of the sensor
    public static final String SENSOR_STATE_CONNECTED           = "CONNECTED";
    public static final String SENSOR_STATE_WARNING             = "WARNING";
    public static final String SENSOR_STATE_CONNECTION_PROGRESS = "CONNECTION_PROGRESS";
    public static final String SENSOR_STATE_ERROR               = "ERROR";

    // The different state of the app
    public static final String APP_STATE_IDLE                       = "APP_STATE_IDLE";
    public static final String APP_STATE_SET_RUNNING_SESSION_NAME   = "APP_STATE_SET_RUNNING_SESSION_NAME";
    public static final String APP_STATE_SAVING                     = "APP_STATE_SAVING";

    // The different time use in the application
    public static final int TIME_SPLASH_SCREEN = 2000;
    public static final int TIME_ANIMATION_FADE_OUT = 500;


    // Constant of the communication
    public static final int SERVER_PORT = 50000;
    public static final String SERVER_ADDRESS = "192.168.0.45";
    public static final int TW_CONNECTION_SOCKET = 10000;
}
