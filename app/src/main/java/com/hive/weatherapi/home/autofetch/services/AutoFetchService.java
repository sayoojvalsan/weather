package com.hive.weatherapi.home.autofetch.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.hive.weatherapi.R;
import com.hive.weatherapi.home.autofetch.receivers.HourReceiver;
import com.hive.weatherapi.home.currentweather.model.CurrentWeather;
import com.hive.weatherapi.home.currentweather.model.CurrentWeatherService;
import com.hive.weatherapi.home.currentweather.model.openweathermap.OpenWeatherMapStrategy;
import com.hive.weatherapi.home.helper.LocationHelper;
import com.hive.weatherapi.home.interfaces.OnCompleted;
import com.hive.weatherapi.home.utils.Constants;
import com.hive.weatherapi.home.utils.Util;
import com.hive.weatherapi.home.view.HomeActivity;

/**
 * Responsible for fetching weather data in the background. This is used in conjection with Android scheduler which runs every hour
 */
public class AutoFetchService extends Service {
    private static final String TAG = AutoFetchService.class.getSimpleName();

    public AutoFetchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startFetching(intent);
        return Service.START_NOT_STICKY;
    }

    private void startFetching(final Intent intent) {

        final CurrentWeatherService currentWeatherService = new CurrentWeatherService(new OpenWeatherMapStrategy());

        //get the last city
        String city = LocationHelper.getLastKnownCity(getApplicationContext());
        if(city == null) {
            Log.d(TAG, "No last city known.");
        }
        currentWeatherService.getCurrentWeatherByCity(city, new OnCompleted<CurrentWeather>() {
            @Override
            public void onComplete(CurrentWeather currentWeather, Throwable throwable) {

                if(currentWeather == null){
                    Log.e(TAG, "Could not fetch New weather data ");

                }
                else {

                    //Save the currentWeather and Create Notification
                    Log.d(TAG, "New weather data " + currentWeather);
                    Util.saveWeatherData(currentWeather, getApplicationContext());
                    createNotification();
                }
                HourReceiver.completeWakefulIntent(intent);
                stopSelf();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed..");

    }



    private void createNotification() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_cloud_circle_black_24dp)
                        .setContentTitle("Weather Update")
                        .setContentText("New Weather report Available");
        Intent resultIntent = new Intent(this, HomeActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        mBuilder.setAutoCancel(true);


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());


    }
}
