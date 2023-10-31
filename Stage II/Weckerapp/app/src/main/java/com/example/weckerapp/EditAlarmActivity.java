package com.example.weckerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;


public class EditAlarmActivity extends AppCompatActivity {

    TimePicker tp_edit_alarm;
    int alarmIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        alarmIndex = getIntent().getIntExtra("alarmIndex", 0);
        Alarm alarm = AlarmsActivity.al_alarms.get(alarmIndex);

        tp_edit_alarm = findViewById(R.id.tp_edit_alarm);
        loadSettings();
        tp_edit_alarm.setHour(Integer.parseInt(alarm.title.split(":")[0]));
        tp_edit_alarm.setMinute(Integer.parseInt(alarm.title.split(":")[1]));

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        ab.setTitle(getResources().getString(R.string.title_activity_edit_alarm));
    }


    public void button_saveEditAlarmClicked(View view) {
        String h = (tp_edit_alarm.getHour() < 10)  ? "0" + tp_edit_alarm.getHour()  : "" + tp_edit_alarm.getHour();
        String m = (tp_edit_alarm.getMinute() < 10)  ? "0" + tp_edit_alarm.getMinute()  : "" + tp_edit_alarm.getMinute();
        AlarmsActivity.al_alarms.get(alarmIndex).setTitle(h + ":" + m);
        ((MainActivity) MainActivity.ctx).saveAlarms();
        Intent intent = new Intent(this, AlarmsActivity.class);
        startActivity(intent);
    }

    public void button_deleteAlarmClicked(View view) {
        AlarmsActivity.al_alarms.remove(alarmIndex);
        ((MainActivity) MainActivity.ctx).saveAlarms();
        Intent intent = new Intent(this, AlarmsActivity.class);
        startActivity(intent);
    }

    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean switchState = sharedPreferences.getBoolean("switch_24h", false);
        tp_edit_alarm.setIs24HourView(switchState);
    }

    public void iv_addTaskClicked(View view) {
        Intent intent_addTask = new Intent(this, ChooseDomainActivity.class);
        switch(view.getId()){
            case R.id.iv_task1:
                intent_addTask.putExtra("task_slot", R.id.iv_task1);
                intent_addTask.putExtra("task_slot_bg", R.id.iv_task1_background);
                intent_addTask.putExtra("task_index", 1);
                break;
            case R.id.iv_task2:
                intent_addTask.putExtra("task_slot", R.id.iv_task2);
                intent_addTask.putExtra("task_slot_bg", R.id.iv_task2_background);
                intent_addTask.putExtra("task_index", 2);
                break;
            case R.id.iv_task3:
                intent_addTask.putExtra("task_slot", R.id.iv_task3);
                intent_addTask.putExtra("task_slot_bg", R.id.iv_task3_background);
                intent_addTask.putExtra("task_index", 3);
                break;
            case R.id.iv_task4:
                intent_addTask.putExtra("task_slot", R.id.iv_task4);
                intent_addTask.putExtra("task_slot_bg", R.id.iv_task4_background);
                intent_addTask.putExtra("task_index", 4);
                break;
            case R.id.iv_task5:
                intent_addTask.putExtra("task_slot", R.id.iv_task5);
                intent_addTask.putExtra("task_slot_bg", R.id.iv_task5_background);
                intent_addTask.putExtra("task_index", 5);
                break;
            case R.id.iv_task6:
                intent_addTask.putExtra("task_slot", R.id.iv_task6);
                intent_addTask.putExtra("task_slot_bg", R.id.iv_task6_background);
                intent_addTask.putExtra("task_index", 6);
                break;
        }
        intent_addTask.putExtra("alarm_index", alarmIndex);
        startActivity(intent_addTask);
    }
}