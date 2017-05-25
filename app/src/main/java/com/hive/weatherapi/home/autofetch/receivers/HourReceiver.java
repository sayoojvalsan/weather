package com.hive.weatherapi.home.autofetch.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.hive.weatherapi.home.autofetch.services.AutoFetchService;

/**
 * Created by hive on 5/24/17.
 */

public class HourReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = HourReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        //Start intent service to fetch new weather data
        Log.d(TAG, "Fetching new weather data now");
        //Start the service
        context.startService(new Intent(context, AutoFetchService.class));

    }
}
