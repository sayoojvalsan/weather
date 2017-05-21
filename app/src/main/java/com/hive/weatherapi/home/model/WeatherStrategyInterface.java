package com.hive.weatherapi.home.model;

import com.hive.weatherapi.home.interfaces.OnCompleted;

/**
 * Created by hive on 5/20/17.
 */

public interface WeatherStrategyInterface {

     void getCurrentWeatherByCity(String cityName, OnCompleted<CurrentWeather> onCompleted);



}
