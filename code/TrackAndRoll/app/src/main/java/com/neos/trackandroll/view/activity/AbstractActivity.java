package com.neos.trackandroll.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.utils.DialogUtils;
import com.neos.trackandroll.utils.LogUtils;

import java.util.HashMap;

public abstract class AbstractActivity extends AppCompatActivity {

    protected boolean drawerOpen;
    protected HashMap<String, String> mapParams;

    private boolean isRegistered = false;

    /**
     * Process called at the creation of the activity that extends this class
     * @param savedInstanceState : the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mapParams = new HashMap<>();
            for (String key : extras.keySet()) {
                try {
                    LogUtils.d(LogUtils.DEBUG_TAG, "KEY => " + key + " VALUE => " + extras.get(key));
                    mapParams.put(key, (String) extras.get(key));
                } catch (ClassCastException e) {
                    LogUtils.e(LogUtils.DEBUG_TAG, "Exception in onCreate AbstractActivity => ", e);
                }
            }
        }
    }

    /**
     * Process called by the user to finish the activity
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        drawerOpen = false;
    }

    /**
     * Process called when an activity will be started. This process override the super
     * class in order to set the animation of the transition
     * @param intent : the intent to change the activity
     * @param requestCode : the request code of the activity created
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * Process called to finish the activity with a result and params
     * @param resultCode : the result code of the activity that will be destroyed
     * @param mapParams : the map params to pass between activities
     */
    protected void finishWithResult(int resultCode, HashMap<String, String> mapParams) {
        Intent intent = new Intent();
        if (mapParams != null) {
            for (String key : mapParams.keySet()) {
                String value = mapParams.get(key);
                intent.putExtra(key, value);
            }
        }
        setResult(resultCode, intent);
        finish();
    }

    /**
     * Process called to finish the activity with a result
     * @param resultCode : the result code of the activity that will be destroyed
     */
    protected void finishWithResult(int resultCode) {
        Intent intent = new Intent();
        setResult(resultCode, intent);
        finish();
    }

    /**
     * Process called to manage the event when a player is removed of the app
     * @param player : the player to remove
     */
    protected void manageRemovePlayer(Player player) {
        DataStore.getInstance().getAppConfiguration().getPlayerMap().remove(player.getPlayerKey());
        DataStore.getInstance().getPlayerList().remove(player);
    }

    /**
     * Process called to close the drawer of the activity
     */
    protected void closeDrawer() {

    }

    /**
     * Process called to remove the player of the activity
     */
    public void removePlayer() {

    }

    /**
     * Process called to remove the session of the activity
     */
    public void removeSession() {

    }

    /**
     * Process called to set the session name
     * @param newSessionName : the new session name
     */
    public void setSessionName(String newSessionName) {

    }

    /**
     * Process called to remove the player session
     */
    public void removePlayerSession() {

    }

    /**
     * Process called to delete the running session
     */
    public void deleteRunningSession() {

    }

    /**
     * Process called to stop the running session time
     */
    public void stopRunningSessionTimeForSaving() {

    }

    /**
     * Process called to add a new session
     * @param newSessionName : the new session
     */
    public void addNewSession(String newSessionName) {

    }

    /**
     * Process called to quit the application with the right result code
     */
    public void quitApplication() {
        finishWithResult(RESULT_OK);
    }

    /**
     * Process called when the user click on the back of his device
     */
    @Override
    public void onBackPressed() {
        if (drawerOpen) {
            closeDrawer();
        } else {
            DialogUtils.displayDialogSureToQuitApp(this);
        }
    }
}