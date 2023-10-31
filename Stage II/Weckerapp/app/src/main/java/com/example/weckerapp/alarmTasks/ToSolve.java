package com.example.weckerapp.alarmTasks;

//This resembles a ToSolve (einzelne Aufgabe)
public abstract class ToSolve {
    int difficulty; //From 1 to 10

    public ToSolve(int difficulty) {
        this.difficulty = difficulty;
    }

    public abstract boolean validateToSolve();
}
