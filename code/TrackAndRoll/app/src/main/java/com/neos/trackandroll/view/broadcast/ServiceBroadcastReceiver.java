package com.neos.trackandroll.view.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.neos.trackandroll.utils.LogUtils;
import com.neos.trackandroll.view.activity.ServiceActivity;

public class ServiceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.d(LogUtils.DEBUG_TAG, "Broadcast Receive => " + action);
        if (intent.getAction().equals(ServiceActivity.ACTION_XXX)) {
            // TODO
        } else if (ServiceActivity.ACTION_YYY.equals(intent.getAction())) {
            // TODO
        }
    }
}