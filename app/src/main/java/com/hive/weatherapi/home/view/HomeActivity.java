package com.hive.weatherapi.home.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.hive.weatherapi.R;
import com.hive.weatherapi.home.currentweater.view.CurrentWeatherFragment;

public class HomeActivity extends AppCompatActivity  {


    private final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Google places fragment
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        autocompleteFragment.setFilter(typeFilter);



        //CurrentWeather Fragment
        final CurrentWeatherFragment currentWeatherFragment =  (CurrentWeatherFragment)
                getSupportFragmentManager().findFragmentById(R.id.current_weather_fragment);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName().toString());
                currentWeatherFragment.searchByCity(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                Log.e(TAG, "An error occurred: " + status);
                Toast.makeText(getApplicationContext(), "An Error has occured " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        //Give the permission results to Weather Fragement. We need this to support new Permission model in Android 6.0 and above
        final CurrentWeatherFragment currentWeatherFragment =  (CurrentWeatherFragment)
                getSupportFragmentManager().findFragmentById(R.id.current_weather_fragment);

        currentWeatherFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }



}
