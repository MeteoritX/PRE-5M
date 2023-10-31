package com.example.weckerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.weckerapp.alarmTasks.MathTask;

import org.w3c.dom.Text;

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

    }


    public void button_saveToSolvesClicked(View view) {
        int alarm_index = extras.getInt("alarm_index", -1);
        if (alarm_index != -1) {
            Alarm a = AlarmsActivity.al_alarms.get(alarm_index);
            a.setAtheneosAlarm(true);
            t.cancel();
            t.purge();

            int task_index = extras.getInt("task_index");
            MathTask mt = new MathTask(sb_number.getProgress(), sb_difficulty.getProgress());
            a.al_AlarmTasks.add(task_index, mt);
            int slot_res_number = extras.getInt("task_slot");
            View v = findViewById(slot_res_number);  //v = null id is not there ?! -> Null-Check und manuell über die id setzen, I guess
            ((ImageView) v).setImageResource(R.drawable.baseline_calculate_24); //Null-Ref
            ((ImageView) findViewById(extras.getInt("task_slot_bg"))).setBackgroundColor(getResources().getColor(R.color.atheneos_yellow));

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
}