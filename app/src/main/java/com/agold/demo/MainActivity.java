package com.agold.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private SlideView callSlideView;
    private SlideView smsSlideView;
    private SlideView sosSlideView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        callSlideView = ((SlideView) findViewById(R.id.slider1));
        callSlideView.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(600);
                android.util.Log.i("ly20170418","slider 1 complete");
                Intent activity2 = new Intent();
                activity2.setClassName("com.agold.demo","com.agold.demo.ContactDataBaseActivity");
                startActivity(activity2);
            }
        });

        smsSlideView = ((SlideView) findViewById(R.id.slider2));
        smsSlideView.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(600);
                android.util.Log.i("ly20170418","slider 2 complete");
            }
        });

        sosSlideView = ((SlideView) findViewById(R.id.slider3));
        sosSlideView.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(600);
                android.util.Log.i("ly20170418","slider 3 complete");
            }
        });

    }

}
