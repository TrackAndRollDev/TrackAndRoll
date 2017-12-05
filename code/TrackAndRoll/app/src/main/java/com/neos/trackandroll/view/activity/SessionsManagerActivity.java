package com.neos.trackandroll.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.utils.DialogUtils;
import com.neos.trackandroll.utils.FileUtils;
import com.neos.trackandroll.view.adapter.sessions.SpinnerSessionArrayAdapter;
import com.neos.trackandroll.view.adapter.sessions.IClickListener;
import com.neos.trackandroll.view.adapter.sessions.RecyclerTouchListener;
import com.neos.trackandroll.view.adapter.sessions.SessionItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;

public class SessionsManagerActivity extends AbstractActivityWithToolbar {

    private RecyclerView rvSessionItems;
    private SessionItemAdapter sessionItemAdapter;
    private String filterSession;
    private int filterSessionPosition;
    private MaterialSpinner spFilterSession;
    private ArrayList<Player> sessionPlayerList;
    private SpinnerSessionArrayAdapter spinnerAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateLayout(1,R.layout.activity_sessions_manager,(ViewGroup) findViewById(R.id.activity_session));

        rvSessionItems = (RecyclerView) findViewById(R.id.rvSessionItems);

        if(mapParams!=null && mapParams.get(ServiceActivity.PARAM_SESSION_NAME)!=null){
            filterSession = mapParams.get(ServiceActivity.PARAM_SESSION_NAME);
            filterSessionPosition = getPositionInList(filterSession);
        }else{
            filterSession = Constant.DEFAULT_SESSION_NAME;
            filterSessionPosition = 0;
        }
        sessionPlayerList = getSessionPlayerList(filterSession);


        initSpinnerView();
        initRecyclerView();
    }

    private void initSpinnerView(){
        // Initializing a String Array
        spinnerAdapter = new SpinnerSessionArrayAdapter(
                this,R.layout.spinner_item,
                DataStore.getInstance().getAppConfiguration().getSessionNameList()
        );
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spFilterSession = (MaterialSpinner) findViewById(R.id.spFilterSession);
        spFilterSession.setAdapter(spinnerAdapter);
        spFilterSession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                filterSession = DataStore.getInstance().getAppConfiguration().getSessionNameList().get(position);
                sessionPlayerList = getSessionPlayerList(filterSession);
                filterSessionPosition = position;
                reinitSessionPlayerList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        spFilterSession.setSelection(filterSessionPosition);
    }

    private int getPositionInList(String filterSession){
        ArrayList<String> sessionNameList = DataStore.getInstance().getAppConfiguration().getSessionNameList();
        for(int i=0;i<sessionNameList.size();i++){
            if(sessionNameList.get(i).equals(filterSession)){
                return i;
            }
        }
        return 0;
    }

    private void reinitSessionPlayerList(){
        sessionItemAdapter = new SessionItemAdapter(
                getApplicationContext(),
                sessionPlayerList,
                filterSession
        );
        rvSessionItems.setAdapter(sessionItemAdapter);
    }

    private ArrayList<Player> getSessionPlayerList(String filterSession){
        ArrayList<Player> sessionPlayerList = new ArrayList<>();
        ArrayList<Player> allPlayerList = DataStore.getInstance().getPlayerList();
        for(int i = 0;i<allPlayerList.size();i++){
            if(allPlayerList.get(i).getPlayerSessionMap().containsKey(filterSession)){
                sessionPlayerList.add(allPlayerList.get(i));
            }
        }
        return sessionPlayerList;
    }

    private void initRecyclerView(){

        // Init recycler view adapter
        sessionItemAdapter = new SessionItemAdapter(
                getApplicationContext(),
                sessionPlayerList,
                filterSession
        );

        // Init recycler view
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setStackFromEnd(true);
        rvSessionItems.setAdapter(sessionItemAdapter);
        rvSessionItems.setHasFixedSize(true);
        rvSessionItems.setLayoutManager(llm);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rvSessionItems.setItemAnimator(itemAnimator);

        rvSessionItems.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvSessionItems, new IClickListener() {
            @Override
            public void onClick(View view, int position, MotionEvent e) {
                HashMap<String,String> mapParams = new HashMap<String, String>();
                mapParams.put(ServiceActivity.PARAM_PLAYER_KEY, DataStore.getInstance().getPlayerList().get(position).getPlayerKey());
                mapParams.put(ServiceActivity.PARAM_ROOT_SCREEN,String.valueOf(ServiceActivity.DISPLAY_ACTIVITY_SESSIONS_MANAGER));
                mapParams.put(ServiceActivity.PARAM_SESSION_NAME,filterSession);
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_PLAYER_PROFILE,mapParams);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("Long Click", "Long Click");
            }
        }));
    }

    @Override
    public void removeSession() {
        DataStore.getInstance().getAppConfiguration().getSessionNameList().remove(filterSession);
        for(int i=0;i<sessionPlayerList.size();i++){
            sessionPlayerList.get(i).getPlayerSessionMap().remove(filterSession);
        }

        filterSession = Constant.DEFAULT_SESSION_NAME;
        filterSessionPosition = 0;
        sessionPlayerList = getSessionPlayerList(filterSession);

        initSpinnerView();
        reinitSessionPlayerList();
        FileUtils.saveAppConfiguration(this);
    }

    @Override
    public void setSessionName(String newSessionName) {
        DataStore.getInstance().getAppConfiguration().getSessionNameList().set(filterSessionPosition,newSessionName);
        for(int i=0;i<sessionPlayerList.size();i++){
            sessionPlayerList.get(i).getPlayerSessionMap().get(filterSession).setSessionName(newSessionName);
            sessionPlayerList.get(i).getPlayerSessionMap().put(newSessionName,sessionPlayerList.get(i).getPlayerSessionMap().get(filterSession));
            sessionPlayerList.get(i).getPlayerSessionMap().remove(filterSession);
        }
        filterSession = newSessionName;
        sessionPlayerList = getSessionPlayerList(filterSession);
        TextView textView = (TextView)spFilterSession.getSelectedView();
        textView.setText(newSessionName);
        FileUtils.saveAppConfiguration(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_delete_session:
                if(!filterSession.equals(Constant.DEFAULT_SESSION_NAME)){
                    DialogUtils.displayDialogDeleteSession(this);
                }else{
                    DialogUtils.displayDialogCannotRemoveGlobalSession(this);
                }
                return true;

            case R.id.action_edit_session:
                if(!filterSession.equals(Constant.DEFAULT_SESSION_NAME)){
                    DialogUtils.displayDialogSetNameSession(this,filterSession);
                }else{
                    DialogUtils.displayDialogCannotCustomGlobalSession(this);
                }
                return true;

            case R.id.action_add_session:
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_SENSORS_MANAGER);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sessions_manager_activity_toolbar, menu);
        return true;
    }
}
