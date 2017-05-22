package com.hive.weatherapi.home.currentweater.view;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hive.weatherapi.R;
import com.hive.weatherapi.home.WeatherApplication;
import com.hive.weatherapi.home.currentweater.model.CurrentWeather;
import com.hive.weatherapi.home.currentweater.model.CurrentWeatherService;
import com.hive.weatherapi.home.currentweater.model.CurrentWeatherServiceInterface;
import com.hive.weatherapi.home.currentweater.model.openweathermap.OpenWeatherMapStrategy;
import com.hive.weatherapi.home.currentweater.presenter.CurrentWeatherPresenter;
import com.hive.weatherapi.home.helper.LocationHelper;
import com.hive.weatherapi.home.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Using MVP Pattern.  {@link CurrentWeatherPresenter} handles all presentation logic
 */
public class CurrentWeatherFragment extends Fragment implements CurrentWeatherViewInterface, LocationHelper.LocationListener {


    @BindView(R.id.text_view_city) TextView mCityName;
    @BindView(R.id.text_view_weather_text) TextView mWeatherText;
    @BindView(R.id.cloud_icon) TextView mCloudIcon;
    @BindView(R.id.current_temp) TextView mCurrentTemp;
    @BindView(R.id.empty_view) TextView mEmptyLayout;



    @BindView(R.id.tv_max_min) TextView mMinMax;


    private  CurrentWeatherPresenter mCurrentWeatherPresenter;
    private  LocationHelper mLocationHelper;

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

        mLocationHelper = new LocationHelper(getActivity());

        mCloudIcon.setTypeface(((WeatherApplication)WeatherApplication.getContext()).getWeatherTypeface());

        final String lastKnownCity = mLocationHelper.getLastKnownCity(getContext());

         if(lastKnownCity != null){
            searchByCity(lastKnownCity);
        }
        else
        {
            //We dont have last known city. Auto locate at this point
            //Start Location helper to resolve permissions / get Current location etc
            mLocationHelper.setLocationListener(this);
            mLocationHelper.fetchCurrentLocation(getActivity());
        }

    }

    @Override
    public void setCurrentWeather(CurrentWeather currentWeather) {
        //Since we have the last searched city, save it
        mLocationHelper.saveLastKnownCity(currentWeather.getCityName(), getContext());

        hideEmptyView();

        Util.resolveWeatherIcon(mCloudIcon, currentWeather.getCurrentWeatherIcon());
        //update view
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


    @Override
    public void onLocationFound(Location location, Throwable throwable) {
        if(location == null) return;

        mCurrentWeatherPresenter.getCurrentWeather(location.getLatitude(), location.getLongitude());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        if(mLocationHelper != null) {
            mLocationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    @Override
    public void onDestroy() {
        if(mLocationHelper != null) {
            mLocationHelper.onDestroy();
        }
        super.onDestroy();
    }
}
