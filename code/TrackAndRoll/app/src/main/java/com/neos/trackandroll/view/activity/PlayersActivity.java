package com.neos.trackandroll.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.utils.DialogUtils;
import com.neos.trackandroll.utils.FileUtils;
import com.neos.trackandroll.utils.ImageUtils;
import com.neos.trackandroll.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayersActivity extends AbstractActivityWithToolbar {

    private static final String SCREEN_STATE_NORMAL = "SCREEN_STATE_NORMAL";
    private static final String SCREEN_STATE_SELECTION = "SCREEN_STATE_SELECTION";
    private String screenState;

    private ArrayList<Player> playerList;

    private ArrayList<View> circleViewList;

    private FloatingActionButton fabAddPlayer;
    private FloatingActionButton fabDeletePlayers;
    private FloatingActionButton fabCancelSelection;

    private PlayersActivity me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateLayout(0,R.layout.activity_players,(ViewGroup) findViewById(R.id.activity_players));
        mapParams = new HashMap<>();
        me = this;

        fabAddPlayer = (FloatingActionButton) findViewById(R.id.fabAddPlayer);
        fabDeletePlayers = (FloatingActionButton) findViewById(R.id.fabDeletePlayers);
        fabCancelSelection = (FloatingActionButton) findViewById(R.id.fabCancelSelection);

        playerList = DataStore.getInstance().getPlayerList();

        initViews();
        initListener();
    }

    private void initViews(){
        circleViewList = new ArrayList<>(playerList.size());
        setStateNormal();
        initGridView();
    }

    private void initGridView(){
        GridView gridview = (GridView) findViewById(R.id.gridView);
        GridViewAdapter gridAdapter;
        gridAdapter = new GridViewAdapter(this, R.layout.item_players_activity, playerList);
        gridview.setAdapter(gridAdapter);

        LogUtils.d(LogUtils.DEBUG_TAG,"set adapter");
    }

    private boolean isOnePlayerSelected(){
        for(int i = 0; i< playerList.size(); i++){
            if(playerList.get(i).isSelected()){
                return true;
            }
        }
        return false;
    }

    private void initListener(){
        fabAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> mapParams = new HashMap<String, String>();
                mapParams.put(ServiceActivity.PARAM_ROOT_SCREEN,String.valueOf(ServiceActivity.DISPLAY_ACTIVITY_PLAYERS));
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_CUSTOM_PLAYER,mapParams);
            }
        });
        fabDeletePlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnePlayerSelected()){
                    DialogUtils.displayDialogSureToRemoveSelectedPlayers(me);
                }
            }
        });
        fabCancelSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStateNormal();
            }
        });
    }

    private void setStateNormal(){
        screenState = SCREEN_STATE_NORMAL;
        unselectAllPlayers();
        fabAddPlayer.setVisibility(View.VISIBLE);
        fabCancelSelection.setVisibility(View.INVISIBLE);
        fabDeletePlayers.setVisibility(View.INVISIBLE);
    }

    private void setStateSelection(){
        screenState = SCREEN_STATE_SELECTION;
        fabAddPlayer.setVisibility(View.INVISIBLE);
        fabCancelSelection.setVisibility(View.VISIBLE);
        fabDeletePlayers.setVisibility(View.VISIBLE);
    }

    private void unselectAllPlayers(){
        for(int i = 0; i< playerList.size(); i++){
            playerList.get(i).setSelected(false);
        }
        for(int i=0;i<circleViewList.size();i++){
            circleViewList.get(i).setSelected(false);
        }
    }

    public void removeSelectedPlayers(){
        for(int i = 0; i< playerList.size(); i++){
            if(playerList.get(i).isSelected()){
                DataStore.getInstance().getAppConfiguration().getPlayerMap().remove(playerList.get(i).getPlayerKey());
                playerList.remove(i);
                i--;
            }
        }
        FileUtils.saveAppConfiguration(this);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty_toolbar, menu);
        return true;
    }

    public class GridViewAdapter extends ArrayAdapter {
        private Context context;
        private int layoutResourceId;
        private ArrayList<Player> playerList;

        public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Player> playerList) {
            super(context, layoutResourceId, playerList);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.playerList = playerList;
        }

        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View item = convertView;
            ViewHolder holder = null;

            if (item == null) {
                LayoutInflater inflater = getLayoutInflater();
                item = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();

                holder.icPlayerPhoto = (CircleImageView) item.findViewById(R.id.icMyAccountPhoto);
                holder.viewPlayerWithoutPicture = item.findViewById(R.id.viewPlayerWithoutPicture);
                holder.tvPlayerName = (TextView) item.findViewById(R.id.tvPlayerName);
                holder.tvPlayerNumber = (TextView) item.findViewById(R.id.tvPlayerNumber);

                if(ImageUtils.isFileImage(playerList.get(position).getPathPhoto())){
                    circleViewList.add(holder.icPlayerPhoto);
                }else{
                    circleViewList.add(holder.viewPlayerWithoutPicture);
                }

                item.setTag(holder);
            } else {
                holder = (ViewHolder) item.getTag();
            }

            if(ImageUtils.isFileImage(playerList.get(position).getPathPhoto())) {
                holder.viewPlayerWithoutPicture.setVisibility(View.INVISIBLE);
                holder.icPlayerPhoto.setVisibility(View.VISIBLE);
                ImageUtils.loadPhoto(playerList.get(position).getPathPhoto(), holder.icPlayerPhoto);
                initItemListener(holder.icPlayerPhoto, position);
            }else{
                holder.viewPlayerWithoutPicture.setVisibility(View.VISIBLE);
                holder.icPlayerPhoto.setVisibility(View.INVISIBLE);
                holder.tvPlayerName.setText(playerList.get(position).getLastName() + " " + playerList.get(position).getFirstName());
                holder.tvPlayerNumber.setText(String.valueOf(playerList.get(position).getPlayerNumber()));
                initItemListener(holder.viewPlayerWithoutPicture,position);
            }
            return item;
        }

        private class ViewHolder {
            CircleImageView icPlayerPhoto;
            View viewPlayerWithoutPicture;
            TextView tvPlayerName;
            TextView tvPlayerNumber;
        }
    }

    private void initItemListener(final View view, final int position){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenState.equals(SCREEN_STATE_SELECTION)) {
                    playerList.get(position).setSelected(!playerList.get(position).isSelected());
                    view.setSelected(playerList.get(position).isSelected());
                }
                if (screenState.equals(SCREEN_STATE_NORMAL)) {
                    HashMap<String, String> mapParams = new HashMap<String, String>();
                    mapParams.put(ServiceActivity.PARAM_PLAYER_KEY, playerList.get(position).getPlayerKey());
                    mapParams.put(ServiceActivity.PARAM_ROOT_SCREEN, String.valueOf(ServiceActivity.DISPLAY_ACTIVITY_PLAYERS));
                    finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_PLAYER_PROFILE, mapParams);
                }
            }
        });

        if (screenState.equals(SCREEN_STATE_SELECTION)) {
            view.setSelected(playerList.get(position).isSelected());
        }

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setStateSelection();
                return false;
            }
        });
    }
}
