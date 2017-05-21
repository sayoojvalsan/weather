package com.hive.weatherapi.home.utils;

import android.widget.TextView;

import com.hive.weatherapi.R;

/**
 * Created by hive on 5/20/17.
 */

public class Util {

    public static void resolveWeatherIcon(TextView weatherTextView, String weatherIcon){

        switch (weatherIcon) {
            case "01d":
                weatherTextView.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                weatherTextView.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                weatherTextView.setText(R.string.wi_cloud_down);
                break;
            case "04d":
                weatherTextView.setText(R.string.wi_cloudy);
                break;
            case "04n":
                weatherTextView.setText(R.string.wi_night_cloudy);
                break;
            case "10d":
                weatherTextView.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                weatherTextView.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                weatherTextView.setText(R.string.wi_day_snow);
                break;
            case "01n":
                weatherTextView.setText(R.string.wi_night_clear);
                break;
            case "02n":
                weatherTextView.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                weatherTextView.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                weatherTextView.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                weatherTextView.setText(R.string.wi_night_rain);
                break;
            case "13n":
                weatherTextView.setText(R.string.wi_night_snow);
        }

    }
}
