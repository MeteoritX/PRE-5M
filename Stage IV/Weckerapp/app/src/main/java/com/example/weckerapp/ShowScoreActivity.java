package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowScoreActivity extends AppCompatActivity {

Button button_back;
TextView tv_score;
TextView tv_score_number;

static int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);
        setSupportActionBar(findViewById(R.id.toolbar_showScore));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button_back = findViewById(R.id.button_back);
        tv_score = findViewById(R.id.tv_score);
        tv_score_number = findViewById(R.id.tv_score_number);
        score++; //TEST ONLY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        tv_score_number.setText("" + score);
        loadSettings();
    }


    public void button_backClicked(View view) {
        startActivity( new Intent(this, MainActivity.class));
    }

    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        float textScale = sharedPreferences.getFloat("sb_textScale", 1.0f);
        button_back.setTextSize(16*textScale);
        tv_score.setTextSize(16*textScale);
        tv_score_number.setTextSize(16*textScale);
        findViewById(R.id.container_showScore).setBackgroundColor(sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey));
    }
}