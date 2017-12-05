package com.neos.trackandroll.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.neos.trackandroll.R;
import com.neos.trackandroll.utils.ViewUtils;

public class HelpActivity extends AbstractActivityWithToolbar {

    private ImageView imgArrowTitle1;
    private ImageView imgArrowTitle2;
    private ImageView imgArrowTitle3;
    private ImageView imgArrowTitle4;
    private ImageView imgArrowTitle5;

    private View layoutQuestion1;
    private View layoutAnswer1;
    private View layoutQuestion2;
    private View layoutAnswer2;
    private View layoutQuestion3;
    private View layoutAnswer3;
    private View layoutQuestion4;
    private View layoutAnswer4;
    private View layoutQuestion5;
    private View layoutAnswer5;

    private boolean viewVisible1;
    private boolean viewVisible2;
    private boolean viewVisible3;
    private boolean viewVisible4;
    private boolean viewVisible5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateLayout(4,R.layout.activity_help,(ViewGroup) findViewById(R.id.activity_help));

        layoutQuestion1 = findViewById(R.id.layoutQuestion1);
        layoutAnswer1 = findViewById(R.id.layoutAnswer1);
        layoutQuestion2 = findViewById(R.id.layoutQuestion2);
        layoutAnswer2 = findViewById(R.id.layoutAnswer2);
        layoutQuestion3 = findViewById(R.id.layoutQuestion3);
        layoutAnswer3 = findViewById(R.id.layoutAnswer3);
        layoutQuestion4 = findViewById(R.id.layoutQuestion4);
        layoutAnswer4 = findViewById(R.id.layoutAnswer4);
        layoutQuestion5 = findViewById(R.id.layoutQuestion5);
        layoutAnswer5 = findViewById(R.id.layoutAnswer5);

        imgArrowTitle1 = (ImageView) findViewById(R.id.imgArrowTitle1);
        imgArrowTitle2 = (ImageView) findViewById(R.id.imgArrowTitle2);
        imgArrowTitle3 = (ImageView) findViewById(R.id.imgArrowTitle3);
        imgArrowTitle4 = (ImageView) findViewById(R.id.imgArrowTitle4);
        imgArrowTitle5 = (ImageView) findViewById(R.id.imgArrowTitle5);

        layoutAnswer1.setVisibility(View.GONE);
        layoutAnswer2.setVisibility(View.GONE);
        layoutAnswer3.setVisibility(View.GONE);
        layoutAnswer4.setVisibility(View.GONE);
        layoutAnswer5.setVisibility(View.GONE);

        viewVisible1 = false;
        viewVisible2 = false;
        viewVisible3 = false;
        viewVisible4 = false;
        viewVisible5 = false;

        initListener();
    }

    private void initListener(){
        layoutQuestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (!viewVisible1){
                    ViewUtils.makeLayoutVisible(getApplicationContext(), layoutAnswer1);
                    viewVisible1 = true;
                    ViewUtils.makeRotate90Clockwise(getApplicationContext(), imgArrowTitle1);
                }
                else{
                    ViewUtils.makeLayoutGone(getApplicationContext(), layoutAnswer1);
                    viewVisible1 = false;
                    ViewUtils.makeRotate90Anticlockwise(getApplicationContext(), imgArrowTitle1);
                }
            }
        });

        layoutQuestion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (!viewVisible2){
                    ViewUtils.makeLayoutVisible(getApplicationContext(), layoutAnswer2);
                    viewVisible2 = true;
                    ViewUtils.makeRotate90Clockwise(getApplicationContext(), imgArrowTitle2);
                }
                else{
                    ViewUtils.makeLayoutGone(getApplicationContext(), layoutAnswer2);
                    viewVisible2 = false;
                    ViewUtils.makeRotate90Anticlockwise(getApplicationContext(), imgArrowTitle2);
                }
            }
        });

        layoutQuestion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (!viewVisible3){
                    ViewUtils.makeLayoutVisible(getApplicationContext(), layoutAnswer3);
                    viewVisible3 = true;
                    ViewUtils.makeRotate90Clockwise(getApplicationContext(), imgArrowTitle3);
                }
                else{
                    ViewUtils.makeLayoutGone(getApplicationContext(), layoutAnswer3);
                    viewVisible3 = false;
                    ViewUtils.makeRotate90Anticlockwise(getApplicationContext(), imgArrowTitle3);
                }
            }
        });

        layoutQuestion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (!viewVisible4){
                    ViewUtils.makeLayoutVisible(getApplicationContext(), layoutAnswer4);
                    viewVisible4 = true;
                    ViewUtils.makeRotate90Clockwise(getApplicationContext(), imgArrowTitle4);
                }
                else{
                    ViewUtils.makeLayoutGone(getApplicationContext(), layoutAnswer4);
                    viewVisible4 = false;
                    ViewUtils.makeRotate90Anticlockwise(getApplicationContext(), imgArrowTitle4);
                }
            }
        });

        layoutQuestion5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (!viewVisible5){
                    ViewUtils.makeLayoutVisible(getApplicationContext(), layoutAnswer5);
                    viewVisible5 = true;
                    ViewUtils.makeRotate90Clockwise(getApplicationContext(), imgArrowTitle5);
                }
                else{
                    ViewUtils.makeLayoutGone(getApplicationContext(), layoutAnswer5);
                    viewVisible5 = false;
                    ViewUtils.makeRotate90Anticlockwise(getApplicationContext(), imgArrowTitle5);
                }
            }
        });
    }


}
