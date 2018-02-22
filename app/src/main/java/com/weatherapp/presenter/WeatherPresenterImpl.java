package com.weatherapp.presenter;

import com.weatherapp.constants.DataConstants;
import com.weatherapp.model.WeatherData;
import com.weatherapp.service.ApiClient;
import com.weatherapp.service.ApiInterface;
import com.weatherapp.view.WeatherView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dineshkumar_a on 2/20/2018.
 */

public class WeatherPresenterImpl implements WeatherPresenter {

    WeatherView view;

    public WeatherPresenterImpl(WeatherView weatherView) {
        this.view=weatherView;
    }

    @Override
    public void makeWeatherServiceCall() {
        view.showProgress();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<WeatherData> call = apiService.getWeatherInformation(DataConstants.CITY_NAME, "metric", DataConstants.APP_ID);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse( Call<WeatherData> call, Response<WeatherData> response) {
                view.hideProgress();
                view.updateTodayWeather(response.body());
            }

            @Override
            public void onFailure(Call<WeatherData> call,  Throwable t) {
                view.hideProgress();
                view.setErrorMessage(t.getMessage());
            }
        });

    }

    @Override
    public void navigationToDetail() {
        view.navigateToDetail();
    }
}
