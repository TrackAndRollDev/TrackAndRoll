package com.neos.trackandroll.testBluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.neos.trackandroll.R;
import com.neos.trackandroll.utils.LogUtils;
import com.neos.trackandroll.view.activity.AbstractActivity;
import com.neos.trackandroll.view.activity.ServiceActivity;

public class TestBluetoothActivity extends AbstractActivity {

    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;

    private Communication communication;
    private TextView console;
    private EditText bluetoothMessageToSend;
    private View sendMessage;

    private TestBluetoothActivity me;

    private ScrollView scroll;

    /**
     * Process called at the creation of the activity
     *
     * @param savedInstanceState : the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(LogUtils.DEBUG_TAG,"BONJOUR");
        me = this;

        setContentView(R.layout.activity_test_bluetooth);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mapParams.get(ServiceActivity.PARAM_DEVICE_KEY));
        }
        LogUtils.d(LogUtils.DEBUG_TAG,"mapParams.get(ServiceActivity.PARAM_DEVICE_KEY) => "+mapParams.get(ServiceActivity.PARAM_DEVICE_KEY));
        LogUtils.d(LogUtils.DEBUG_TAG,"mapParams.get(ServiceActivity.PARAM_DEVICE_ADDRESS) => "+mapParams.get(ServiceActivity.PARAM_DEVICE_ADDRESS));

        this.communication = Communication.getNewCommunication(this,mapParams.get(ServiceActivity.PARAM_DEVICE_ADDRESS));

        // Get views
        console = (TextView) findViewById(R.id.console) ;
        bluetoothMessageToSend = (EditText) findViewById(R.id.bluetoothMessageToSend) ;
        sendMessage = findViewById(R.id.sendMessage) ;
        scroll = (ScrollView) findViewById(R.id.scroll) ;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null)
            Toast.makeText(this, "Pas de Bluetooth", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Bluetooth Activeyy de la tete aux piey",Toast.LENGTH_SHORT).show();

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
        }



        // Init the listeners
        initListener();
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

    public void initListener() {
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendTextSend(bluetoothMessageToSend.getText().toString());
                communication.sendMessage(bluetoothMessageToSend.getText().toString());
                bluetoothMessageToSend.setText("");
            }
        });
    }

    private void scrollToBottom(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        },100);
    }

    public void appendTextReceived(String text){
        console.append("\r\nMESSAGE RECU : "+text);
        scrollToBottom();
    }

    public void appendTextSend(String text){
        console.append("\r\nMESSAGE SEND : "+text);
        scrollToBottom();
    }

    @Override
    public void onBackPressed() {
        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_LIST_BLUETOOTH);
    }
}
