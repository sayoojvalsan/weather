package com.hive.weatherapi.home.currentweater.model;

import com.hive.weatherapi.home.interfaces.OnCompleted;

/**
 * Created by hive on 5/20/17.
 */

public interface CurrentWeatherServiceInterface {

    void getCurrentWeatherByCity(String city, OnCompleted<CurrentWeather> currentWeather);
    void getCurrentWeatherByLatLng(double lat, double lng, OnCompleted<CurrentWeather> currentWeather);

}
