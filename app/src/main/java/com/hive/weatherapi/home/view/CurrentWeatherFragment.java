package com.hive.weatherapi.home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hive.weatherapi.R;
import com.hive.weatherapi.home.WeatherApplication;
import com.hive.weatherapi.home.model.CurrentWeather;
import com.hive.weatherapi.home.model.CurrentWeatherService;
import com.hive.weatherapi.home.model.CurrentWeatherServiceInterface;
import com.hive.weatherapi.home.model.openweathermap.OpenWeatherMapStrategy;
import com.hive.weatherapi.home.presenter.CurrentWeatherPresenter;
import com.hive.weatherapi.home.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class CurrentWeatherFragment extends Fragment implements CurrentWeatherViewInterface{


    @BindView(R.id.text_view_city) TextView mCityName;
    @BindView(R.id.text_view_weather_text) TextView mWeatherText;
    @BindView(R.id.cloud_icon) TextView mCloudIcon;
    @BindView(R.id.current_temp) TextView mCurrentTemp;
    @BindView(R.id.empty_view) TextView mEmptyLayout;



    @BindView(R.id.tv_max_min) TextView mMinMax;


    private  CurrentWeatherPresenter mCurrentWeatherPresenter;
    public CurrentWeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Create a weather service using OpenMapWeather Client
        CurrentWeatherServiceInterface openWeatherMapStrategy = new CurrentWeatherService(new OpenWeatherMapStrategy());

        //Create the presenter
        mCurrentWeatherPresenter = new CurrentWeatherPresenter(this, openWeatherMapStrategy);

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCloudIcon.setTypeface(((WeatherApplication)WeatherApplication.getContext()).getWeatherTypeface());


    }

    @Override
    public void setCurrentWeather(CurrentWeather currentWeather) {

        hideEmptyView();
        Util.resolveWeatherIcon(mCloudIcon, currentWeather.getCurrentWeatherIcon());
        mCityName.setText(currentWeather.getCityName());
        mWeatherText.setText(currentWeather.getCurrentWeatherText());
        mCurrentTemp.setText(currentWeather.getCurrentWeatherValue());
        mMinMax.setText(currentWeather.getCurrentWeatherTextMax() + " | " + currentWeather.getCurrentWeatherTextMin());
    }

    private void hideEmptyView() {
        if(mEmptyLayout.getVisibility() == View.VISIBLE) mEmptyLayout.setVisibility(View.GONE);
    }

    @Override
    public void searchByCity(String city) {
        mCurrentWeatherPresenter.getCurrentWeather(city);

    }
}
