package com.weatherapp.view;

import com.weatherapp.model.WeatherData;

/**
 * Created by dineshkumar_a on 2/20/2018.
 */

public interface WeatherView {
    void showProgress();
    void hideProgress();
    void updateTodayWeather(WeatherData info);
    void navigateToDetail();
    void setErrorMessage(String errorMessage);

}
