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
    ArrayList<String> al_modules; //String 4 testing
    static Context context;
    static Intent intent_from_module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_module);
        setSupportActionBar(findViewById(R.id.toolbar_mod));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent_from_module = getIntent();
        context = this;

        if(al_modules == null){
            al_modules = new ArrayList<>();
        }
        lv_modules = findViewById(R.id.lv_modules);
        lv_modules.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, al_modules));

        //Stage II only
        al_modules.add("Arithmetik");
        al_modules.add("Algebra");
        al_modules.add("Geometrie");
        lv_modules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, ChooseToSolvesActivity.class);
                intent.putExtras(intent_from_module.getExtras());
                intent.putExtra("module_index", i);
                startActivity(intent);
            }
        });
    }
}