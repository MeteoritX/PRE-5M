package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
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

        if(extras == null){
            intent_from_module = getIntent();
            extras = intent_from_module.getExtras();
        }

        context = this;

        if(al_modules == null){
            al_modules = new ArrayList<>();
        }
        lv_modules = findViewById(R.id.lv_modules);
        lv_modules.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, al_modules));

        //Stage II only
        if(al_modules.size() == 0){
            al_modules.add(getResources().getString(R.string.module_arithmetics));
            al_modules.add(getResources().getString(R.string.module_algebra));
            al_modules.add(getResources().getString(R.string.module_geometry));
        }

        lv_modules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, ChooseToSolvesActivity.class);
                intent.putExtras(extras);
                intent.putExtra("module_indexer", al_modules.get(i));
                startActivity(intent);
            }
        });
    }
}