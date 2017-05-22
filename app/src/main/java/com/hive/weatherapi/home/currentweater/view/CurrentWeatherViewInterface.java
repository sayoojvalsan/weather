package com.hive.weatherapi.home.currentweater.view;

import com.hive.weatherapi.home.currentweater.model.CurrentWeather;

/**
 * Created by hive on 5/20/17.
 */

public interface CurrentWeatherViewInterface {

    void setCurrentWeather(CurrentWeather currentWeather);
    void searchByCity(String city);

}
