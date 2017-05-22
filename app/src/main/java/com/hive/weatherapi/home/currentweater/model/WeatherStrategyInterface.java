package com.hive.weatherapi.home.currentweater.model;

import com.hive.weatherapi.home.interfaces.OnCompleted;

/**
 * Created by hive on 5/20/17.
 */

public interface WeatherStrategyInterface {

     void getCurrentWeatherByCity(String cityName, OnCompleted<CurrentWeather> onCompleted);
     void getCurrentWeatherByLatLong(double lat, double lon, OnCompleted<CurrentWeather> onCompleted);



}
