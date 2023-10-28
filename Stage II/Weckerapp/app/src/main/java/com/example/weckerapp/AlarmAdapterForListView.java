package com.example.weckerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmAdapterForListView extends ArrayAdapter<Alarm> {

    private Context context;
    private int resource;

    public AlarmAdapterForListView(@NonNull Context context, int resource, @NonNull ArrayList<Alarm> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        ImageView iv_alarm = convertView.findViewById(R.id.alarm_image);
        TextView tv_alarm = convertView.findViewById(R.id.tv_alarm);
        Switch sw_alarmON = convertView.findViewById(R.id.sw_alarmON);

        iv_alarm.setImageResource(getItem(position).getSymbol());
        getItem(position).refreshDisplayedTitle();
        tv_alarm.setText(getItem(position).getDisplayed_title());
        sw_alarmON.setChecked(getItem(position).active);

        sw_alarmON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = MainActivity.ctx.getSharedPreferences("Alarms", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                AlarmsActivity.al_alarms.get(position).setActive(!AlarmsActivity.al_alarms.get(position).active);
                editor.putBoolean("active" + position, AlarmsActivity.al_alarms.get(position).active);
                editor.commit();
                editor.apply();
            }
        });
        return convertView;
    }
}
