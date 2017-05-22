package com.hive.weatherapi.home.currentweater.model;

import com.hive.weatherapi.home.interfaces.OnCompleted;

/**
 * Created by hive on 5/20/17.
 */

public class CurrentWeatherService implements CurrentWeatherServiceInterface{


    //Generic WeatherInterface - Can be any client
    private WeatherStrategyInterface mWeatherStrategyInterface;

    //Dependency Injection
    public CurrentWeatherService(WeatherStrategyInterface weatherStrategyInterface ) {

        mWeatherStrategyInterface = weatherStrategyInterface;
    }


    @Override
    public void getCurrentWeatherByCity(String city, OnCompleted<CurrentWeather> currentWeatherOnCompleteListener) {

        mWeatherStrategyInterface.getCurrentWeatherByCity(city, currentWeatherOnCompleteListener);
    }

    @Override
    public void getCurrentWeatherByLatLng(double lat, double lng, OnCompleted<CurrentWeather> currentWeatherOnCompleteListener) {
        mWeatherStrategyInterface.getCurrentWeatherByLatLong(lat, lng, currentWeatherOnCompleteListener);
    }
}
