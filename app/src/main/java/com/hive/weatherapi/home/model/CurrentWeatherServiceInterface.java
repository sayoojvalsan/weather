package com.hive.weatherapi.home.model;

import com.hive.weatherapi.home.interfaces.OnCompleted;

/**
 * Created by hive on 5/20/17.
 */

public interface CurrentWeatherServiceInterface {

    void getCurrentWeatherByCity(String city, OnCompleted<CurrentWeather> currentWeather);

}
