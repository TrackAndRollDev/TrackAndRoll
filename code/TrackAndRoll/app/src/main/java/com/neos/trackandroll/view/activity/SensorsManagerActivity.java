package com.neos.trackandroll.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.model.sensor.TrackAndRollSensor;
import com.neos.trackandroll.utils.FileUtils;
import com.neos.trackandroll.view.adapter.sensors.SensorItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class SensorsManagerActivity extends AbstractActivityWithToolbar {

    private RecyclerView rvSensorItems;
    private SensorItemAdapter sensorItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateLayout(2, R.layout.activity_sensors_manager, (ViewGroup) findViewById(R.id.activity_sensors_manager));

        rvSensorItems = (RecyclerView) findViewById(R.id.rvSensorItems);

        initRecyclerView();
    }

    private void initRecyclerView() {

        // Init recycler view adapter
        sensorItemAdapter = new SensorItemAdapter(
                getApplicationContext(),
                DataStore.getInstance().getAppConfiguration().getTrackAndRollSensorList()
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

    private void prepareRunningSessionSensorList(){
        ArrayList<TrackAndRollSensor> runningSessionSensorList = new ArrayList<>();
        ArrayList<TrackAndRollSensor> sensorList = DataStore.getInstance().getAppConfiguration().getTrackAndRollSensorList();
        for(int i=0;i<sensorList.size();i++){
            if(!sensorList.get(i).getAllocatedPlayerKey().equals(Constant.SENSOR_NON_ASSIGNED_NAME)){
                runningSessionSensorList.add(sensorList.get(i));
            }
        }
        DataStore.getInstance().setRunningSessionSensorList(runningSessionSensorList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_launch_session:
                prepareRunningSessionSensorList();
                DataStore.getInstance().setBeginSessionTime(Calendar.getInstance());
                FileUtils.saveAppConfiguration(this);
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_RUNNING_SESSION);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sensors_manager_activity_toolbar, menu);
        return true;
    }
}
