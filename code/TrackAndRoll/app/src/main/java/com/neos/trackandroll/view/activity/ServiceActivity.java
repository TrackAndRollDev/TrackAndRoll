package com.neos.trackandroll.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.neos.trackandroll.utils.LogUtils;

public class ServiceActivity extends AbstractActivity {

    private static final int REQUEST_CODE_ACTIVITY_SPLASH               =200;
    private static final int REQUEST_CODE_ACTIVITY_LOGIN                =201;
    private static final int REQUEST_CODE_ACTIVITY_PLAYERS              =202;
    private static final int REQUEST_CODE_ACTIVITY_SENSORS_MANAGER      =203;
    private static final int REQUEST_CODE_ACTIVITY_SESSIONS_MANAGER     =204;
    private static final int REQUEST_CODE_ACTIVITY_HELP                 =205;
    private static final int REQUEST_CODE_ACTIVITY_CUSTOM_PLAYER        =206;
    private static final int REQUEST_CODE_ACTIVITY_MY_ACCOUNT           =207;
    private static final int REQUEST_CODE_ACTIVITY_PLAYER_PROFILE       =208;
    private static final int REQUEST_CODE_ACTIVITY_RUNNING_SESSION      =209;
    private static final int REQUEST_CODE_ACTIVITY_DATA_SPEED           =210;
    private static final int REQUEST_CODE_ACTIVITY_DATA_DISTANCE        =211;
    private static final int REQUEST_CODE_ACTIVITY_DATA_TIME            =212;
    private static final int REQUEST_CODE_ACTIVITY_DATA_HEART_BEAT      =213;
    private static final int REQUEST_CODE_ACTIVITY_DATA_ENERGY          =214;

    private static final int REQUEST_CODE_ACTIVITY_TEST_BLUETOOTH       =240;
    private static final int REQUEST_CODE_ACTIVITY_LIST_BLUETOOTH       =241;


    public static final int DISPLAY_ACTIVITY_LOGIN                      = 1;
    public static final int DISPLAY_ACTIVITY_PLAYERS                    = 2;
    public static final int DISPLAY_ACTIVITY_SENSORS_MANAGER            = 3;
    public static final int DISPLAY_ACTIVITY_SESSIONS_MANAGER           = 4;
    public static final int DISPLAY_ACTIVITY_HELP                       = 5;
    public static final int DISPLAY_ACTIVITY_CUSTOM_PLAYER              = 6;
    public static final int DISPLAY_ACTIVITY_MY_ACCOUNT                 = 7;
    public static final int DISPLAY_ACTIVITY_PLAYER_PROFILE             = 8;
    public static final int DISPLAY_ACTIVITY_RUNNING_SESSION            = 9;
    public static final int DISPLAY_ACTIVITY_DATA_SPEED                 = 10;
    public static final int DISPLAY_ACTIVITY_DATA_DISTANCE              = 11;
    public static final int DISPLAY_ACTIVITY_DATA_TIME                  = 12;
    public static final int DISPLAY_ACTIVITY_DATA_HEART_BEAT            = 13;
    public static final int DISPLAY_ACTIVITY_DATA_ENERGY                = 14;

    public static final String PARAM_PLAYER_KEY                         = "PARAM_PLAYER_KEY";
    public static final String PARAM_SESSION_NAME                       = "PARAM_SESSION_NAME";
    public static final String PARAM_ROOT_SCREEN                        = "PARAM_ROOT_SCREEN";
    public static final String PARAM_CHILD_SCREEN                       = "PARAM_CHILD_SCREEN";

    public static final String ACTION_XXX                   = "ACTION_XXX";
    public static final String ACTION_YYY                   = "ACTION_YYY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null) {
            Intent i = new Intent(this, SplashActivity.class);
            startActivityForResult(i, REQUEST_CODE_ACTIVITY_SPLASH);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.d(LogUtils.DEBUG_TAG,"ResultCode ==> "+requestCode+" ResultCode ==> "+resultCode);
        switch(requestCode){
            case REQUEST_CODE_ACTIVITY_SPLASH:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_LOGIN:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_PLAYERS:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_SENSORS_MANAGER:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_SESSIONS_MANAGER:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_HELP:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_CUSTOM_PLAYER:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_MY_ACCOUNT:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_PLAYER_PROFILE:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_DATA_SPEED:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_DATA_DISTANCE:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_DATA_TIME:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_DATA_HEART_BEAT:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_DATA_ENERGY:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_RUNNING_SESSION:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_TEST_BLUETOOTH:
                manageEndActivity(requestCode, resultCode, data);
                break;

            case REQUEST_CODE_ACTIVITY_LIST_BLUETOOTH:
                manageEndActivity(requestCode, resultCode, data);
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void manageEndActivity(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            LogUtils.d(LogUtils.DEBUG_TAG,"resultCode == RESULT_OK");
            finish();
        }else{
            LogUtils.d(LogUtils.DEBUG_TAG,"resultCode == " + resultCode);
            displayScreen(resultCode, resultCode, data);
        }
    }

    private void displayScreen(int requestCode, int resultCode, Intent data){
        switch(resultCode){
            case DISPLAY_ACTIVITY_LOGIN:
                startActivityWithParams(LoginActivity.class,REQUEST_CODE_ACTIVITY_LOGIN,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_PLAYERS:
                startActivityWithParams(PlayersActivity.class,REQUEST_CODE_ACTIVITY_PLAYERS,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_SENSORS_MANAGER:
                startActivityWithParams(SensorsManagerActivity.class,REQUEST_CODE_ACTIVITY_SENSORS_MANAGER,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_SESSIONS_MANAGER:
                startActivityWithParams(SessionsManagerActivity.class, REQUEST_CODE_ACTIVITY_SESSIONS_MANAGER,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_HELP:
                startActivityWithParams(HelpActivity.class,REQUEST_CODE_ACTIVITY_HELP,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_CUSTOM_PLAYER:
                startActivityWithParams(CustomPlayerActivity.class, REQUEST_CODE_ACTIVITY_CUSTOM_PLAYER,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_MY_ACCOUNT:
                startActivityWithParams(MyAccountActivity.class,REQUEST_CODE_ACTIVITY_MY_ACCOUNT,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_PLAYER_PROFILE:
                startActivityWithParams(PlayerProfileActivity.class,REQUEST_CODE_ACTIVITY_PLAYER_PROFILE,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_DATA_SPEED:
                startActivityWithParams(DataSpeedActivity.class,REQUEST_CODE_ACTIVITY_DATA_SPEED,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_DATA_DISTANCE:
                startActivityWithParams(DataDistanceActivity.class,REQUEST_CODE_ACTIVITY_DATA_DISTANCE,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_DATA_TIME:
                startActivityWithParams(DataTimeActivity.class,REQUEST_CODE_ACTIVITY_DATA_TIME,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_DATA_HEART_BEAT:
                startActivityWithParams(DataHeartBeatActivity.class,REQUEST_CODE_ACTIVITY_DATA_HEART_BEAT,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_DATA_ENERGY:
                startActivityWithParams(DataEnergyActivity.class,REQUEST_CODE_ACTIVITY_DATA_ENERGY,
                        requestCode, resultCode, data);
                break;

            case DISPLAY_ACTIVITY_RUNNING_SESSION:
                startActivityWithParams(RunningSessionActivity.class,REQUEST_CODE_ACTIVITY_RUNNING_SESSION,
                        requestCode, resultCode, data);
                break;

            default:
                finish();
                break;
        }
    }

    private void startActivityWithParams(Class myClass, int toRequestCode,int fromRequestCode, int resultCode, Intent data){
        Bundle extras = data.getExtras();
        Intent intent = new Intent(this, myClass);
        if (extras != null) {
            for (String key : extras.keySet()) {
                String value = (String) extras.get(key);
                intent.putExtra(key, value);
                LogUtils.d(LogUtils.DEBUG_TAG,"SERVICE EXTRA KEY ==>" + key + " VALUE ==> " + extras.get(key));
            }
        }
        setResult(resultCode, intent);
        LogUtils.d(LogUtils.DEBUG_TAG,"Start activity = " + myClass);
        startActivityForResult(intent,toRequestCode);
    }
}
