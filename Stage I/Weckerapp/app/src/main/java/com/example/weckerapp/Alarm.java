package com.example.weckerapp;

public class Alarm {
    String title;
    int symbol;
    boolean atheneosAlarm;
    boolean active;

    //Activities (Stage II)

    public Alarm() {
    }

    public Alarm(String title, int symbol, boolean atheneosAlarm, boolean active) {
        this.title = title;
        this.symbol = symbol;
        this.atheneosAlarm = atheneosAlarm;
        this.active = active;
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
}
