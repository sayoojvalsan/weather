package com.hive.weatherapi.home.currentweather.view;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hive.weatherapi.R;
import com.hive.weatherapi.home.WeatherApplication;
import com.hive.weatherapi.home.currentweather.model.CurrentWeather;
import com.hive.weatherapi.home.currentweather.model.CurrentWeatherService;
import com.hive.weatherapi.home.currentweather.model.CurrentWeatherServiceInterface;
import com.hive.weatherapi.home.currentweather.model.openweathermap.OpenWeatherMapStrategy;
import com.hive.weatherapi.home.currentweather.presenter.CurrentWeatherPresenter;
import com.hive.weatherapi.home.helper.LocationHelper;
import com.hive.weatherapi.home.utils.Constants;
import com.hive.weatherapi.home.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Using MVP Pattern.  {@link CurrentWeatherPresenter} handles all presentation logic
 */
public class CurrentWeatherFragment extends Fragment implements CurrentWeatherViewInterface, LocationHelper.LocationListener {


    private static final String TAG = CurrentWeatherFragment.class.getSimpleName();
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

        //Check weather data in cache first
         checkForWeatherDatainCache();

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

    private void checkForWeatherDatainCache() {


        CurrentWeather currentWeather = Util.getCurrentWeatherData(getContext());
        if(currentWeather == null) return;
        Log.d(TAG, "Got cached weather " + currentWeather.toString());

        //update the UI with cached data
        setCurrentWeather(currentWeather);
    }


    @Override
    public void setCurrentWeather(CurrentWeather currentWeather) {
        //Since we have the last searched city, save it
        mLocationHelper.saveLastKnownCity(currentWeather.getCityName(), getContext());
        Util.saveWeatherData(currentWeather, getContext());

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
    public void onError(Throwable throwable) {
        Toast.makeText(getContext(), " Error " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLocationFound(Location location, Throwable throwable) {
        if(location == null) return;

        mCurrentWeatherPresenter.getCurrentWeather(location.getLatitude(), location.getLongitude());

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
