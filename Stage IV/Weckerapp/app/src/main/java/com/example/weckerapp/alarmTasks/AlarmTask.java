package com.example.weckerapp.alarmTasks;

import android.media.MediaPlayer;
import android.provider.Settings;

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
        this.length = 60;
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
