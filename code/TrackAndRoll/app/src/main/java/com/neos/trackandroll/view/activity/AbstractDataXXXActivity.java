package com.neos.trackandroll.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.utils.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractDataXXXActivity extends AbstractActivity {

    private HashMap<String,Player> playerMap;
    private Player currentPlayer;

    private String filterSession;
    private int filterSessionPosition;
    private ArrayList<Player> sessionPlayerList;



    /**
     * Process called at the creation of the activity
     * @param savedInstanceState : the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_speed);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        playerMap = DataStore.getInstance().getAppConfiguration().getPlayerMap();
        currentPlayer = playerMap.get(mapParams.get(ServiceActivity.PARAM_PLAYER_KEY));

        if(mapParams!=null && mapParams.get(ServiceActivity.PARAM_SESSION_NAME)!=null){
            filterSession = mapParams.get(ServiceActivity.PARAM_SESSION_NAME);
            filterSessionPosition = getPositionInList(filterSession);
        }else{
            filterSession = Constant.DEFAULT_SESSION_NAME;
            filterSessionPosition = 0;
        }
        sessionPlayerList = getSessionPlayerList(filterSession);

        initListener();
    }


    /**
     * Process called to init the activity listener
     */
    private void initListener() {

    }


    /**
     * Process called when an item of the toolbar has been selected
     * @param item : the item selected
     * @return boolean Return false to allow normal menu processing to
     *          proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_delete_player_data:
                DialogUtils.displayDialogDeletePlayerData(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_data_xxx_activity_toolbar, menu);
        return true;
    }


    /**
     * Process called when the user press the back button of the android device
     */
    @Override
    public void onBackPressed() {
        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_PLAYER_PROFILE,mapParams);
    }



    public void removePlayerData(){

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


}
