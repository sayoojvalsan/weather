package com.hive.weatherapi.home.currentweather.model;

/**
 * Created by hive on 5/20/17.
 */

public class CurrentWeather {

    private String cityName;
    private String currentWeatherValue;
    private String currentWeatherUnit;
    private String currentWeatherText;
    private String currentWeatherTextMax;

    private String currentWeatherIcon;

    public String getCurrentWeatherIcon() {
        return currentWeatherIcon;
    }

    public void setCurrentWeatherIcon(String currentWeatherIcon) {
        this.currentWeatherIcon = currentWeatherIcon;
    }

    public String getCurrentWeatherTextMax() {
        return currentWeatherTextMax;
    }

    public void setCurrentWeatherTextMax(String currentWeatherTextMax) {
        this.currentWeatherTextMax = currentWeatherTextMax;
    }

    public String getCurrentWeatherTextMin() {
        return currentWeatherTextMin;
    }

    public void setCurrentWeatherTextMin(String currentWeatherTextMin) {
        this.currentWeatherTextMin = currentWeatherTextMin;
    }

    private String currentWeatherTextMin;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCurrentWeatherValue() {
        return currentWeatherValue;
    }

    public void setCurrentWeatherValue(String currentWeatherValue) {
        this.currentWeatherValue = currentWeatherValue;
    }

    public String getCurrentWeatherUnit() {
        return currentWeatherUnit;
    }

    public void setCurrentWeatherUnit(String currentWeatherUnit) {
        this.currentWeatherUnit = currentWeatherUnit;
    }

    public String getCurrentWeatherText() {
        return currentWeatherText;
    }

    public void setCurrentWeatherText(String currentWeatherText) {
        this.currentWeatherText = currentWeatherText;
    }
}
