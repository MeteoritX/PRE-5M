package com.example.weckerapp;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weckerapp.alarmTasks.AlarmTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Timer;

public class SolveTaskMedical extends AppCompatActivity {


    ArrayMap<String, String> dic;
    TextView tv_toSolve;
    TextView tv_total_solved;
    EditText et_input;
    Button button_check;
    ProgressBar pb_remaining_time;
    FloatingActionButton button_mute;
    static CountDownTimer countdown_timer;
    static boolean timerIsActive;
    MediaPlayer mediaPlayer1;

    MediaPlayer mediaPlayer2;

    Icon icon1;
    Icon icon2;
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
        if(mediaPlayer1 == null) {
            mediaPlayer1 = MediaPlayer.create(this, R.raw.solving_music);
        }
        if(mediaPlayer2 == null) {
            mediaPlayer2 = MediaPlayer.create(this, R.raw.repetitive_bssssw);
        }

        icon1 = Icon.createWithResource(this, R.drawable.baseline_music_note_24);
        icon2 = Icon.createWithResource(this, R.drawable.baseline_music_off_24);

        setContentView(R.layout.activity_solve_task_medical);

        setSupportActionBar(findViewById(R.id.toolbar_solve));
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_solve_task));

        Intent testIntent = getIntent();
        Bundle b = testIntent.getExtras();
        alarm_index = b.getInt("alarm_index", -1); //nullpointer
        button_check = findViewById(R.id.button_check);
        task_queue = new ArrayDeque<>();
        pb_remaining_time = findViewById(R.id.progressBar_solveTask);
        button_mute = findViewById(R.id.button_mute);
        context = this;
        tv_toSolve = findViewById(R.id.tv_toSolve);
        tv_total_solved = findViewById(R.id.tv_total_finished);
        et_input = findViewById(R.id.et_input);
        timer_intermezzo = new Timer();


        if (alarm_index != -1) {
            a = AlarmsActivity.al_alarms.get(alarm_index);
            queueValidTasks();
        }
        timerIsActive = false;
        try {
            inflateTask();
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadSettings();
    }


    public void button_muteClicked(View view) {
        if (mediaPlayer1.isPlaying()) {
            mediaPlayer1.pause();
            mp_position = mediaPlayer1.getCurrentPosition();
            button_mute.setImageIcon(icon1);

        } else {
            if(!mediaPlayer2.isPlaying()) {
                mediaPlayer1.seekTo(mp_position);
                mediaPlayer1.start();
                button_mute.setImageIcon(icon2);
            }
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
        if (timerIsActive == false) {


            mediaPlayer1.start();
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
                    if (timerIsActive == true) {
                        mediaPlayer1.stop();
                        mediaPlayer2.start();
                        mediaPlayer2.setVolume(1.0f, 1.0f);
                        timerIsActive = false;
                    }
                }
            };
            countdown_timer.start();
            timerIsActive = true;
        }
    }

    public void inflateTask() throws XmlPullParserException, IOException {


        if (task_queue.peek() != null) {
            task = task_queue.poll();
            time4task = task.length;
            difficuty = task.getDifficulty();
            toSolves = task.getNumberOfToSolves();
            tv_total_solved.setText("0/" + toSolves);
            solved = 0;
            //Sound next task
            generateMedicalTask();
        } else {
            //All done - no tasks left
            if (mediaPlayer1.isPlaying()) mediaPlayer1.stop();
            if (mediaPlayer2.isPlaying()) mediaPlayer2.stop();
            if (timerIsActive) countdown_timer.cancel();

            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }

            Runtime.getRuntime().exit(0);
            //Sound done
        }

    }


    public void generateMedicalTask() throws XmlPullParserException, IOException {
        if(dic == null){
            dic = XML.readXML("anatomie.xml", "Latein", "Deutsch");
        }
        int word = (int) (dic.size() * Math.random());
        String key = dic.keyAt(word);
        tv_toSolve.setText(key);
        startSpecificTimer(time4task);
    }


    public void button_checkClicked(View view) throws XmlPullParserException, IOException {


        if (et_input.getText().toString().equals(dic.get(tv_toSolve.getText()))) {
            solved += 1;
            tv_total_solved.setText(solved + "/" + toSolves);
            mediaPlayer1.stop();
            mediaPlayer2.stop();
            timerIsActive = false;
            countdown_timer.cancel();
            et_input.setText("");
            if (solved >= toSolves) {
                inflateTask();
                return;
            }
            generateMedicalTask();
        } else {
            //Sound incorrect
        }


    }

    public void loadSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SettingsActivity.prim = sharedPreferences.getInt("primaryColour", R.color.atheneos_yellow);
        SettingsActivity.sec = sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey);
        findViewById(R.id.container_solveTask).setBackgroundColor(SettingsActivity.sec);
        float textScale = sharedPreferences.getFloat("sb_textScale", 1.0f);
        button_check.setTextSize(16 * textScale);
    }
}







