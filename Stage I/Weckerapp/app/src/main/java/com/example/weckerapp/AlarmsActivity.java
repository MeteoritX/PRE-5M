package com.example.weckerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class AlarmsActivity extends AppCompatActivity {

    Toolbar toolbar_alarms;
    ListView lv_alarms;
    static ArrayList<Alarm> al_alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        toolbar_alarms = findViewById(R.id.toolbar_alarms);
        setSupportActionBar(toolbar_alarms);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.text_alarms_title));
        lv_alarms = findViewById(R.id.listView_alarms);

        AlarmAdapterForListView alarmAdapter = new AlarmAdapterForListView(this, R.layout.alarm_row, al_alarms);
        lv_alarms.setAdapter(alarmAdapter);

        //Dummy Alarm (Atheneos)
        //al_alarms.add(new Alarm("20:30", R.drawable.atheneos, true, false));

        lv_alarms.setClickable(true);
        lv_alarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Intent intent = new Intent(this, )
            }
        });


    }
}