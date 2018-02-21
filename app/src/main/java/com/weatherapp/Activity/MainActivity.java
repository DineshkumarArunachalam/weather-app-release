package com.weatherapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weatherapp.R;
import com.weatherapp.constants.DataConstants;
import com.weatherapp.model.WeatherData;
import com.weatherapp.presenter.WeatherPresenter;
import com.weatherapp.presenter.WeatherPresenterImpl;
import com.weatherapp.view.WeatherView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements WeatherView {

    @BindView(R.id.today_weather)
    TextView todayWeather;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.detail_screen)
    Button detailClick;
    private WeatherPresenter weatherPresenter;
    private WeatherData weatherData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        weatherPresenter = new WeatherPresenterImpl(this);
        weatherPresenter.makeWeatherServiceCall();

    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateTodayWeather(WeatherData info) {
        weatherData =info;
        todayWeather.setVisibility(View.VISIBLE);
        todayWeather.setText(info.getWeather().get(0).getMain());
        detailClick.setVisibility(View.VISIBLE);
    }


    @Override
    public void navigateToDetail() {

        Intent detailIntent = new Intent(this, WeatherDetailPage.class);
        Bundle b = new Bundle();
        b.putInt(DataConstants.HUMIDITY,weatherData.getMain().getHumidity());
        b.putDouble(DataConstants.WIND,weatherData.getWind().getSpeed());
        b.putInt(DataConstants.PRESSURE,weatherData.getMain().getPressure());
        detailIntent.putExtras(b);
        startActivity(detailIntent);
    }

    @Override
    public void setErrorMessage(String errorMessage) {

    }

    @OnClick(R.id.detail_screen)
    void navigation() {
        weatherPresenter.navigationToDetail();
    }
}

