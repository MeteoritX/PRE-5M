package com.example.weckerapp;

import java.util.ArrayList;

//This resembles a Task (Aufgabenstellung)
public abstract class AlarmTask {
    ArrayList<ToSolve> al_toSolves;
    public abstract boolean hasBeenCompleted();
}
