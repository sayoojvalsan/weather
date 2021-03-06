package com.hive.weatherapi.home.currentweather.presenter;

import com.hive.weatherapi.home.currentweather.model.CurrentWeather;
import com.hive.weatherapi.home.currentweather.model.CurrentWeatherServiceInterface;
import com.hive.weatherapi.home.currentweather.view.CurrentWeatherViewInterface;
import com.hive.weatherapi.home.interfaces.OnCompleted;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by hive on 5/22/17.g
 *
 * Testing Presenter {@link CurrentWeatherPresenter}
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrentWeatherPresenterTest {

    @Mock
    private CurrentWeatherViewInterface mCurrentWeatherViewInterface;

    @Mock
    private CurrentWeatherServiceInterface mCurrentWeatherServiceInterface;

    private CurrentWeatherPresenter mCurrentWeatherPresenter;
    
    @Mock
    OnCompleted mCallBack;



    @Before
    public void setUp() throws Exception {


        mCurrentWeatherPresenter = new CurrentWeatherPresenter(mCurrentWeatherViewInterface, mCurrentWeatherServiceInterface);
    }

    @Test
    public void getWeatherByCity() throws Exception {
        final CurrentWeather currentWeather  = new CurrentWeather();

        String city = "Los Angeles";
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                ((OnCompleted)invocation.getArguments()[1]).onComplete(currentWeather, null);
                return null;
            }
        }).when(mCurrentWeatherServiceInterface).getCurrentWeatherByCity(city, mCurrentWeatherPresenter);

        mCurrentWeatherPresenter.getCurrentWeather(city);

        //Verify view is called
        verify(mCurrentWeatherViewInterface, times(1)).setCurrentWeather(currentWeather);


    }


    @Test
    public void getCurrentWeather() throws Exception {
        final CurrentWeather currentWeather  = new CurrentWeather();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                ((OnCompleted)invocation.getArguments()[2]).onComplete(currentWeather, null);
                return null;
            }
        }).when(mCurrentWeatherServiceInterface).getCurrentWeatherByLatLng(12d, 12d, mCurrentWeatherPresenter);

        mCurrentWeatherPresenter.getCurrentWeather(12d, 12d);

        //Verify view is called
        verify(mCurrentWeatherViewInterface, times(1)).setCurrentWeather(currentWeather);


    }

}