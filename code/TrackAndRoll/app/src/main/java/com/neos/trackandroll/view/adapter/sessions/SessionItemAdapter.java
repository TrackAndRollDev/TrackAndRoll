package com.neos.trackandroll.view.adapter.sessions;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.constant.Fonts;
import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.utils.DateUtils;
import com.neos.trackandroll.utils.ImageUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SessionItemAdapter extends RecyclerView.Adapter<SessionItemAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Player> playerList;
    private String filterSession;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView icPlayerPhoto;
        View viewPlayerWithoutPicture;
        TextView tvPlayerNumber;
        TextView tvPlayerFirstName;
        TextView tvPlayerLastName;
        TextView tvPlayerSpeed;
        TextView tvPlayerDistance;
        TextView tvPlayerTime;
        TextView tvPlayerHeartBeat;

        public MyViewHolder(View view) {
            super(view);
            icPlayerPhoto = (CircleImageView) view.findViewById(R.id.icMyAccountPhoto);
            viewPlayerWithoutPicture = view.findViewById(R.id.viewPlayerWithoutPicture);
            tvPlayerNumber = (TextView) view.findViewById(R.id.tvPlayerNumber);
            tvPlayerFirstName = (TextView) view.findViewById(R.id.tvAccountFirstName);
            tvPlayerLastName = (TextView) view.findViewById(R.id.tvAccountLastName);
            tvPlayerSpeed = (TextView) view.findViewById(R.id.tvPlayerSpeed);
            tvPlayerDistance = (TextView) view.findViewById(R.id.tvPlayerDistance);
            tvPlayerTime = (TextView) view.findViewById(R.id.tvPlayerTime);
            tvPlayerHeartBeat = (TextView) view.findViewById(R.id.tvPlayerHeartBeat);
        }

    }

    public SessionItemAdapter(Context context, ArrayList<Player> playerList,String filterSession) {
        this.context=context;
        this.playerList=playerList;
        this.filterSession = filterSession;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session_player, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvPlayerNumber.setTypeface(Typeface.createFromAsset(context.getAssets(), Fonts.ROBOTO_LIGHT));
        holder.tvPlayerFirstName.setTypeface(Typeface.createFromAsset(context.getAssets(), Fonts.ROBOTO_LIGHT));
        holder.tvPlayerLastName.setTypeface(Typeface.createFromAsset(context.getAssets(), Fonts.ROBOTO_LIGHT));
        holder.tvPlayerSpeed.setTypeface(Typeface.createFromAsset(context.getAssets(), Fonts.ROBOTO_LIGHT));
        holder.tvPlayerDistance.setTypeface(Typeface.createFromAsset(context.getAssets(), Fonts.ROBOTO_LIGHT));
        holder.tvPlayerTime.setTypeface(Typeface.createFromAsset(context.getAssets(), Fonts.ROBOTO_LIGHT));
        holder.tvPlayerHeartBeat.setTypeface(Typeface.createFromAsset(context.getAssets(), Fonts.ROBOTO_LIGHT));

        if(ImageUtils.isFileImage(playerList.get(position).getPathPhoto())) {
            holder.viewPlayerWithoutPicture.setVisibility(View.INVISIBLE);
            holder.icPlayerPhoto.setVisibility(View.VISIBLE);
            ImageUtils.loadPhoto(playerList.get(position).getPathPhoto(), holder.icPlayerPhoto);
        }else{
            holder.viewPlayerWithoutPicture.setVisibility(View.VISIBLE);
            holder.icPlayerPhoto.setVisibility(View.INVISIBLE);
            holder.tvPlayerNumber.setText(String.valueOf(playerList.get(position).getPlayerNumber()));
        }

        holder.tvPlayerFirstName.setText(playerList.get(position).getFirstName());
        holder.tvPlayerLastName.setText(playerList.get(position).getLastName());

        holder.tvPlayerSpeed.setText(String.format(
                context.getResources().getString(R.string.player_profile_format_speed),
                playerList.get(position).getPlayerSessionMap().get(filterSession).getPlayerSessionData().getPlayerSpeed()
        ));
        holder.tvPlayerDistance.setText(String.format(
                context.getResources().getString(R.string.player_profile_format_distance_m),
                playerList.get(position).getPlayerSessionMap().get(filterSession).getPlayerSessionData().getPlayerDistance()
        ));
        holder.tvPlayerTime.setText(DateUtils.convertSecondsToFormatApp(playerList.get(position).getPlayerSessionMap().get(filterSession).getPlayerSessionData().getPlayerTime()));
        holder.tvPlayerHeartBeat.setText(String.format(
                context.getResources().getString(R.string.player_profile_format_heart_beat),
                playerList.get(position).getPlayerSessionMap().get(filterSession).getPlayerSessionData().getPlayerHeartBeat()
        ));
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

}