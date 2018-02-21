package com.weatherapp.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dineshkumar_a on 2/20/2018.
 */

public class ApiClient {
  //  http://api.openweathermap.org/data/2.5/weather?q=Canberra&units=metric&APPID=1cccfb501bd7f85d040ce00fd16ee1bf



    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
