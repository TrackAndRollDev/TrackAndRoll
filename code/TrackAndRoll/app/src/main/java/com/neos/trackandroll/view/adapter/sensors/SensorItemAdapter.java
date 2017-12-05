package com.neos.trackandroll.view.adapter.sensors;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.model.constant.Fonts;
import com.neos.trackandroll.model.sensor.TrackAndRollSensor;
import com.neos.trackandroll.utils.DataUtils;
import com.neos.trackandroll.utils.ImageUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.ganfra.materialspinner.MaterialSpinner;

public class SensorItemAdapter extends RecyclerView.Adapter<SensorItemAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<TrackAndRollSensor> trackAndRollSensorList;
    private ArrayList<String> playerStringList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView icPlayerPhoto;
        private View viewPlayerWithoutPicture;
        private TextView tvPlayerNumber;
        private TextView tvSensorNumber;
        private TextView tvSensorId;
        private MaterialSpinner spPlayerList;
        private View imgSensorStateBackground;
        private ImageView imgSensorStateSrc;
        private ProgressBar pbStateConnectionProgress;

        private SpinnerPlayerArrayAdapter spinnerAdapter;
        private int selectedPlayerPositionPlusOne;

        public MyViewHolder(View view) {
            super(view);

            icPlayerPhoto = (CircleImageView) view.findViewById(R.id.icMyAccountPhoto);
            viewPlayerWithoutPicture = view.findViewById(R.id.viewPlayerWithoutPicture);
            tvPlayerNumber = (TextView) view.findViewById(R.id.tvPlayerNumber);
            tvSensorNumber = (TextView) view.findViewById(R.id.tvSensorNumber);
            tvSensorId = (TextView) view.findViewById(R.id.tvSensorId);
            spPlayerList = (MaterialSpinner) view.findViewById(R.id.spPlayerList);
            imgSensorStateBackground = (View) view.findViewById(R.id.imgSensorStateBackground);
            imgSensorStateSrc = (ImageView) view.findViewById(R.id.imgSensorStateSrc);
            pbStateConnectionProgress = (ProgressBar) view.findViewById(R.id.pbStateConnectionProgress);
            pbStateConnectionProgress.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

            selectedPlayerPositionPlusOne = 0;
            initSpinner();
        }

        private void initSpinner(){
            // Initializing a String Array
            spinnerAdapter = new SpinnerPlayerArrayAdapter(context,R.layout.spinner_item,playerStringList);
            spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spPlayerList.setAdapter(spinnerAdapter);
        }

        public void updatePlayerView(){
            if(selectedPlayerPositionPlusOne>0){
                ImageUtils.loadPhoto(DataStore.getInstance().getPlayerList()
                                .get(selectedPlayerPositionPlusOne-1).getPathPhoto()
                        ,icPlayerPhoto);

                if(ImageUtils.isFileImage(DataStore.getInstance().getPlayerList()
                        .get(selectedPlayerPositionPlusOne-1).getPathPhoto())) {
                    viewPlayerWithoutPicture.setVisibility(View.INVISIBLE);
                    icPlayerPhoto.setVisibility(View.VISIBLE);
                    ImageUtils.loadPhoto(DataStore.getInstance().getPlayerList()
                                    .get(selectedPlayerPositionPlusOne-1).getPathPhoto()
                            ,icPlayerPhoto);
                }else{
                    viewPlayerWithoutPicture.setVisibility(View.VISIBLE);
                    icPlayerPhoto.setVisibility(View.INVISIBLE);
                    tvPlayerNumber.setText(String.valueOf(DataStore.getInstance().getPlayerList()
                            .get(selectedPlayerPositionPlusOne-1).getPlayerNumber()));
                }
            }else{
                viewPlayerWithoutPicture.setVisibility(View.VISIBLE);
                icPlayerPhoto.setVisibility(View.INVISIBLE);
                tvPlayerNumber.setText("");
            }
        }

        public void updateSpinner(final TrackAndRollSensor trackAndRollSensor){
            spPlayerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    selectedPlayerPositionPlusOne = position;
                    updatePlayerView();
                    if(selectedPlayerPositionPlusOne>0){
                        trackAndRollSensor.setAllocatedPlayerKey(DataStore.getInstance().getPlayerList().get(selectedPlayerPositionPlusOne-1).getPlayerKey());
                    }else{
                        trackAndRollSensor.setAllocatedPlayerKey(Constant.SENSOR_NON_ASSIGNED_NAME);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });
            spPlayerList.setSelection(selectedPlayerPositionPlusOne);
        }
    }

    public SensorItemAdapter(Context context, ArrayList<TrackAndRollSensor> trackAndRollSensorList) {
        this.context=context;
        this.trackAndRollSensorList=trackAndRollSensorList;
        this.playerStringList = DataUtils.convertPlayerListToStringPlayerList(DataStore.getInstance().getPlayerList());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.selectedPlayerPositionPlusOne = DataUtils.getSelectedPlayerPosition(DataStore.getInstance().getPlayerList(),trackAndRollSensorList.get(position).getAllocatedPlayerKey());
        holder.updatePlayerView();
        holder.updateSpinner(trackAndRollSensorList.get(position));
        holder.tvSensorNumber.setTypeface(Typeface.createFromAsset(context.getAssets(), Fonts.ROBOTO_BOLD));
        holder.tvSensorId.setTypeface(Typeface.createFromAsset(context.getAssets(), Fonts.ROBOTO_LIGHT));

        holder.tvSensorNumber.setText(String.valueOf(trackAndRollSensorList.get(position).getSensorNumber()));
        holder.tvSensorNumber.setText(String.format(
                context.getResources().getString(R.string.activity_sensors_manager_sensor_format),
                trackAndRollSensorList.get(position).getSensorNumber()
        ));

        holder.tvSensorId.setText(trackAndRollSensorList.get(position).getSensorId());

        if(holder.selectedPlayerPositionPlusOne>=0){
            holder.spPlayerList.setSelection(holder.selectedPlayerPositionPlusOne);
        }

        updateSensorStateIcon(holder,position);
    }

    private void updateSensorStateIcon(MyViewHolder holder, int position) {
        switch(trackAndRollSensorList.get(position).getSensorState()){
            case Constant.SENSOR_STATE_ERROR:
                holder.pbStateConnectionProgress.setVisibility(View.INVISIBLE);
                holder.imgSensorStateSrc.setVisibility(View.VISIBLE);
                holder.imgSensorStateBackground.setBackgroundResource(R.drawable.circle_red);
                holder.imgSensorStateSrc.setBackgroundResource(R.drawable.ic_white_cross);
                break;

            case Constant.SENSOR_STATE_CONNECTED:
                holder.pbStateConnectionProgress.setVisibility(View.INVISIBLE);
                holder.imgSensorStateSrc.setVisibility(View.VISIBLE);
                holder.imgSensorStateBackground.setBackgroundResource(R.drawable.circle_green);
                holder.imgSensorStateSrc.setBackgroundResource(R.drawable.ic_valid);
                break;

            case Constant.SENSOR_STATE_WARNING:
                holder.pbStateConnectionProgress.setVisibility(View.INVISIBLE);
                holder.imgSensorStateSrc.setVisibility(View.VISIBLE);
                holder.imgSensorStateBackground.setBackgroundResource(R.drawable.circle_orange);
                holder.imgSensorStateSrc.setBackgroundResource(R.drawable.ic_warning);
                break;

            case Constant.SENSOR_STATE_CONNECTION_PROGRESS:
                holder.pbStateConnectionProgress.setVisibility(View.VISIBLE);
                holder.imgSensorStateSrc.setVisibility(View.INVISIBLE);
                holder.imgSensorStateBackground.setBackgroundResource(R.drawable.circle_transparent);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return trackAndRollSensorList.size();
    }
}