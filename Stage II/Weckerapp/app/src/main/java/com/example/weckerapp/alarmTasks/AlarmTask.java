package com.example.weckerapp.alarmTasks;

import java.util.ArrayList;

//This resembles a Task (Aufgabenstellung)
public abstract class AlarmTask {
    ArrayList<ToSolve> al_toSolves;

    public AlarmTask() {
    }

    public AlarmTask(int numberOfToSolves, int difficulty) {
    }

    public abstract boolean hasBeenCompleted();


}
