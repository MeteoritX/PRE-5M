package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.weckerapp.alarmTasks.Domain;


public class EditAlarmActivity extends AppCompatActivity {

    TimePicker tp_edit_alarm;
    static int alarmIndex;

     ImageView iv_task1;
     ImageView iv_task1_bg;
     ImageView iv_task2;
     ImageView iv_task2_bg;
     ImageView iv_task3;
     ImageView iv_task3_bg;
     ImageView iv_task4;
     ImageView iv_task4_bg;
     ImageView iv_task5;
     ImageView iv_task5_bg;
     ImageView iv_task6;
     ImageView iv_task6_bg;

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        alarmIndex = getIntent().getIntExtra("alarmIndex", alarmIndex);
        Alarm alarm = AlarmsActivity.al_alarms.get(alarmIndex);

        tp_edit_alarm = findViewById(R.id.tp_edit_alarm);
        loadSettings();
        tp_edit_alarm.setHour(Integer.parseInt(alarm.title.split(":")[0]));
        tp_edit_alarm.setMinute(Integer.parseInt(alarm.title.split(":")[1]));

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        ab.setTitle(getResources().getString(R.string.title_activity_edit_alarm));


        visualiseTasks(0, findViewById(R.id.iv_task1), findViewById(R.id.iv_task1_background));
        visualiseTasks(1, findViewById(R.id.iv_task2), findViewById(R.id.iv_task2_background));
        visualiseTasks(2, findViewById(R.id.iv_task3), findViewById(R.id.iv_task3_background));
        visualiseTasks(3, findViewById(R.id.iv_task4), findViewById(R.id.iv_task4_background));
        visualiseTasks(4, findViewById(R.id.iv_task5), findViewById(R.id.iv_task5_background));
        visualiseTasks(5, findViewById(R.id.iv_task6), findViewById(R.id.iv_task6_background));


        //Schwierigkeit, Menge, Modul, ToSolves
     loadSettings();
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
            SettingsActivity.prim = sharedPreferences.getInt("primaryColour", R.color.atheneos_yellow);
            SettingsActivity.sec = sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey);
            findViewById(R.id.container_editAlarm).setBackgroundColor(SettingsActivity.sec);
            //Colour the task icons
    }

    public void iv_addTaskClicked(View view) {
        Intent intent_addTask = new Intent(this, ChooseDomainActivity.class);
        switch(view.getId()){
            case R.id.iv_task1:
                intent_addTask.putExtra("task_index", 0);
                break;
            case R.id.iv_task2:
                intent_addTask.putExtra("task_index", 1);
                break;
            case R.id.iv_task3:
                intent_addTask.putExtra("task_index", 2);
                break;
            case R.id.iv_task4:
                intent_addTask.putExtra("task_index", 3);
                break;
            case R.id.iv_task5:
                intent_addTask.putExtra("task_index", 4);
                break;
            case R.id.iv_task6:
                intent_addTask.putExtra("task_index", 5);
                break;
        }
        intent_addTask.putExtra("alarm_index", alarmIndex);
        startActivity(intent_addTask);
    }

    public void visualiseTasks(int taskIndex, ImageView iv_task, ImageView iv_task_bg){
        Alarm a_current = AlarmsActivity.al_alarms.get(alarmIndex);

        if(a_current.al_AlarmTasks.get(taskIndex) != null) {
            iv_task_bg.setColorFilter(R.color.atheneos_yellow, PorterDuff.Mode.DST_IN);

            switch (a_current.al_AlarmTasks.get(taskIndex).getDom()) {
                case 0:
                    // Domain.DOM_MATHEMATICS
                    iv_task.setImageResource(R.drawable.baseline_calculate_24);
                    break;
                case 1:
                    // Domain.DOM_MEDICINE
                    //iv_task.setImageResource(R.drawable.);
                    break;
                case 2:
                    // Domain.DOM_LINGUISTICS
                    //iv_task.setImageResource(R.drawable.); (Book)
                    break;
            }
        }
    }


}
