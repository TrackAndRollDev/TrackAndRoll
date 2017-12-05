package com.neos.trackandroll.view.adapter.sessions;

import android.view.MotionEvent;
import android.view.View;

public interface IClickListener {
    void onClick(View view, int position, MotionEvent e);
    void onLongClick(View view, int position);
}