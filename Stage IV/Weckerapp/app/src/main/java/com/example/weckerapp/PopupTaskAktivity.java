package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.weckerapp.alarmTasks.AlarmTask;

import java.io.Console;
import java.util.ArrayList;

public class PopupTaskAktivity extends AppCompatActivity {

    TextView tv;
    int index_of_alarm;
    Button button_deactivateAlarm;
    int task_index;

    ArrayList<AlarmTask> TaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_task_aktivity);
        tv = findViewById(R.id.tv_score_number);
        button_deactivateAlarm = findViewById(R.id.button_back);
        index_of_alarm = getIntent().getIntExtra("alarm_index", -1);
        task_index = getIntent().getIntExtra("task_index", -1);

        if(index_of_alarm != -1){
            tv.setText(AlarmsActivity.al_alarms.get(index_of_alarm).displayed_title);
        }

        TaskList = AlarmsActivity.al_alarms.get(index_of_alarm).getAl_AlarmTasks();

        loadSettings();
    }

    public void button_deactivateAlarmClicked(View view) {
        MainActivity.mediaPlayer.stop();
        Intent intent;
        intent = null;
        if(index_of_alarm != -1 && AlarmsActivity.al_alarms.get(index_of_alarm).atheneosAlarm){


            switch (TaskList.get(0).getDom()){
                case 0:
                    intent = new Intent(this, SolveTaskMath.class);
                    intent.putExtra("alarm_index", index_of_alarm);
                    CurrentTask.current_task = CurrentTask.current_task + 1;
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(this, SolveTaskMath.class);
                    intent.putExtra("alarm_index", index_of_alarm);
                    CurrentTask.current_task++;
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(this, SolveTaskMath.class);
                    intent.putExtra("alarm_index", index_of_alarm);
                    CurrentTask.current_task = CurrentTask.current_task + 1;
                    startActivity(intent);
                    break;
            }

        }else{
            //Clearance for next alarm
            MainActivity.audioActivated = false;
            CurrentTask.current_task = 0;
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SettingsActivity.prim = sharedPreferences.getInt("primaryColour", R.color.atheneos_yellow);
        SettingsActivity.sec = sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey);
        findViewById(R.id.container_showScore).setBackgroundColor(SettingsActivity.sec);
        float textScale = sharedPreferences.getFloat("sb_textScale", 1.0f);
       // button_deactivateAlarm.setTextSize(16*textScale);
    }


}