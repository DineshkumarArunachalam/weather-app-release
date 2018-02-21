package com.weatherapp.service;

import com.weatherapp.model.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dineshkumar_a on 2/20/2018.
 */

public interface ApiInterface {

    // weather api

    @GET("/data/2.5/weather")
    Call<WeatherData> getWeatherInformation(@Query("q") String apiKey, @Query("units") String entity_type, @Query("APPID") String APPID);
}
