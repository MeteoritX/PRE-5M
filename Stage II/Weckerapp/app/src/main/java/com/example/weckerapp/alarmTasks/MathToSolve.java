package com.example.weckerapp.alarmTasks;

public class MathToSolve extends ToSolve{


    public MathToSolve(int difficulty) {
        super(difficulty);
    }

    @Override
    public boolean validateToSolve() {
        return false;
    }
}
