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
import android.widget.TabHost;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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





    }

}