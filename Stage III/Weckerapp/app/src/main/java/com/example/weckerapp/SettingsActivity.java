package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar_settings;
    Switch switch_24h;
    Switch switch_nightMode;
    Switch switch_reducedMode;
    SeekBar sb_textScale;
    View primaryColour;
    View secondaryColour;


    static int prim;
    static int sec;
    static Intent intentToColourPicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SET THEME before bundle
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_settings);

        toolbar_settings = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        actionBar.setTitle(getResources().getString(R.string.text_settings_title));
        switch_nightMode = findViewById(R.id.switch_nightMode);
        switch_reducedMode = findViewById(R.id.switch_reducedMode);
        sb_textScale = findViewById(R.id.seekBar_textScale);
        primaryColour = findViewById(R.id.primaryColour);
        secondaryColour = findViewById(R.id.secondaryColour);
        intentToColourPicker = new Intent(this, ColourPickerActivity.class);

        //Initial Create
        if(prim == 0 && sec == 0){
            prim = ((ColorDrawable) primaryColour.getBackground()).getColor();
            sec = ((ColorDrawable) secondaryColour.getBackground()).getColor();
        }
        switch_24h = findViewById(R.id.switch_24h);
        loadSettings();

        primaryColour.setBackgroundColor(prim);
        secondaryColour.setBackgroundColor(sec);

    }


    public void loadSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean switchState = sharedPreferences.getBoolean("switch_24h", false);
        switch_24h.setChecked(switchState);
        //Stage III
        switch_nightMode.setChecked(sharedPreferences.getBoolean("switch_nightMode", false));
        switch_reducedMode.setChecked(sharedPreferences.getBoolean("switch_reducedMode", false));
        sb_textScale.setProgress(Integer.parseInt(("" + sharedPreferences.getFloat("sb_textScale", 1.0f)).substring(2, 3)));
        primaryColour.setBackgroundColor(sharedPreferences.getInt("primaryColour", ((ColorDrawable) primaryColour.getBackground()).getColor()));
        secondaryColour.setBackgroundColor(sharedPreferences.getInt("secondaryColour", ((ColorDrawable) secondaryColour.getBackground()).getColor()));
            findViewById(R.id.container_settings).setBackgroundColor(sec);

        int nightModeFlag = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(nightModeFlag == Configuration.UI_MODE_NIGHT_NO){
            switch_nightMode.setChecked(false);
        }else if (nightModeFlag == Configuration.UI_MODE_NIGHT_YES) {
            switch_nightMode.setChecked(true);
        }

    }

    public void saveCurrenSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("switch_24h", switch_24h.isChecked());
        //Stage III
        editor.putBoolean("switch_nightMode", switch_nightMode.isChecked());
        editor.putBoolean("switch_reducedMode", switch_reducedMode.isChecked());
        editor.putFloat("sb_textScale", Float.parseFloat("1." + sb_textScale.getProgress()));
        editor.putInt("primaryColour", prim);
        editor.putInt("secondaryColour", sec);
        editor.apply();
    }

    public void button_saveSettings_Clicked(View view) {
        saveCurrenSettings();
        if(switch_nightMode.isChecked()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void primaryColourClicked(View view) {
        saveCurrenSettings();
        intentToColourPicker.putExtra("title", getResources().getString(R.string.text_primaryColour));
        intentToColourPicker.putExtra("current_colour", ((ColorDrawable)primaryColour.getBackground()).getColor());
        startActivity(intentToColourPicker);
    }

    public void secondaryColourClicked(View view) {
        saveCurrenSettings();
        intentToColourPicker.putExtra("title", getResources().getString(R.string.text_secondaryColour));
        intentToColourPicker.putExtra("current_colour", ((ColorDrawable)secondaryColour.getBackground()).getColor());
        startActivity(intentToColourPicker);
    }


    public void resetClicked(View view) {
        switch_24h.setChecked(false);
        switch_nightMode.setChecked(false);
        switch_reducedMode.setChecked(false);
        sb_textScale.setProgress(0);
        primaryColour.setBackgroundColor(Color.rgb(Integer.valueOf("FF", 16), Integer.valueOf("A5", 16), Integer.valueOf("00", 16)));
        secondaryColour.setBackgroundColor(Color.rgb(Integer.valueOf("6A", 16), Integer.valueOf("6A", 16), Integer.valueOf("6A", 16)));
        prim = getResources().getColor(R.color.atheneos_yellow);
        sec = getResources().getColor(R.color.atheneos_LightGrey);
    }
}