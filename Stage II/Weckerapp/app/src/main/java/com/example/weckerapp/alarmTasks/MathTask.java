package com.example.weckerapp.alarmTasks;

import java.util.ArrayList;

public class MathTask extends AlarmTask {

    public MathTask() {
    }

    public MathTask(int numberOfToSolves, int difficulty) {
        super();
        al_toSolves = new ArrayList<>();
        for (int i = 0; i < numberOfToSolves; i++) {
             al_toSolves.add(new MathToSolve(difficulty));
        }
    }

    @Override
    public boolean hasBeenCompleted() {
        return false;
    }
}
