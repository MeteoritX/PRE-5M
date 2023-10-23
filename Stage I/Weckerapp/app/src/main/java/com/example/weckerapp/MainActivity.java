package com.example.weckerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TabHost;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    Chronometer chronometer;
    boolean chronometerRunning;
    long delta_t;
    TextView tv_delta_t;
    Button button_start_stop;

    TextClock tc;
    TimePicker tp;
    String alarmtime = "";
    String currenttime = "";
    TextView tv;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tc = findViewById(R.id.tec);
        tp = findViewById(R.id.tip);
        tc.setFormat24Hour("k:mm");
        tc.setFormat12Hour(null);


        //Tabs
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("Wecker");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Wecker");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Timer");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Timer");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Stoppuhr");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Stoppuhr");
        tabHost.addTab(spec);

        //Initialise reference variables
        chronometer = (Chronometer)  findViewById(R.id.chron);
        chronometer.setFormat("%h:%m:%s");
        chronometerRunning = false;
        button_start_stop = (Button) findViewById(R.id.button_start_stop);
        tv_delta_t = (TextView) findViewById(R.id.tv_delta_t);


        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!alarmtime.equals("")){
                    if(tc.getText().toString().equals(alarmtime)){
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
                        mediaPlayer.start();
                    }
                }
            }
        },0,1000);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Setting up a top menue for settings / access to defined alarms
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Click listener: Checking which menue item has been clicked
        switch (item.getItemId()){
            case R.id.settings:
                Toast.makeText(this, "settings clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.alarms:
                Toast.makeText(this, "alarms clicked", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void button_setAlarm_Clicked(View view) {
        alarmtime = tp.getHour()+":"+tp.getMinute();
        Toast.makeText(this, "Alarm gesetzt", Toast.LENGTH_LONG).show();
    }


    public void button_start_stop_Clicked(View view) {
        if(!chronometerRunning)
        {
            chronometer.start();
            chronometerRunning = true;
            button_start_stop.setText(getResources().getString(R.string.text_button_stop));
        }else {
            chronometer.stop();
            chronometerRunning = false;
            button_start_stop.setText(getResources().getString(R.string.text_button_start));
            delta_t = SystemClock.elapsedRealtime() - chronometer.getBase();
            tv_delta_t.setText(getResources().getString(R.string.text_delta_t) + "\n" + delta_t);

            //Base-Konflikt beheben u. delta_t rückformatieren
        }
    }
}