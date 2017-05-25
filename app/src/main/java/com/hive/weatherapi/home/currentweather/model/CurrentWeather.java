package com.hive.weatherapi.home.currentweather.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hive on 5/20/17.
 */

public class CurrentWeather implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cityName);
        dest.writeString(this.currentWeatherValue);
        dest.writeString(this.currentWeatherUnit);
        dest.writeString(this.currentWeatherText);
        dest.writeString(this.currentWeatherTextMax);
        dest.writeString(this.currentWeatherIcon);
        dest.writeString(this.currentWeatherTextMin);
    }

    public CurrentWeather() {
    }

    protected CurrentWeather(Parcel in) {
        this.cityName = in.readString();
        this.currentWeatherValue = in.readString();
        this.currentWeatherUnit = in.readString();
        this.currentWeatherText = in.readString();
        this.currentWeatherTextMax = in.readString();
        this.currentWeatherIcon = in.readString();
        this.currentWeatherTextMin = in.readString();
    }

    public static final Parcelable.Creator<CurrentWeather> CREATOR = new Parcelable.Creator<CurrentWeather>() {
        @Override
        public CurrentWeather createFromParcel(Parcel source) {
            return new CurrentWeather(source);
        }

        @Override
        public CurrentWeather[] newArray(int size) {
            return new CurrentWeather[size];
        }
    };

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "cityName='" + cityName + '\'' +
                ", currentWeatherValue='" + currentWeatherValue + '\'' +
                ", currentWeatherUnit='" + currentWeatherUnit + '\'' +
                ", currentWeatherText='" + currentWeatherText + '\'' +
                ", currentWeatherTextMax='" + currentWeatherTextMax + '\'' +
                ", currentWeatherIcon='" + currentWeatherIcon + '\'' +
                ", currentWeatherTextMin='" + currentWeatherTextMin + '\'' +
                '}';
    }
}
