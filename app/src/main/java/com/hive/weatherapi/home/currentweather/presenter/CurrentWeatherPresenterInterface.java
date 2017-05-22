package com.hive.weatherapi.home.currentweather.presenter;

/**
 * Created by hive on 5/20/17.
 */

public interface CurrentWeatherPresenterInterface {

     void getCurrentWeather(final String input);

     void getCurrentWeather(double latitude, double longitude);
}
