package com.neos.trackandroll.view.adapter.sensors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.neos.trackandroll.R;

import java.util.List;

public class SpinnerPlayerArrayAdapter extends ArrayAdapter<String> {

    public Context context;

    public SpinnerPlayerArrayAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context=context;
    }

    @Override
    public boolean isEnabled(int position){
        if(position == 0){
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        } else {
            return true;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(context.getResources().getColor(R.color.colorWhite));
            return super.getView(position, tv, parent);
        }else{
            return super.getView(position, convertView, parent);
        }
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;

        // For disable first item
        /*
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(context.getResources().getColor(R.color.colorWhite));
            tv.setVisibility(View.INVISIBLE);
        }
        else {
            tv.setTextColor(context.getResources().getColor(R.color.colorWhite));
        }
        */
        tv.setTextColor(context.getResources().getColor(R.color.colorWhite));
        if(position%2==0){
            // Set the item background color
            tv.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }else {
            // Set the alternate item background color
            tv.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
        return view;
    }
}
