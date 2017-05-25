package com.hive.weatherapi.home.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.hive.weatherapi.home.autofetch.receivers.HourReceiver;


/**
 * Created by hive on 5/24/17.
 */

public class WeatherScheduler {


    private static final String TAG = WeatherScheduler.class.getSimpleName();

    public void setRepeatingAlarmEveryHour(Context context){


        Intent intent = new Intent(context, HourReceiver.class);//the same as up
        boolean isWorking = (PendingIntent.getBroadcast(context, 108, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (isWorking) {
            Log.d(TAG, "Alarm already  working");
            return;
        } else {
            Log.d(TAG, "Alarm is not set yet.. setting new Alarm..");
        }


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(context, HourReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 108, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 1 * 60 * 1000,
                1 * 60 * 1000,
                pendingIntent);


    }
}
