package com.neos.trackandroll.view.activity;

import android.os.Bundle;
import android.os.Handler;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.constant.Constant;

import java.util.HashMap;

public class SplashActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set splash Layout
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> map = new HashMap<>();
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_LOGIN,map);
            }
        }, Constant.TIME_SPLASH_SCREEN);
    }
}
