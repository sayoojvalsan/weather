package com.hive.weatherapi.home.interfaces;

/**
 * Created by hive on 5/20/17.
 */

public interface OnCompleted<T> {

     void onComplete(T type, Throwable throwable);
}
