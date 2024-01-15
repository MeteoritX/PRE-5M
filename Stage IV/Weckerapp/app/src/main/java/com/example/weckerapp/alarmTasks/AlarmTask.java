package com.example.weckerapp.alarmTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.content.SharedPreferences;

import com.example.weckerapp.R;

//This resembles a Task (Aufgabenstellung)
public class AlarmTask {

    int numberOfToSolves; //From 1 to 10
    int difficulty; //From 1 to 10
    int dom;
    String module; //e.g. "Arithmetics"

    public int length;




    public AlarmTask() {
    }

    public AlarmTask(int numberOfToSolves, int difficulty, int dom, String module) {
        this.numberOfToSolves = numberOfToSolves;
        this.difficulty = difficulty;
        this.dom = dom;
        this.module = module;

            switch (difficulty){
                case 1:
                    this.length = 300;
                    break;
                case 2:
                    this.length = 260;
                    break;
                case 3:
                    this.length = 220;
                    break;
                case 4:
                    this.length = 200;
                    break;
                case 5:
                    this.length = 180;
                    break;
                case 6:
                    this.length = 160;
                    break;
                case 7:
                    this.length = 140;
                    break;
                case 8:
                    this.length = 120;
                    break;
                case 9:
                    this.length = 100;
                    break;
                case 10:
                    this.length = 60;
                    break;
            }



    }

    public boolean hasBeenCompleted(){
        return false;
    };

    public int getNumberOfToSolves() {
        return numberOfToSolves;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getDom() {
        return dom;
    }

    public String getModule() {
        return module;
    }
}
