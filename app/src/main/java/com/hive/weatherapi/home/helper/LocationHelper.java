package com.hive.weatherapi.home.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.hive.weatherapi.home.utils.Constants;
import com.hive.weatherapi.home.utils.Util;

/**
 * Created by hive on 5/22/17.
 *
 * Responsible for resolving Permission issues, Autolocate etc.
 * The Autolocate will run for few seconds.
 */

public class LocationHelper implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {
    private final String TAG = LocationHelper.class.getSimpleName();

    private final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    private boolean mLocationPermissionGranted;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Handler mHandler;
    private TimerRunnable mRunnable;
    private Context mContext;
    private boolean mRequestingLocationUpdates;
    private LocationListener mLocationListener;




    public LocationHelper(Activity activty) {

        mContext = activty.getApplicationContext();
        mGoogleApiClient = new GoogleApiClient.Builder(activty)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }
    public void fetchCurrentLocation(Activity activty){
        mGoogleApiClient.connect();
        checkPermissions(activty);
        startLocationUpdates();
    }

    private void checkPermissions(Activity activty) {

        if (!isGooglePlayServicesAvailable(activty)) {
            return;
        }
        if (ContextCompat.checkSelfPermission(activty,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activty,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d(TAG, "shouldShowRequestPermissionRationale");

                ActivityCompat.requestPermissions(activty,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            } else {

                // No explanation needed, we can request the permission.
                Log.d(TAG, "No explanation needed");

                ActivityCompat.requestPermissions(activty,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // ACCESS_COARSE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }
    }

    private void startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates");

        if (mRequestingLocationUpdates) {
            Log.d(TAG, "Location services are already enabled. Skipping");
            return;
        }
        if (!Util.doWeHavePermission(mContext)){
            Log.w(TAG, "Permissions are missing. Skipping");
            return;
        }

        if(!mGoogleApiClient.isConnected()) {
            Log.w(TAG, "Google client is not connected. Skipping");

            return;
        }

        if (!Util.isGooglePlayServicesAvailable(mContext)) return;

        //We are checking for permission on Top
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location services enabled");
        mRequestingLocationUpdates = true;


    }


    /**
     * Check if Google play services is availble. If not, do the resolution if any
     * @param activity
     * @return boolean
     */
    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }



    private void stopLocationUpdates() {
        Log.d(TAG, "stopLocationUpdates");
        mRequestingLocationUpdates = false;
        if(!mGoogleApiClient.isConnected()) return;
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }





    public void setLocationListener(LocationListener locationListener) {
        mLocationListener = locationListener;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");


        mRunnable = new TimerRunnable();

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 30 * 1000);

        //Google apis are connected
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        mLocationRequest.setInterval(1000); // Update location every second
        startLocationUpdates();
        if (!mLocationPermissionGranted) return;

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if(lastLocation != null){
            if(mLocationListener != null)
                mLocationListener.onLocationFound(lastLocation, null);
        }

    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionFailed ");

    }




    @Override
    public void onLocationChanged(Location location) {

        Log.d(TAG, "onLocationChanged");

        mHandler.removeCallbacks(mRunnable); //Remove our Timer
        stopLocationUpdates();
        //Do something with new location
        if(mLocationListener != null)
            mLocationListener.onLocationFound(location, null);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed " + connectionResult.getErrorMessage());
        if(mLocationListener != null)
            mLocationListener.onLocationFound(null, new Throwable(connectionResult.getErrorMessage()));

    }


    public void onDestroy() {
        mLocationListener = null;
        stopLocationUpdates();
        if(mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        if(mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

    }


    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    startLocationUpdates();
                }
            }
        }

    }


    public static void saveLastKnownCity(String city, Context context){

        if(context == null) return;

        Util.getSharedPref(context).edit().putString(Constants.PREF_LOCATION_KEY, city).commit();

    }


    public static String getLastKnownCity( Context context){

        if(context == null) return null;

        return Util.getSharedPref(context).getString(Constants.PREF_LOCATION_KEY, null);

    }


    private class TimerRunnable implements Runnable{

        @Override
        public void run() {
            try{
                Log.d(TAG, "Waited too long. Stopping location updates");
                stopLocationUpdates();
            }
            catch (Exception e){
                Log.w(TAG, "Timer Interrupted");
            }
        }
    }

    public interface LocationListener{

        void onLocationFound(Location location, Throwable throwable);
    }

}
