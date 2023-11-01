package com.example.weckerapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weckerapp.alarmTasks.AlarmTask;

import java.util.ArrayList;

public class SolveTaskActivity extends AppCompatActivity {


   TextView tv_toSolve;
    ProgressBar pb_remaining_time;
    FloatingActionButton button_mute;
    static CountDownTimer countdown_timer;
    static MediaPlayer mediaPlayer;
    static Context context;

    int alarm_index;
    Alarm a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_task);
        setSupportActionBar(findViewById(R.id.toolbar_solve));
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_solve_task));
        pb_remaining_time = findViewById(R.id.progressBar_solveTask);
         button_mute = findViewById(R.id.button_mute);
         context = this;
         tv_toSolve = findViewById(R.id.tv_toSolve);

        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.solving_music);
        }
        mediaPlayer.start();
        pb_remaining_time.setProgress(600);
        alarm_index = getIntent().getExtras().getInt("alarm_index", -1);
        if(alarm_index != -1){
            a = AlarmsActivity.al_alarms.get(alarm_index);
            //Difficulty and stuff
           tv_toSolve.setText(" " + (int) (Math.random() * 11) + (int) (Math.random() * 11));

        }

        //TaskConnection! -> length
       countdown_timer = new CountDownTimer(60000, 200){
            @Override
            public void onTick(long l) {
                if((pb_remaining_time.getProgress() >= 20)){
                    pb_remaining_time.setProgress(pb_remaining_time.getProgress()-2);
                }
            }

            @Override
            public void onFinish() {
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context, R.raw.repetitive_bssssw);
                mediaPlayer.start();
                mediaPlayer.setVolume(5,5);
            }
        };
       countdown_timer.start();


        //Anzeige der Aufgabenstellung
    }


    public void button_checkClicked(View view) {

    }

    public void button_muteClicked(View view) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            button_mute.setImageResource(R.drawable.baseline_music_note_24);

        }else{
            mediaPlayer = MediaPlayer.create(this, R.raw.solving_music);
            mediaPlayer.start();
            button_mute.setImageResource(R.drawable.baseline_music_off_24);
        }
    }

    public void displayCurrentTask(){
        for (AlarmTask at: a.getAl_AlarmTasks()) {
            if(at == null) {continue; }else{

            }

        }
    }
}