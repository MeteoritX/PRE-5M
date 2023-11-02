package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.weckerapp.alarmTasks.Domain;

public class ChooseDomainActivity extends AppCompatActivity {

   static Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_domain);
        setSupportActionBar(findViewById(R.id.toolbar_doms));
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_doms));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadSettings();
        if(extras == null){
           extras = getIntent().getExtras();
        }
    }


    public void domain_mathClicked(View view) {
       Intent intent = new Intent(this, ChooseModuleActivity.class);
       intent.putExtras(extras);
       intent.putExtra("dom", Domain.DOM_MATHEMATICS.ordinal());
       startActivity(intent);
    }

    public void domain_medClicked(View view) {
        //Stage IV
        //Dom Enum
    }

    public void domain_lingClicked(View view) {
        //Stage IV
        //Dom Enum
    }

    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SettingsActivity.prim = sharedPreferences.getInt("primaryColour", R.color.atheneos_yellow);
        SettingsActivity.sec = sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey);
        findViewById(R.id.container_domains).setBackgroundColor(SettingsActivity.sec);
    }
}