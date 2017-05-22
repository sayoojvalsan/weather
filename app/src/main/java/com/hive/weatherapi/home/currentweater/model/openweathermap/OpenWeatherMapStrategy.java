package com.hive.weatherapi.home.currentweater.model.openweathermap;

import android.util.Log;

import com.hive.weatherapi.home.interfaces.OnCompleted;
import com.hive.weatherapi.home.currentweater.model.CurrentWeather;
import com.hive.weatherapi.home.currentweater.model.WeatherStrategyInterface;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hive on 5/20/17.
 */

public class OpenWeatherMapStrategy implements WeatherStrategyInterface {
    final String TAG = OpenWeatherMapStrategy.class.getSimpleName();
    final String APP_ID = "377159a37b4fb15dbcd43ffdd594c6fc";
    final String UNIT = "imperial";

    final OpenWeatherMapRetroFitService mOpenWeatherMapRetroFitService ;

    public OpenWeatherMapStrategy() {


        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .build();


        mOpenWeatherMapRetroFitService = retrofit.create(OpenWeatherMapRetroFitService.class);

    }


    @Override
    public void getCurrentWeatherByCity(String cityName, final OnCompleted<CurrentWeather> onCompleted) {


        Observable<OpenWeatherMapData> weatherMapDataObservable = mOpenWeatherMapRetroFitService.getCurrentWeatherData(cityName, APP_ID, UNIT);

        weatherMapDataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OpenWeatherMapData>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError " + e.getLocalizedMessage());

                        onCompleted.onComplete(null, e);

                    }

                    @Override
                    public void onNext(OpenWeatherMapData openWeatherMapData) {
                        Log.d("Current Weather", openWeatherMapData.getWeather()
                                .get(0)
                                .getDescription());


                        CurrentWeather weather = convertToViewData(openWeatherMapData);
                        onCompleted.onComplete(weather, null);

                    }
                });

    }

    @Override
    public void getCurrentWeatherByLatLong(double lat, double lon, final OnCompleted<CurrentWeather> onCompleted) {
        Observable<OpenWeatherMapData> weatherMapDataObservable = mOpenWeatherMapRetroFitService.getCurrentWeatherData(String.valueOf(lat), String.valueOf(lon), APP_ID, UNIT);

        weatherMapDataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OpenWeatherMapData>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError " + e.getLocalizedMessage());

                        onCompleted.onComplete(null, e);

                    }

                    @Override
                    public void onNext(OpenWeatherMapData openWeatherMapData) {
                        Log.d("Current Weather", openWeatherMapData.getWeather()
                                .get(0)
                                .getDescription());


                        CurrentWeather weather = convertToViewData(openWeatherMapData);
                        onCompleted.onComplete(weather, null);

                    }
                });
    }


    public CurrentWeather convertToViewData(OpenWeatherMapData openWeatherMapData){


        Weather weather = openWeatherMapData.getWeather().get(0);

        CurrentWeather currentWeather  = new CurrentWeather();
        currentWeather.setCityName(openWeatherMapData.getName());
        currentWeather.setCurrentWeatherText(weather.getDescription());
        currentWeather.setCurrentWeatherUnit("F");
        currentWeather.setCurrentWeatherValue(openWeatherMapData.getMain().getTemp().toString());
        currentWeather.setCurrentWeatherIcon(weather.getIcon());
        currentWeather.setCurrentWeatherTextMax(openWeatherMapData.getMain().getTempMax().toString());
        currentWeather.setCurrentWeatherTextMin(openWeatherMapData.getMain().getTempMin().toString());

        return  currentWeather;

    }



    public interface OpenWeatherMapRetroFitService {
        @GET("weather")
        Observable<OpenWeatherMapData> getCurrentWeatherData(@Query("q") String sort, @Query("appid") String appid, @Query("units") String unit);
        @GET("weather")
        Observable<OpenWeatherMapData> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid, @Query("units") String unit);

    }


}
