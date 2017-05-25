package com.hive.weatherapi.home.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.hive.weatherapi.R;
import com.hive.weatherapi.home.currentweather.model.CurrentWeather;

/**
 * Created by hive on 5/20/17.
 */

public class Util {

    private static final String TAG = Util.class.getSimpleName();

    public static void resolveWeatherIcon(TextView weatherTextView, String weatherIcon){

        switch (weatherIcon) {
            case "01d":
                weatherTextView.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                weatherTextView.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                weatherTextView.setText(R.string.wi_cloud_down);
                break;
            case "04d":
                weatherTextView.setText(R.string.wi_cloudy);
                break;
            case "04n":
                weatherTextView.setText(R.string.wi_night_cloudy);
                break;
            case "10d":
                weatherTextView.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                weatherTextView.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                weatherTextView.setText(R.string.wi_day_snow);
                break;
            case "01n":
                weatherTextView.setText(R.string.wi_night_clear);
                break;
            case "02n":
                weatherTextView.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                weatherTextView.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                weatherTextView.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                weatherTextView.setText(R.string.wi_night_rain);
                break;
            case "13n":
                weatherTextView.setText(R.string.wi_night_snow);
        }

    }

    public static  boolean doWeHavePermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isGooglePlayServicesAvailable(Context context){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }


    public  static SharedPreferences getSharedPref(Context context){
        return context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }


    public static  void saveWeatherData(CurrentWeather currentWeather, Context context) {


        SharedPreferences pref = Util.getSharedPref(context);
        Gson gson = new Gson();
        String json = gson.toJson(currentWeather);
        pref.edit().putString(Constants.PREF_CURRENT_WEATHER_KEY, json).commit();

        Log.d(TAG, "Current weather saved successfully " + currentWeather.toString());

    }

    public static  CurrentWeather getCurrentWeatherData( Context context) {


        SharedPreferences pref = Util.getSharedPref(context);
        Gson gson = new Gson();
        String json = pref.getString(Constants.PREF_CURRENT_WEATHER_KEY, null);
        if (json == null) return null;
        return gson.fromJson(json, CurrentWeather.class);

    }

}
