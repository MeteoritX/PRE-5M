package com.example.weckerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.weckerapp.databinding.ActivityPopupTaskAktivityBinding;

public class PopupTaskAktivity extends AppCompatActivity {

    TextView tv;
    int index_of_alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_task_aktivity);
        tv = findViewById(R.id.textView3);
        index_of_alarm = getIntent().getIntExtra("alarm_index", -1);
        if(index_of_alarm != -1){
            tv.setText(AlarmsActivity.al_alarms.get(index_of_alarm).displayed_title);
        }
    }

    public void button_deactivateAlarmClicked(View view) {
        MainActivity.mediaPlayer.stop();
        Intent intent;
        if(index_of_alarm != -1 && AlarmsActivity.al_alarms.get(index_of_alarm).atheneosAlarm){
            intent = new Intent(this, SolveTaskActivity.class);
            startActivity(intent);
        }else{
            //Clearance for next alarm
            MainActivity.audioActivated = false;
            intent = new Intent(this, MainActivity.class);
        }
        intent.putExtra("alarm_index", index_of_alarm);
        startActivity(intent);
    }


}