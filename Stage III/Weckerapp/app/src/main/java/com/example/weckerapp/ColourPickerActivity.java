package com.example.weckerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ColourPickerActivity extends AppCompatActivity {

TextView tv_title;
TextView tv_rgb_hex;
View current_colour;
ImageView iv_colourPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_picker);
        setSupportActionBar(findViewById(R.id.toolbar_colourPicker));
        tv_title = findViewById(R.id.tv_colour);
        tv_rgb_hex = findViewById(R.id.tv_rgb_hex);
        iv_colourPicker = findViewById(R.id.iv_colourPicker);
        iv_colourPicker.setDrawingCacheEnabled(true);
        iv_colourPicker.buildDrawingCache(true);
        tv_title.setText(getIntent().getExtras().getString("title", ""));
        current_colour = findViewById(R.id.current_colour);
        current_colour.setBackgroundColor(getIntent().getExtras().getInt("current_colour", 0));


        iv_colourPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    Bitmap bitmap = iv_colourPicker.getDrawingCache();
                    int pixel = bitmap.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());
                    int r = Color.red(pixel);
                    int g= Color.green(pixel);
                    int b = Color.blue(pixel);
                    tv_rgb_hex.setText("RGB: " + r + ", " + g + ", " + b + "       HEX: #" + Integer.toHexString(pixel));
                    current_colour.setBackgroundColor(Color.rgb(r,g,b));
                }
                return true;
            }
        });

        loadSettings();
    }

    public void button_saveColour_Clicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        if(tv_title.getText().equals(getResources().getString(R.string.text_primaryColour))){
           SettingsActivity.prim = ((ColorDrawable) current_colour.getBackground()).getColor();
        }else if(tv_title.getText().equals(getResources().getString(R.string.text_secondaryColour))){
            SettingsActivity.sec = ((ColorDrawable) current_colour.getBackground()).getColor();
        }
        startActivity(intent);
    }

    public void loadSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SettingsActivity.prim = sharedPreferences.getInt("primaryColour", R.color.atheneos_yellow);
        SettingsActivity.sec = sharedPreferences.getInt("secondaryColour", R.color.atheneos_LightGrey);
        findViewById(R.id.container_colourPicker).setBackgroundColor(SettingsActivity.sec);
    }
}