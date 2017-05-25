package com.hive.weatherapi.home.currentweather.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.hive.weatherapi.home.interfaces.OnCompleted;
import com.hive.weatherapi.home.currentweather.model.CurrentWeather;
import com.hive.weatherapi.home.currentweather.model.CurrentWeatherServiceInterface;
import com.hive.weatherapi.home.currentweather.view.CurrentWeatherViewInterface;
import com.hive.weatherapi.home.utils.Constants;
import com.hive.weatherapi.home.utils.Util;

import java.lang.ref.WeakReference;

/**
 * Created by hive on 5/20/17.
 */


public class CurrentWeatherPresenter implements  CurrentWeatherPresenterInterface, OnCompleted<CurrentWeather> {

    private String TAG = CurrentWeatherPresenter.class.getSimpleName();
    private  WeakReference<CurrentWeatherViewInterface> mCurrentWeatherViewInterface; //Using weak reference to avoid Mem leaks. View can be detached anytime.
    private  CurrentWeatherServiceInterface mCurrentWeatherServiceInterface;


    public CurrentWeatherPresenter(CurrentWeatherViewInterface currentWeatherViewInterface, CurrentWeatherServiceInterface currentWeatherServiceInterface) {

        mCurrentWeatherViewInterface = new WeakReference<>(currentWeatherViewInterface);

        mCurrentWeatherServiceInterface = currentWeatherServiceInterface;
    }

    @Override
    public void getCurrentWeather(final String city) {
            getWeatherByCity(city);

    }



    

    @Override
    public void getCurrentWeather(final double latitude, final  double longitude) {
        mCurrentWeatherServiceInterface.getCurrentWeatherByLatLng(latitude, longitude, this);
    }

    private void getWeatherByCity(final String city) {
        mCurrentWeatherServiceInterface.getCurrentWeatherByCity(city, this);
    }


    @Override
    public void onComplete(CurrentWeather currentWeather, Throwable throwable) {
        //Check weak reference for validity
        CurrentWeatherViewInterface currentWeatherViewInterface = mCurrentWeatherViewInterface.get();
        if(currentWeatherViewInterface != null) {
            currentWeatherViewInterface.setCurrentWeather(currentWeather);
        }
    }
}
