package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar_settings;
    Switch switch_24h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_settings);

        toolbar_settings = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        actionBar.setTitle(getResources().getString(R.string.text_settings_title));

        switch_24h = findViewById(R.id.switch_24h);
        loadSettings();
    }


    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean switchState = sharedPreferences.getBoolean("switch_24h", false);
        switch_24h.setChecked(switchState);
    }

    public void saveCurrenSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("switch_24h", switch_24h.isChecked());
        editor.apply();
    }

    public void button_saveSettings_Clicked(View view) {
        saveCurrenSettings();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}