package com.example.weckerapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextClock tc;
    TimePicker tp;
    String alarmtime = "";
    String currenttime = "";
    TextView tv;
    //final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        tc = findViewById(R.id.tec);
        tp = findViewById(R.id.tip);
        tc.setFormat24Hour("k:mm");
        tc.setFormat12Hour(null);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!alarmtime.equals("")){
                    if(tc.getText().toString().equals(alarmtime)){
                        //r.play();
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
                        mediaPlayer.start();
                        tv.setText("Bitte Aufwachen");
                    }
                }
            }
        },0,1000);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickAlarmSetzten(View view){

        alarmtime = tp.getHour()+":"+tp.getMinute();
        Toast.makeText(this, "Alarm gesetzt", Toast.LENGTH_LONG).show();
    }
}