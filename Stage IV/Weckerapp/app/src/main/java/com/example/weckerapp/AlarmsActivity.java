package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlarmsActivity extends AppCompatActivity {

    Toolbar toolbar_alarms;
    ListView lv_alarms;
    static ArrayList<Alarm> al_alarms;
   static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        ctx = this;
        toolbar_alarms = findViewById(R.id.toolbar_alarms);
        setSupportActionBar(toolbar_alarms);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        actionBar.setTitle(getResources().getString(R.string.text_alarms_title));
        lv_alarms = findViewById(R.id.listView_alarms);

        AlarmAdapterForListView alarmAdapter = new AlarmAdapterForListView(this, R.layout.alarm_row, al_alarms);
        lv_alarms.setAdapter(alarmAdapter);
        ((MainActivity) MainActivity.ctx).loadAlarms();

        //Dummy Alarm (Atheneos)
        //al_alarms.add(new Alarm("20:30", R.drawable.atheneos, true, false));

        lv_alarms.setClickable(true);
        lv_alarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AlarmsActivity.ctx, EditAlarmActivity.class);
                intent.putExtra("alarmIndex", i);
                startActivity(intent);
            }
        });
        loadSettings();
    }

    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SettingsActivity.prim = sharedPreferences.getInt("primaryColour", R.color.atheneos_yellow);
        SettingsActivity.sec = sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey);
        findViewById(R.id.container_alarms).setBackgroundColor(SettingsActivity.sec);
    }
}