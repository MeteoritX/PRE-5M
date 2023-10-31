package com.example.weckerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ChooseToSolvesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_to_solves);
        setSupportActionBar(findViewById(R.id.toolbar_toSolves));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



       ((TextView)findViewById(R.id.textView4)).setText(""+ getIntent().getExtras().getInt("module_index", -1));

        //Extras auspacken (Slots), Background färben, Schieberegler für Schwierigkeit + Aufgabenanzahl, atheneos = true
    }
}