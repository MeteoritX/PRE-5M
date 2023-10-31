package com.example.weckerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.weckerapp.alarmTasks.AlarmTask;

import java.util.ArrayList;

public class SolveTaskActivity extends AppCompatActivity {

    ArrayList<AlarmTask> alarm_tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_task);
        setSupportActionBar(findViewById(R.id.toolbar_solve));
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_solve_task));

        //Anzeige der Aufgabenstellung
    }
}