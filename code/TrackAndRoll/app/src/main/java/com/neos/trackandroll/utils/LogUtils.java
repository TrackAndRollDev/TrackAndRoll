package com.neos.trackandroll.utils;

import android.util.Log;

public class LogUtils {

    public static final String DEBUG_TAG = "DEBUG_TAG";

    public static void d(String tag, String message){
        Log.d(tag,message);
    }

    public static void e(String tag, String message){
        Log.e(tag,message);
    }

    public static void e(String tag, String message,Throwable throwable){
        Log.e(tag,message,throwable);
    }
}

