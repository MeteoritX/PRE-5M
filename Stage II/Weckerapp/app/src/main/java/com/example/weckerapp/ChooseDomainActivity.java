package com.example.weckerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

        if(extras == null){
           extras = getIntent().getExtras();
        }
    }


    public void domain_mathClicked(View view) {
       Intent intent = new Intent(this, ChooseModuleActivity.class);
       intent.putExtras(extras);
       startActivity(intent);
    }

    public void domain_medClicked(View view) {
        //Stage IV
    }

    public void domain_lingClicked(View view) {
        //Stage IV
    }
}