package com.example.weckerapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Alarm {
    String title;
    int symbol;
    boolean atheneosAlarm;
    boolean active;
    String displayed_title;

    //Activities (Stage II)

    public Alarm() {
    }

    public Alarm(String title, int symbol, boolean atheneosAlarm, boolean active) {
        this.title = title;
        this.symbol = symbol;
        this.atheneosAlarm = atheneosAlarm;
        this.active = active;
        refreshDisplayedTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public boolean isAtheneosAlarm() {
        return atheneosAlarm;
    }

    public void setAtheneosAlarm(boolean atheneosAlarm) {
        this.atheneosAlarm = atheneosAlarm;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void refreshDisplayedTitle(){
        SharedPreferences sharedPreferences = MainActivity.ctx.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("switch_24h", false)){
            this.displayed_title = title;
        }else{
            String hour = title.split(":")[0];
            String min = title.split(":")[1];
            displayed_title = (Integer.parseInt(hour)%12) + ":" + min;
           if(Integer.parseInt(hour) >= 12){
                displayed_title += " PM";
           }else{
               displayed_title += " AM";
           }
        }
    }

    public String getDisplayed_title() {
        return displayed_title;
    }
}
