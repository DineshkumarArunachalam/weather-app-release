package com.weatherapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.weatherapp.R;
import com.weatherapp.constants.DataConstants;
import com.weatherapp.constants.LocationHelper;
import com.weatherapp.model.WeatherData;
import com.weatherapp.presenter.WeatherPresenter;
import com.weatherapp.presenter.WeatherPresenterImpl;
import com.weatherapp.view.WeatherView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
    private FusedLocationProviderClient mFusedProvider;
    private LocationCallback mLocationCallBack;
    private LocationRequest mLocationRequest;
    private int MY_LOCATION = 99;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        weatherPresenter = new WeatherPresenterImpl(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION);
            }
        }
        getLocationValues();
    }

    //location results
    private void getLocationValues() {
        mLocationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {

                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude(), 1);
                    String city = addresses.get(0).getLocality();
                    DataConstants.CITY_NAME = city;
                    stopGps();
                    weatherPresenter.makeWeatherServiceCall();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.onLocationResult(locationResult);
            }
        };
        mLocationRequest = LocationHelper.createLRequest();
        mFusedProvider = LocationServices.getFusedLocationProviderClient(this);
        startGps();
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
        weatherData = info;
        todayWeather.setVisibility(View.VISIBLE);
        todayWeather.setText(info.getWeather().get(0).getMain());
        detailClick.setVisibility(View.VISIBLE);
    }


    @Override
    public void navigateToDetail() {

        Intent detailIntent = new Intent(this, WeatherDetailPage.class);
        Bundle b = new Bundle();
        b.putInt(DataConstants.HUMIDITY, weatherData.getMain().getHumidity());
        b.putDouble(DataConstants.WIND, weatherData.getWind().getSpeed());
        b.putInt(DataConstants.PRESSURE, weatherData.getMain().getPressure());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startGps();
            } else {

            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopGps();
    }

    // start the fusion location provider and get the values
    private void startGps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedProvider.requestLocationUpdates(mLocationRequest, mLocationCallBack, null);
    }

    // stop the fusion provider
    private void stopGps() {
        mFusedProvider.removeLocationUpdates(mLocationCallBack);
    }
}

