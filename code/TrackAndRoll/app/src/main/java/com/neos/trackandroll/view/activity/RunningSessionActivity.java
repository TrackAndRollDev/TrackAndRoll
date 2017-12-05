package com.neos.trackandroll.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.utils.DateUtils;
import com.neos.trackandroll.utils.DialogUtils;
import com.neos.trackandroll.utils.FileUtils;
import com.neos.trackandroll.view.adapter.sensors.SensorItemAdapter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class RunningSessionActivity extends AbstractActivity {

    private RecyclerView rvSensorItems;
    private SensorItemAdapter sensorItemAdapter;
    private TextView tvSessionTime;
    boolean stopTime;
    private Handler handlerTimer;
    private MaterialDialog savingDialog;
    private String newSessionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_running_session);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        tvSessionTime = (TextView) findViewById(R.id.tvSessionTime);
        rvSensorItems = (RecyclerView) findViewById(R.id.rvSensorItems);

        initRecyclerView();

        setScreenFromState();
    }

    private void setScreenFromState(){
        switch (DataStore.getInstance().getAppConfiguration().getApplicationState()){
            case Constant.APP_STATE_IDLE:
                stopTime = false;
                initTimeThread();
                break;

            case Constant.APP_STATE_SAVING:
                stopTime = true;
                tvSessionTime.setText(DateUtils.convertTimeToHourMinSecond(DataStore.getInstance().getBeginSessionTime(),DataStore.getInstance().getEndSessionTime()));
                savingDialog = DialogUtils.displayDialogSavingRunningSession(this);
                // TODO MOCK
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        savingDialog.dismiss();
                        onRunningSessionSaved();
                    }
                },2000);
                break;

            case Constant.APP_STATE_SET_RUNNING_SESSION_NAME:
                stopTime = true;
                tvSessionTime.setText(DateUtils.convertTimeToHourMinSecond(DataStore.getInstance().getBeginSessionTime(),DataStore.getInstance().getEndSessionTime()));
                DialogUtils.displayDialogNameRunningSession(this,DataStore.getInstance().getEndSessionTime());
                break;
        }
    }

    private void initRecyclerView() {

        // Init recycler view adapter
        sensorItemAdapter = new SensorItemAdapter(
                getApplicationContext(),
                DataStore.getInstance().getRunningSessionSensorList()
        );

        // Init recycler view
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setStackFromEnd(true);
        rvSensorItems.setAdapter(sensorItemAdapter);
        rvSensorItems.setHasFixedSize(true);
        rvSensorItems.setLayoutManager(llm);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rvSensorItems.setItemAnimator(itemAnimator);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_stop_session:
                DialogUtils.displayDialogEndSession(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_running_session_activity_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void deleteRunningSession() {
        // TODO communication envoyer fin de session sans sauvegarde
        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_SENSORS_MANAGER);
    }

    @Override
    public void addNewSession(String newSessionName) {
        this.newSessionName = newSessionName;
        DataStore.getInstance().getAppConfiguration().getSessionNameList().add(newSessionName);
        // TODO manage session

        DataStore.getInstance().getAppConfiguration().setApplicationState(Constant.APP_STATE_SAVING);
        setScreenFromState();
    }

    @Override
    public void stopRunningSessionTimeForSaving() {
        stopTime = true;
        DataStore.getInstance().setEndSessionTime(Calendar.getInstance());
        DataStore.getInstance().getAppConfiguration().setApplicationState(Constant.APP_STATE_SET_RUNNING_SESSION_NAME);
        DialogUtils.displayDialogNameRunningSession(this,DataStore.getInstance().getEndSessionTime());
    }

    public void onRunningSessionSaved(){
        mapParams = new HashMap<>();
        mapParams.put(ServiceActivity.PARAM_SESSION_NAME,newSessionName);
        DataStore.getInstance().getAppConfiguration().setApplicationState(Constant.APP_STATE_IDLE);
        FileUtils.saveAppConfiguration(this);
        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_SESSIONS_MANAGER,mapParams);
    }

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            if (!stopTime) {
                tvSessionTime.setText(DateUtils.convertTimeToHourMinSecond(DataStore.getInstance().getBeginSessionTime(),Calendar.getInstance()));
                handlerTimer.postDelayed(timeRunnable,1000);
            }
        }
    };

    private void initTimeThread(){
        new SessionTimeThread().start();
        handlerTimer = new Handler();
        handlerTimer.postDelayed(timeRunnable,1000);
    }

    private class SessionTimeThread extends Thread {

        @Override
        public void run() {
            new Timer().schedule(
                    new TimerTask() {
                        @Override
                            public void run() {
                                if(!stopTime) {
                                    new SessionTimeThread().start();
                                }
                            }
                    }
                    ,1000
            );
        }
    }
}
