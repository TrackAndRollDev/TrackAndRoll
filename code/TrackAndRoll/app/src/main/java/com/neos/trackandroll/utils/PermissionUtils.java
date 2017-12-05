package com.neos.trackandroll.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class PermissionUtils {

    // The request storage permission
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 12;

    /**
     * Process called to check storage permissions
     * @param activity : the activity
     * @return if user has the permission
     */
    public static boolean checkStoragePermissions(FragmentActivity activity){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                ) {


            // Request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                    },
                    MY_PERMISSIONS_REQUEST_STORAGE);
            return false;
        }
        return true;
    }

    public static boolean checkBluetoothPermission(AppCompatActivity activity){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_ADMIN)!= PackageManager.PERMISSION_GRANTED
                ) {


            // Request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN,
                    },
                    MY_PERMISSIONS_REQUEST_STORAGE);
            return false;
        }
        return true;
    }

    /**
     * Process called to check storage permissions
     * @param context : the context of the activity
     * @return if user has the permission
     */
    public static boolean checkStoragePermissions(Context context){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context,Manifest.permission.VIBRATE)!= PackageManager.PERMISSION_GRANTED
                ) {
            return false;
        }
        return true;
    }
}