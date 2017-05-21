package com.hive.weatherapi.home.presenter;

import android.util.Log;

import com.hive.weatherapi.home.interfaces.OnCompleted;
import com.hive.weatherapi.home.model.CurrentWeather;
import com.hive.weatherapi.home.model.CurrentWeatherServiceInterface;
import com.hive.weatherapi.home.view.CurrentWeatherViewInterface;

import java.lang.ref.WeakReference;

/**
 * Created by hive on 5/20/17.
 */

public class CurrentWeatherPresenter implements  CurrentWeatherPresenterInterface{

    private String TAG = CurrentWeatherPresenter.class.getSimpleName();
    private  WeakReference<CurrentWeatherViewInterface> mCurrentWeatherViewInterface; //Using weak reference to avoid Mem leaks. View can be detached anytime.
    private  CurrentWeatherServiceInterface mCurrentWeatherServiceInterface;


    public CurrentWeatherPresenter(CurrentWeatherViewInterface currentWeatherViewInterface, CurrentWeatherServiceInterface currentWeatherServiceInterface) {

        mCurrentWeatherViewInterface = new WeakReference<>(currentWeatherViewInterface);

        mCurrentWeatherServiceInterface = currentWeatherServiceInterface;
    }

    @Override
    public void getCurrentWeather(String input) {
            Log.d(TAG, "Presenter fetching current weather for " + input);
            getWeatherByCity(input);

    }

    private void getWeatherByCity(String input) {
        mCurrentWeatherServiceInterface.getCurrentWeatherByCity(input, new OnCompleted<CurrentWeather>() {
            @Override
            public void onComplete(CurrentWeather currentWeather, Throwable throwable) {

                //Check weak reference for validity
                CurrentWeatherViewInterface currentWeatherViewInterface = mCurrentWeatherViewInterface.get();
                if(currentWeatherViewInterface != null) {
                    currentWeatherViewInterface.setCurrentWeather(currentWeather);
                }
            }
        });
    }


}
