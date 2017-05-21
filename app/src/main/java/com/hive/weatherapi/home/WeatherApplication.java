package com.hive.weatherapi.home;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by hive on 5/20/17.
 */

public class WeatherApplication extends Application {


    private final static String PATH_TO_WEATHER_FONT = "fonts/weather.ttf";
    private  static Context mContext;

    private Typeface mWeatherTypeface;

    public WeatherApplication() {
    }

    @Override
    public void onCreate() {

        mWeatherTypeface = Typeface.createFromAsset(getAssets(), PATH_TO_WEATHER_FONT);
        super.onCreate();
        mContext= this;

    }


    public Typeface getWeatherTypeface() {
        return mWeatherTypeface;
    }

    public static Context getContext(){
        return mContext;
    }

}
