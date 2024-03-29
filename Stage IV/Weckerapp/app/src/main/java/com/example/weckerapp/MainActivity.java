package com.example.weckerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.weckerapp.alarmTasks.AlarmTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    Chronometer chronometer;
    boolean chronometerRunning;
    long delta_t;
    TextView tv_delta_t;
    Button button_start_stop;
    TextView tv_remainingSeconds;
    TimePicker spinnerClock;
    boolean countdownRunning;
    Button button_startCountdown;
    FloatingActionButton button_cancelCountdown;
    ProgressBar progressBar_chronometer;
    ProgressBar progressBar_decorator;
    ProgressBar progressBar_outer;
    Button button_setAlarm;
    static Context ctx;
    static boolean audioActivated;
    static Timer timer;

    static MediaPlayer mediaPlayer;

    static int globalPrimaryColour; //Accents and input elements
    static int globalSecondaryColour; //Backgrounds
    TabHost tabHost;

    TextClock tc;
    TimePicker tp;

    TextView tv_lab_remainingSeconds;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tc = findViewById(R.id.tec);
        tp = findViewById(R.id.tip);

        //region Tabs
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("Wecker");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Wecker");
        tabHost.addTab(spec);


        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        if((sharedPreferences.getBoolean("switch_reducedMode", false))){
            for (int i = 0; i <  ((LinearLayout) findViewById(R.id.tab2)).getChildCount(); i++) {
                ((LinearLayout) findViewById(R.id.tab2)).getChildAt(i).setAlpha(0.0f);
            }

            for (int i = 0; i <   ((LinearLayout) findViewById(R.id.tab3)).getChildCount(); i++) {
                ((LinearLayout) findViewById(R.id.tab3)).getChildAt(i).setAlpha(0.0f);
            }
        }else {
            findViewById(R.id.tab2).setVisibility(View.VISIBLE);
            findViewById(R.id.tab3).setVisibility(View.VISIBLE);
            spec = tabHost.newTabSpec("Timer");
            spec.setContent(R.id.tab2);
            spec.setIndicator("Timer");
            tabHost.addTab(spec);
            spec = tabHost.newTabSpec("Stoppuhr");
            spec.setContent(R.id.tab3);
            spec.setIndicator("Stoppuhr");
            tabHost.addTab(spec);
        }
        //endregion

        //Initialise reference variables
        chronometer = (Chronometer)  findViewById(R.id.chron);
        chronometer.setFormat("%h:%m:%s");
        chronometerRunning = false;
        button_start_stop = (Button) findViewById(R.id.button_start_stop);
        tv_delta_t = (TextView) findViewById(R.id.tv_delta_t);

        tv_remainingSeconds = (TextView) findViewById(R.id.tv_remainingSeconds);
        spinnerClock = (TimePicker) findViewById(R.id.spinnerClock);
        countdownRunning = false;
        spinnerClock.setIs24HourView(true);
        button_startCountdown = (Button) findViewById(R.id.button_startCountdown);
        button_cancelCountdown = (FloatingActionButton) findViewById(R.id.button_cancel_countdown);
        button_cancelCountdown.setEnabled(false);
        button_cancelCountdown.setAlpha(0.75f);
        progressBar_chronometer = findViewById(R.id.progressBar_chrono);
        progressBar_chronometer.setIndeterminate(false);
        progressBar_chronometer.setMax(59);
        delta_t = 0;
        progressBar_decorator = findViewById(R.id.progressBar_decorator);
        progressBar_decorator.setMax(29);
        progressBar_outer = findViewById(R.id.progressBar_outer);
        progressBar_outer.setMax(14);
        audioActivated = false;
        tv_lab_remainingSeconds = findViewById(R.id.tv_text_remainingSec);

        if(AlarmsActivity.al_alarms == null){
            AlarmsActivity.al_alarms = new ArrayList<Alarm>();
        }

        button_setAlarm = findViewById(R.id.button_setAlarm);
        loadAlarms();
        ctx = this;

        //Cyclic alarm check
        if(timer == null){
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    for (Alarm a : AlarmsActivity.al_alarms) {
                        Date date = Calendar.getInstance().getTime();
                        int h = date.getHours();
                        int m = date.getMinutes();
                        String[] split_title = a.title.split(":");
                        if(Integer.parseInt(split_title[0]) == h && Integer.parseInt(split_title[1]) == m && a.active && !audioActivated ){
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
                            mediaPlayer.start();
                            audioActivated = true;

                            //Redirect to Popup-Task
                            Intent intent = new Intent(ctx, PopupTaskAktivity.class);
                            intent.putExtra("alarm_index", AlarmsActivity.al_alarms.indexOf((Alarm) a));
                            startActivity(intent);
                        }
                    }
                }
            },0,16000);
        }
        verifyPermission(this);
        loadSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Setting up a top menue for settings / access to defined alarms
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //region Menue Navigatiors
        //Click listener: Checking which menue item has been clicked
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.alarms:
                Intent intent_alarms = new Intent(this, AlarmsActivity.class);
                startActivity(intent_alarms);
                return true;

            case R.id.score:
                Intent intent_score = new Intent(this, ShowScoreActivity.class);
                startActivity(intent_score);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
        //endregion Menue Navigators
    }

    public void button_setAlarm_Clicked(View view) throws ParseException {
        String h = (tp.getHour() < 10)  ? "0" + tp.getHour()  : "" + tp.getHour();
        String m = (tp.getMinute() < 10)  ? "0" + tp.getMinute()  : "" + tp.getMinute();

        AlarmsActivity.al_alarms.add(new Alarm((h+":"+m), R.drawable.baseline_alarm_24, false, true));
        Toast.makeText(this, "Alarm  gesetzt", Toast.LENGTH_SHORT).show();
        saveAlarms();
    }


    public void button_start_stop_Clicked(View view) {
        //region Chronometer
        if(!chronometerRunning)
        {
            progressBar_decorator.setVisibility(View.VISIBLE);
            chronometer.setBase(SystemClock.elapsedRealtime());

            chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    delta_t = SystemClock.elapsedRealtime() - chronometer.getBase();
                    progressBar_chronometer.setProgress((int) ((delta_t/1000)%60));
                    progressBar_decorator.setProgress((int) ((delta_t/1000)%30));
                    progressBar_outer.setProgress((int) ((delta_t/1000)%15));
                }
            });
            chronometer.start();
            chronometerRunning = true;
            button_start_stop.setText(getResources().getString(R.string.text_button_stop));


        }else {
            chronometer.stop();
            chronometerRunning = false;
            button_start_stop.setText(getResources().getString(R.string.text_button_start));
            delta_t = SystemClock.elapsedRealtime() - chronometer.getBase();
            tv_delta_t.setText(getResources().getString(R.string.text_delta_t) + "\n" + ((float) delta_t/1000) + "s");
            progressBar_chronometer.setProgress(0);
            progressBar_decorator.setProgress(0);
            progressBar_outer.setProgress(0);
        }
        //endregion
    }

    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean switchState = sharedPreferences.getBoolean("switch_24h", false);
        tp.setIs24HourView(switchState);
        SettingsActivity.prim = sharedPreferences.getInt("primaryColour", R.color.atheneos_yellow);
        SettingsActivity.sec = sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey);
        tabHost.setBackgroundColor(SettingsActivity.sec);


        int nightModeFlag = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(nightModeFlag == Configuration.UI_MODE_NIGHT_NO){
            button_setAlarm.setBackgroundColor(SettingsActivity.prim);
            tabHost.getTabWidget().setBackgroundColor(SettingsActivity.prim);
            button_startCountdown.setBackgroundColor(SettingsActivity.prim);
            button_cancelCountdown.setBackgroundTintList(ColorStateList.valueOf(SettingsActivity.prim));
            button_start_stop.setBackgroundColor(SettingsActivity.prim);
            progressBar_outer.getProgressDrawable().setColorFilter(SettingsActivity.prim, android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar_decorator.getProgressDrawable().setColorFilter(SettingsActivity.prim, android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar_chronometer.getProgressDrawable().setColorFilter(SettingsActivity.prim, android.graphics.PorterDuff.Mode.SRC_IN);
        }else if (sharedPreferences.getBoolean("switch_nightMode", false)){
          Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 55);
        }
        float textScale = sharedPreferences.getFloat("sb_textScale", 1.0f);
        button_setAlarm.setTextSize(16*textScale);
        button_start_stop.setTextSize(16*textScale);
        button_startCountdown.setTextSize(16*textScale);
        tv_lab_remainingSeconds.setTextSize(24*textScale);
        tv_delta_t.setTextSize(30*textScale);
    }


    //region Countdown
    @SuppressLint("RestrictedApi")
    public void button_startCountdonwn_Clicked(View view) {
        if(!countdownRunning){

            int startMin = spinnerClock.getHour(); //treat as minute
            int startSec = spinnerClock.getMinute(); //treat as sectond
            int totalMilliseconds = startMin * 60000 + startSec * 1000;
            button_startCountdown.setEnabled(false);
            countdownRunning = true;
            button_cancelCountdown.setEnabled(true);
            button_cancelCountdown.setAlpha(1.0f);


            new CountDownTimer(totalMilliseconds, 1000) {

                public void onTick(long millisUntilFinished) {
                    if(countdownRunning){
                        tv_remainingSeconds.setText("" + millisUntilFinished / 1000);
                        spinnerClock.setHour((int) (millisUntilFinished / 60000));
                        spinnerClock.setMinute(((int) (millisUntilFinished / 1000)) % 60);
                    }else{
                        this.cancel();
                    }

                }

                public void onFinish() {
                    tv_remainingSeconds.setText("0");
                    spinnerClock.setHour(0);
                    spinnerClock.setMinute(0);
                    countdownRunning = false;
                    button_startCountdown.setEnabled(true);
                    button_cancelCountdown.setEnabled(false);
                    button_cancelCountdown.setAlpha(0.75f);
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
                    mediaPlayer.start();
                }
            }.start();
        }


    }

    @SuppressLint("RestrictedApi")
    public void button_cancel_countdownClicked(View view) {
        button_cancelCountdown.setEnabled(false);
        button_cancelCountdown.setAlpha(0.75f);
        button_startCountdown.setEnabled(true);
        countdownRunning = false;
    }
    //endregion

    public void saveAlarms(){
        SharedPreferences sharedPreferences = getSharedPreferences("Alarms", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        editor.apply();

        for (int i = 0; i < AlarmsActivity.al_alarms.size(); i++) {
            editor.putString("title" + i, AlarmsActivity.al_alarms.get(i).title);
            editor.putInt("symbol" + i, AlarmsActivity.al_alarms.get(i).symbol);
            editor.putBoolean("atheneosAlarm" + i, AlarmsActivity.al_alarms.get(i).atheneosAlarm);
            editor.putBoolean("active" + i, AlarmsActivity.al_alarms.get(i).active);

            //Atheneos expansion
            for (int j = 0; j < AlarmsActivity.al_alarms.get(i).al_AlarmTasks.size(); j++) {
                if(AlarmsActivity.al_alarms.get(i).al_AlarmTasks.get(j) == null) continue;
                editor.putInt(j + "difficulty" + i, AlarmsActivity.al_alarms.get(i).al_AlarmTasks.get(j).getDifficulty());
                editor.putInt(j + "number" + i, AlarmsActivity.al_alarms.get(i).al_AlarmTasks.get(j).getNumberOfToSolves());
                editor.putInt(j + "dom" + i, AlarmsActivity.al_alarms.get(i).al_AlarmTasks.get(j).getDom());
                editor.putString(j + "module" + i, AlarmsActivity.al_alarms.get(i).al_AlarmTasks.get(j).getModule());
            }

        }
        editor.apply();
    }

    public void loadAlarms(){
        SharedPreferences sharedPreferences = getSharedPreferences("Alarms", Context.MODE_PRIVATE);
        try{
            int i = 0;
            AlarmsActivity.al_alarms.clear();
            while(true){
                if (sharedPreferences.getString("title" + i, "-").equals("-")) return;

                Alarm a =  new Alarm(
                        sharedPreferences.getString("title" + i, "--:--"),
                        sharedPreferences.getInt("symbol" + i, R.drawable.baseline_alarm_24),
                        sharedPreferences.getBoolean("atheneosAlarm" + i, false),
                        sharedPreferences.getBoolean("active" + i, false));

                //Atheneos expansion
                for (int j = 0; j < 6; j++) {

                   int difficulty = sharedPreferences.getInt(j + "difficulty" + i, 0);
                   int number = sharedPreferences.getInt(j + "number" + i, 0);
                   int dom = sharedPreferences.getInt(j + "dom" + i, 0);
                   String module = sharedPreferences.getString(j + "module" + i, "");

                   if(difficulty != 0){
                       a.al_AlarmTasks.set(j, new AlarmTask(number, difficulty, dom, module));
                   }
                }

                AlarmsActivity.al_alarms.add(a);
                i++;
            }
        }catch (Exception e) {
            Toast.makeText(this, "EXCEPTION", Toast.LENGTH_SHORT);
        }
    }

public void verifyPermission(Context ctx){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(Settings.System.canWrite(ctx)){
                //Permission is granted
            }else{
                Intent intentChangePermission = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                ctx.startActivity(intentChangePermission);
            }
        }
}

}