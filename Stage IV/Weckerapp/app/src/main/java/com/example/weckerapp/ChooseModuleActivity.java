package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseModuleActivity extends AppCompatActivity {

    ListView lv_modules;
    static ArrayList<String> al_modules; //String 4 testing
    static Context context;
    static Intent intent_from_module;

    static Bundle extras; //Nach dem Speichern auf NULL setzen!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_module);
        setSupportActionBar(findViewById(R.id.toolbar_mod));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(extras == null || extras.getInt("dom", -1 ) != -1){
            intent_from_module = getIntent();
            extras = intent_from_module.getExtras();

            // !!!!!!!! Rücktaste von ChooseToSovesActiviy führt zu Fehler
        }

        context = this;

        if(al_modules == null){
            al_modules = new ArrayList<>();
        }
        lv_modules = findViewById(R.id.lv_modules);
        lv_modules.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, al_modules));

        //Stage IV

        al_modules.clear();
        if(extras.getInt("dom",-1) == 0){
            al_modules.add(getResources().getString(R.string.module_arithmetics));
            al_modules.add(getResources().getString(R.string.module_algebra));
            al_modules.add(getResources().getString(R.string.module_geometry));
        }
        if(extras.getInt("dom",-1) == 1){

            al_modules.add(getResources().getString(R.string.module_anatomyTerms));
            al_modules.add(getResources().getString(R.string.module_anatomyModel));
            al_modules.add(getResources().getString(R.string.module_somethingMedical));
        }
        if(extras.getInt("dom",-1) == 2){

            al_modules.add(getResources().getString(R.string.module_lateinDic));
            al_modules.add(getResources().getString(R.string.module_englishDic));
            al_modules.add(getResources().getString(R.string.module_spanishDic));
        }

        int SelectedModul = extras.getInt("dom");


        lv_modules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, ChooseToSolvesActivity.class);
                intent.putExtras(extras);
                intent.putExtra("dom", SelectedModul);
                intent.putExtra("module_indexer", al_modules.get(i));
                startActivity(intent);
            }
        });
        loadSettings();
    }

    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SettingsActivity.prim = sharedPreferences.getInt("primaryColour", R.color.atheneos_yellow);
        SettingsActivity.sec = sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey);
        findViewById(R.id.container_modules).setBackgroundColor(SettingsActivity.sec);
    }
}