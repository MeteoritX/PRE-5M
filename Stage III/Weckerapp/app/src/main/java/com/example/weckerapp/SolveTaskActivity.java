package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weckerapp.alarmTasks.AlarmTask;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class SolveTaskActivity extends AppCompatActivity {


   TextView tv_toSolve;
   TextView tv_total_solved;
   EditText et_input;
    ProgressBar pb_remaining_time;
    FloatingActionButton button_mute;
    static CountDownTimer countdown_timer;
    static MediaPlayer mediaPlayer;
    static boolean timerIsActive;

    Queue<AlarmTask> task_queue;
    static Context context;

    int alarm_index;
    Alarm a;

    int mp_position;
    static Timer timer_intermezzo;

    //------------Current-Task--------------
    AlarmTask task;
    int time4task;
    int difficuty;
    int toSolves;
    int solved;
    int currentOutcome;
    //--------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_task);
        setSupportActionBar(findViewById(R.id.toolbar_solve));
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_solve_task));


        Intent testIntent = getIntent();
        Bundle b =  testIntent.getExtras();
        alarm_index = b.getInt("alarm_index", -1); //nullpointer

        task_queue = new ArrayDeque<>();
        pb_remaining_time = findViewById(R.id.progressBar_solveTask);
         button_mute = findViewById(R.id.button_mute);
         context = this;
         tv_toSolve = findViewById(R.id.tv_toSolve);
         tv_total_solved = findViewById(R.id.tv_total_finished);
         et_input = findViewById(R.id.et_input);
         timer_intermezzo = new Timer();




        if(alarm_index != -1){
            a = AlarmsActivity.al_alarms.get(alarm_index);
            queueValidTasks();
        }
        timerIsActive = false;
        inflateTask();}


    public void button_muteClicked(View view) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            mp_position = mediaPlayer.getCurrentPosition();
            button_mute.setImageResource(R.drawable.baseline_music_note_24);

        }else{
            mediaPlayer = MediaPlayer.create(this, R.raw.solving_music);
            mediaPlayer.start();
            mediaPlayer.pause();
            mediaPlayer.seekTo(mp_position);
            mediaPlayer.start();
            button_mute.setImageResource(R.drawable.baseline_music_off_24);
        }
    }

    public void queueValidTasks() {
        for (AlarmTask at : a.getAl_AlarmTasks()) {
            if (at == null) {
                continue;
            } else {
                task_queue.add(at);
            }
        }
    }


    public void startSpecificTimer(int seconds_for_task_completion) {
        if(timerIsActive == false) {

            mediaPlayer = MediaPlayer.create(this, R.raw.solving_music);
            mediaPlayer.start();
            pb_remaining_time.setMax(seconds_for_task_completion * 10);
            pb_remaining_time.setProgress(seconds_for_task_completion * 10);
            countdown_timer = new CountDownTimer(seconds_for_task_completion * 1000, 200) {
                @Override
                public void onTick(long l) {
                    if ((pb_remaining_time.getProgress() >= 20)) {
                        pb_remaining_time.setProgress(pb_remaining_time.getProgress() - 2);
                    }
                }

                @Override
                public void onFinish() {
                    if(timerIsActive == true){
                        mediaPlayer.stop();
                        mediaPlayer = MediaPlayer.create(context, R.raw.repetitive_bssssw);
                        mediaPlayer.start();
                        mediaPlayer.setVolume(1.0f, 1.0f);
                        timerIsActive = false;
                    }
                }
            };
            countdown_timer.start();
            timerIsActive = true;
        }
    }

    public void inflateTask(){
        if(task_queue.peek() != null){
            task =   task_queue.poll();
            time4task = task.length;
             difficuty = task.getDifficulty();
             toSolves = task.getNumberOfToSolves();
             tv_total_solved.setText("0/" + toSolves);
             solved = 0;
             //Sound next task
             generateMathTask();
        }else {
            //All done - no tasks left
           if (mediaPlayer.isPlaying()) mediaPlayer.stop();
           if (timerIsActive) countdown_timer.cancel();
           startActivity(new Intent(this, MainActivity.class));
           //Sound done
        }
    }


    public void generateMathTask(){
        int firstNumber = (int) (Math.random() * 100);
        int secondNumber = (int) (Math.random() * 100);
        int thirdNumber = (int) (Math.random() * 100);
        String generatedToSolve = "" + firstNumber;
        int outcome = firstNumber;

        //First operation
        switch ((int)  (Math.random() * 2)) {
            case 0:
                outcome -= secondNumber;
                generatedToSolve += " - " + secondNumber;
                break;
            case 1:
                outcome += secondNumber;
                generatedToSolve += " + " + secondNumber;
                break;
        }

        //Second operation
        switch ((int)  (Math.random() * 2)) {
            case 0:
                outcome -= thirdNumber;
                generatedToSolve += " - " + thirdNumber;
                break;
            case 1:
                outcome += thirdNumber;
                generatedToSolve += " + " + thirdNumber;
                break;
        }

        generatedToSolve += " =";
        currentOutcome = outcome;
        tv_toSolve.setText(generatedToSolve);
        startSpecificTimer(time4task);
    }

    public void button_checkClicked(View view) {
        if(et_input.getText().toString().equals("" + currentOutcome)){
            solved += 1;
            tv_total_solved.setText(solved + "/" + toSolves);
            mediaPlayer.stop();
            timerIsActive = false;
            countdown_timer.cancel();
            et_input.setText("");
            if (solved >= toSolves){
                inflateTask();
                return;
            }
            generateMathTask();
        }else {
            //Sound incorrect
        }
    }

}