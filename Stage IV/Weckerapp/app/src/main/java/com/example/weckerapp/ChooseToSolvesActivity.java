package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.weckerapp.alarmTasks.AlarmTask;

import java.util.Timer;
import java.util.TimerTask;

public class ChooseToSolvesActivity extends AppCompatActivity {

    TextView tv_difficulty;
    TextView tv_number;
    SeekBar sb_difficulty;
    SeekBar sb_number;
    TextView tv_example;
    static Timer t;
    static boolean t_active;

static Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_to_solves);
        setSupportActionBar(findViewById(R.id.toolbar_toSolves));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(extras == null){
            extras = getIntent().getExtras();
            t_active = false;
            t = new Timer();
        }
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_choose_to_solves) + ": " +  getIntent().getExtras().getString("module_indexer", ""));
        tv_difficulty = findViewById(R.id.tv_difficulty);
        tv_number = findViewById(R.id.tv_number_of_toSolves);
        sb_difficulty = findViewById(R.id.seekBar_difficulty);
        sb_number = findViewById(R.id.seekBar_number_of_toSolves);
        tv_example = findViewById(R.id.tv_example_structure);
        sb_difficulty.setProgress(1);
        sb_number.setProgress(1);


        String module_diff = extras.getString("module_indexer", ""); //Module differentiator

        switch(extras.getInt("dom")){
            case 0:
                if(t_active == false)t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        seekBar_difficultyClicked(sb_difficulty);
                        seekBar_numberClicked(sb_number);

                        switch (sb_difficulty.getProgress()){
                            case 1:
                                tv_example.setText("9 + 3 =");
                                break;
                            case 2:
                                tv_example.setText("9 + 12 =");
                                break;
                            case 3:
                                tv_example.setText("34 + 12 =");
                                break;
                            case 4:
                                tv_example.setText("46 + 199 =");
                                break;
                            case 5:
                                tv_example.setText("322 + 710 =");
                                break;
                            case 6:
                                tv_example.setText("352 - 795 =");
                                break;
                            case 7:
                                tv_example.setText("12 * 3 * 6 =");
                                break;
                            case 8:
                                tv_example.setText("(-46) * 9 + 382 =");
                                break;
                            case 9:
                                tv_example.setText("34 * 399 + (-77) =");
                                break;
                            case 10:
                                tv_example.setText("(-843) * (-503) + 23 =");
                                break;
                        }

                    }
                }, 800, 800);

                break;
            case 1:
                if(t_active == false)t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        seekBar_difficultyClicked(sb_difficulty);
                        seekBar_numberClicked(sb_number);

                        switch (sb_difficulty.getProgress()){
                            case 1:
                                tv_example.setText("50% des Wortes");
                                break;
                            case 2:
                                tv_example.setText("55% des Wortes");
                                break;
                            case 3:
                                tv_example.setText("60% des Wortes");
                                break;
                            case 4:
                                tv_example.setText("65% des Wortes");
                                break;
                            case 5:
                                tv_example.setText("70% des Wortes");
                                break;
                            case 6:
                                tv_example.setText("75% des Wortes");
                                break;
                            case 7:
                                tv_example.setText("80% des Wortes");
                                break;
                            case 8:
                                tv_example.setText("85% des Wortes");
                                break;
                            case 9:
                                tv_example.setText("90% des Wortes");
                                break;
                            case 10:
                                tv_example.setText("100% des Wortes");
                                break;
                        }

                    }
                }, 800, 800);

                break;
            case 2:
                if(t_active == false)t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        seekBar_difficultyClicked(sb_difficulty);
                        seekBar_numberClicked(sb_number);

                        switch (sb_difficulty.getProgress()){
                            case 1:
                                tv_example.setText("50% des Wortes");
                                break;
                            case 2:
                                tv_example.setText("55% des Wortes");
                                break;
                            case 3:
                                tv_example.setText("60% des Wortes");
                                break;
                            case 4:
                                tv_example.setText("65% des Wortes");
                                break;
                            case 5:
                                tv_example.setText("70% des Wortes");
                                break;
                            case 6:
                                tv_example.setText("75% des Wortes");
                                break;
                            case 7:
                                tv_example.setText("80% des Wortes");
                                break;
                            case 8:
                                tv_example.setText("85% des Wortes");
                                break;
                            case 9:
                                tv_example.setText("90% des Wortes");
                                break;
                            case 10:
                                tv_example.setText("100% des Wortes");
                                break;
                        }

                    }
                }, 800, 800);
                break;
        }




        loadSettings();
    }


    public void button_saveToSolvesClicked(View view) {
        int alarm_index = extras.getInt("alarm_index", -1);
        if (alarm_index != -1) {
            Alarm a = AlarmsActivity.al_alarms.get(alarm_index);
            a.setAtheneosAlarm(true);
            a.setSymbol(R.drawable.atheneos);
            t.cancel();
            t.purge();


            int task_index = extras.getInt("task_index");
            AlarmTask at = new AlarmTask(sb_number.getProgress(), sb_difficulty.getProgress(), extras.getInt("dom", 1), extras.getString("module_indexer", ""));
            a.al_AlarmTasks.set(task_index, at);


            extras = null;
            ChooseDomainActivity.extras = null;
            ChooseModuleActivity.extras = null;
            Intent intent = new Intent(this, EditAlarmActivity.class);
            startActivity(intent);
        }

    }

    public void seekBar_difficultyClicked(View view) {
        tv_difficulty.setText(getResources().getString(R.string.text_difficulty)+": " + sb_difficulty.getProgress());
    }

    public void seekBar_numberClicked(View view) {
        tv_number.setText(getResources().getString(R.string.text_number_of_toSolves)+": " + sb_number.getProgress());
    }

    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SettingsActivity.prim = sharedPreferences.getInt("primaryColour", R.color.atheneos_yellow);
        SettingsActivity.sec = sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey);
        findViewById(R.id.container_toSolves).setBackgroundColor(SettingsActivity.sec);
        float textScale = sharedPreferences.getFloat("sb_textScale", 1.0f);
        ((Button) findViewById(R.id.button_saveToSolves)).setTextSize(16*textScale);
        tv_difficulty.setTextSize(20*textScale);
        tv_number.setTextSize(20*textScale);
    }
}