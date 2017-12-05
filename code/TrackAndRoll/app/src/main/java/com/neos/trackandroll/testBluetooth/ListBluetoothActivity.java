package com.neos.trackandroll.testBluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.neos.trackandroll.R;
import com.neos.trackandroll.utils.LogUtils;
import com.neos.trackandroll.view.activity.AbstractActivity;
import com.neos.trackandroll.view.activity.ServiceActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ListBluetoothActivity extends AbstractActivity {

    private ListView listView;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bluetooth);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listView = (ListView) findViewById(R.id.listView);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // TODO mettre l'adresse que tu veux
        BluetoothDevice mmDevice = mBluetoothAdapter.getRemoteDevice("00:15:83:EF:3B:FC");
        for (Iterator<BluetoothDevice> it = pairedDevices.iterator(); it.hasNext(); ) {
            BluetoothDevice bluetoothDevice = it.next();
            if(bluetoothDevice.getUuids()!=null){
                for(int i=0;i<bluetoothDevice.getUuids().length;i++){
                    LogUtils.d(LogUtils.DEBUG_TAG, "UUID for "+bluetoothDevice.getName()+" => "+bluetoothDevice.getUuids()[i]);
                }
            }
        }
        /*
        if (pairedDevices.contains(mmDevice)) {
            LogUtils.d(LogUtils.DEBUG_TAG, "Click on it !");
            mapParams = new HashMap<>();
            for(int i=0;i<mmDevice.getUuids().length;i++){
                LogUtils.d(LogUtils.DEBUG_TAG, "UUID => "+mmDevice.getUuids()[i]);
            }
            mapParams.put(ServiceActivity.PARAM_DEVICE_KEY, mmDevice.getName());
            finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_TEST_BLUETOOTH,mapParams);
        }*/
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_LOGIN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDeviceToList(final BluetoothDevice device){
        mDeviceList.add(device.getName());
        LogUtils.d(LogUtils.DEBUG_TAG, device.getName() + "\n" + device.getAddress());
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mDeviceList));
        if(device.getUuids()!=null){
            for(int i=0;i<device.getUuids().length;i++){
                LogUtils.d(LogUtils.DEBUG_TAG, "UUID for "+device.getName()+" => "+device.getUuids()[i]);
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mapParams==null){
                    mapParams = new HashMap<String, String>();
                }
                mapParams.put(ServiceActivity.PARAM_DEVICE_KEY, mDeviceList.get(position));
                mapParams.put(ServiceActivity.PARAM_DEVICE_ADDRESS,device.getAddress());
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_TEST_BLUETOOTH,mapParams);
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                addDeviceToList((BluetoothDevice)intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
            }
        }
    };
}