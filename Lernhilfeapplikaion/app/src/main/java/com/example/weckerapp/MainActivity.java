package com.example.weckerapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TimePicker tip;
    private Button btnAlarm;
    private static Button btnKeinAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlarm=findViewById(R.id.btnAlarm);
        tip=findViewById(R.id.tipUhr);
        tip.setIs24HourView(false);
        btnKeinAlarm = findViewById(R.id.btnKeinAlarm);
        btnAlarm.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view){
        Calendar kalender = Calendar.getInstance();
        kalender.set(kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH), tip.getHour(), tip.getMinute(), 0);
        alarmSetzten(kalender.getTimeInMillis());
    }








    private void alarmSetzten(long zeitInMS){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, zeitInMS, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Der Alarm wurde gesetzt", Toast.LENGTH_LONG).show();

    }
}
