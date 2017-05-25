package com.hive.weatherapi.home.autofetch.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.hive.weatherapi.home.helper.WeatherScheduler;

/**
 * Created by sayoojvalsan on 2/22/16.
 */
public class BootReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "BootReceiver");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            final WeatherScheduler weatherScheduler = new WeatherScheduler();
            weatherScheduler.setRepeatingAlarmEveryHour(context);
            //Remove the wake lock
            BootReceiver.completeWakefulIntent(intent);
        }

    }
}
