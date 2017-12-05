package com.neos.trackandroll.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.model.player.session.Session;
import com.neos.trackandroll.utils.DataUtils;
import com.neos.trackandroll.utils.DialogUtils;
import com.neos.trackandroll.utils.FileUtils;
import com.neos.trackandroll.utils.ImageUtils;
import com.neos.trackandroll.utils.LogUtils;
import com.neos.trackandroll.view.adapter.sessions.SpinnerSessionArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.ganfra.materialspinner.MaterialSpinner;

public class PlayerProfileActivity extends AbstractActivity {

    private HashMap<String, Player> playerMap;
    private Player currentPlayer;

    private CircleImageView icPlayerPhoto;

    private TextView tvPlayerAge;
    private TextView tvPlayerPoste;
    private TextView tvPlayerNumber;
    private TextView tvPlayerDataSpeed;
    private TextView tvPlayerDataDistance;
    private TextView tvPlayerDataTime;
    private TextView tvPlayerDataHeartBeat;
    private TextView tvPlayerDataEnergy;
    private FloatingActionButton fabDeletePlayerData;

    private View layoutDataSpeed;
    private View layoutDataDistance;
    private View layoutDataTime;
    private View layoutDataHeartBeat;
    private View layoutDataEnergy;

    ArrayList<String> sessionNameList;
    private String filterSession;
    private int filterSessionPosition;
    private MaterialSpinner spFilterSession;
    private SpinnerSessionArrayAdapter spinnerAdapter;

    private AbstractActivity me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        me = this;

        playerMap = DataStore.getInstance().getAppConfiguration().getPlayerMap();
        currentPlayer = playerMap.get(mapParams.get(ServiceActivity.PARAM_PLAYER_KEY));

        icPlayerPhoto = (CircleImageView) findViewById(R.id.icMyAccountPhoto);
        tvPlayerAge = (TextView) findViewById(R.id.tvPlayerAge);
        tvPlayerPoste = (TextView) findViewById(R.id.tvPlayerPoste);
        tvPlayerNumber = (TextView) findViewById(R.id.tvPlayerNumber);
        tvPlayerDataSpeed = (TextView) findViewById(R.id.tvPlayerDataSpeed);
        tvPlayerDataDistance = (TextView) findViewById(R.id.tvPlayerDataDistance);
        tvPlayerDataTime = (TextView) findViewById(R.id.tvPlayerDataTime);
        tvPlayerDataHeartBeat = (TextView) findViewById(R.id.tvPlayerDataHeartBeat);
        tvPlayerDataEnergy = (TextView) findViewById(R.id.tvPlayerDataEnergy);
        fabDeletePlayerData = (FloatingActionButton) findViewById(R.id.fabDeletePlayerData);

        layoutDataSpeed = findViewById(R.id.layoutDataSpeed);
        layoutDataDistance = findViewById(R.id.layoutDataDistance);
        layoutDataTime = findViewById(R.id.layoutDataTime);
        layoutDataHeartBeat = findViewById(R.id.layoutDataHeartBeat);
        layoutDataEnergy = findViewById(R.id.layoutDataEnergy);

        if(mapParams != null && mapParams.get(ServiceActivity.PARAM_SESSION_NAME) != null){
            filterSession = mapParams.get(ServiceActivity.PARAM_SESSION_NAME);
        } else {
            filterSession = Constant.DEFAULT_SESSION_NAME;
        }

        sessionNameList = DataUtils.convertSetSessionToStringList(currentPlayer.getPlayerSessionMap().keySet());
        filterSessionPosition = getFilterSessionPosition(filterSession);

        initSpinnerView();
        initProfileView();
        setDataFromSession(currentPlayer.getPlayerSessionMap().get(filterSession));
        initActionBar();
        initListener();
    }

    private void initListener() {
        fabDeletePlayerData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSession.equals(Constant.DEFAULT_SESSION_NAME)) {
                    DialogUtils.displayDialogCannotRemoveGlobalSession(me);
                } else {
                    DialogUtils.displayDialogDeletePlayerData(me);
                }
            }
        });

        layoutDataSpeed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_DATA_SPEED,mapParams);
            }
        });

        layoutDataDistance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_DATA_DISTANCE,mapParams);
            }
        });

        layoutDataTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_DATA_TIME,mapParams);
            }
        });

        layoutDataHeartBeat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_DATA_HEART_BEAT,mapParams);
            }
        });

        layoutDataEnergy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_DATA_ENERGY,mapParams);
            }
        });
    }

    @Override
    public void removePlayerSession() {
        currentPlayer.getPlayerSessionMap().remove(filterSession);
        sessionNameList = DataUtils.convertSetSessionToStringList(currentPlayer.getPlayerSessionMap().keySet());
        filterSession = Constant.DEFAULT_SESSION_NAME;
        filterSessionPosition = 0;
        initSpinnerView();
        setDataFromSession(currentPlayer.getPlayerSessionMap().get(filterSession));
        FileUtils.saveAppConfiguration(this);
    }

    private int getFilterSessionPosition(String filterSession) {
        for (int i = 0; i < sessionNameList.size(); i++) {
            if (sessionNameList.get(i).equals(filterSession)) {
                return i;
            }
        }
        return 0;
    }

    private void initSpinnerView() {
        // Initializing a String Array
        spinnerAdapter = new SpinnerSessionArrayAdapter(
                this, R.layout.spinner_item,
                sessionNameList
        );
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spFilterSession = (MaterialSpinner) findViewById(R.id.spFilterSession);
        spFilterSession.setAdapter(spinnerAdapter);
        spFilterSession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selectedItemView != null) {
                    filterSession = ((TextView) selectedItemView).getText().toString();
                    filterSessionPosition = position;
                    setDataFromSession(currentPlayer.getPlayerSessionMap().get(filterSession));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        spFilterSession.setSelection(filterSessionPosition);
    }

    private void initProfileView() {
        if (ImageUtils.isFileImage(currentPlayer.getPathPhoto())) {
            ImageUtils.loadPhoto(currentPlayer.getPathPhoto(), icPlayerPhoto);
        } else {
            icPlayerPhoto.setImageResource(R.drawable.ic_avatar);
        }
        tvPlayerAge.setText(String.format(
                this.getResources().getString(R.string.player_profile_format_age),
                currentPlayer.getAge()
        ));
        tvPlayerPoste.setText(currentPlayer.getPostName());
        tvPlayerNumber.setText(String.format(
                this.getResources().getString(R.string.player_profile_format_number),
                currentPlayer.getPlayerNumber()
        ));
    }

    private void setDataFromSession(Session session) {
        tvPlayerDataSpeed.setText(String.format(
                this.getResources().getString(R.string.player_profile_format_speed),
                session.getPlayerSessionData().getPlayerSpeed()
        ));
        tvPlayerDataDistance.setText(String.format(
                this.getResources().getString(R.string.player_profile_format_distance_km),
                session.getPlayerSessionData().getPlayerDistance()
        ));
        /* TODO
        tvPlayerDataTime.setText(String.format(
                this.getResources().getString(R.string.player_profile_format_),
                player.getPlayerSessionData().getPlayerTime()
        ));
        */
        tvPlayerDataTime.setText("todo");
        tvPlayerDataHeartBeat.setText(String.format(
                this.getResources().getString(R.string.player_profile_format_heart_beat),
                session.getPlayerSessionData().getPlayerHeartBeat()
        ));
        tvPlayerDataEnergy.setText(String.format(
                this.getResources().getString(R.string.player_profile_format_energy),
                session.getPlayerSessionData().getPlayerEnergy()
        ));
    }

    private void initActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(
                    currentPlayer.getFirstName()
                            + " "
                            + currentPlayer.getLastName()
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_delete_player:
                DialogUtils.displayDialogSureToRemovePlayer(this);
                return true;

            case R.id.action_edit_player:
                mapParams.put(ServiceActivity.PARAM_CHILD_SCREEN, String.valueOf(ServiceActivity.DISPLAY_ACTIVITY_PLAYER_PROFILE));
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_CUSTOM_PLAYER, mapParams);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void removePlayer() {
        super.manageRemovePlayer(currentPlayer);
        finishWithResult(Integer.valueOf(mapParams.get(ServiceActivity.PARAM_ROOT_SCREEN)), mapParams);
    }

    @Override
    public void onBackPressed() {
        LogUtils.d(LogUtils.DEBUG_TAG, "onBackPressed => KEY = " + ServiceActivity.PARAM_ROOT_SCREEN + " VALUE => " + Integer.valueOf(mapParams.get(ServiceActivity.PARAM_ROOT_SCREEN)));
        finishWithResult(Integer.valueOf(mapParams.get(ServiceActivity.PARAM_ROOT_SCREEN)), mapParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_player_profile_activity_toolbar, menu);
        return true;
    }
}
