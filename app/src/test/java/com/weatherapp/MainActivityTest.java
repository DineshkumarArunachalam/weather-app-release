package com.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.weatherapp.Activity.MainActivity;
import com.weatherapp.Activity.WeatherDetailPage;
import com.weatherapp.constants.DataConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by dineshkumar_a on 2/21/2018.
 */

@RunWith(RobolectricTestRunner.class)

public class MainActivityTest {

    private MainActivity mActivity;
    private TextView todayWeather;
    private Button DetailClick;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
        todayWeather = (TextView) mActivity.findViewById(R.id.today_weather);
        DetailClick=(Button)mActivity.findViewById(R.id.detail_screen);

    }

    @Test
    public void setWeatherToday() {


        todayWeather.setText(" cool testing weather 5deg");
        //  ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        //assertThat("Next activity has started", application.getNextStartedActivity(), is(notNullValue()));
    }

    @Test
    public void navigateToDetail() {
        WeatherDataTest data;
        Intent detailIntent = new Intent(mActivity, WeatherDetailPage.class);
        Bundle b = new Bundle();
        b.putInt(DataConstants.HUMIDITY,2);
        b.putDouble(DataConstants.WIND,2);
        b.putInt(DataConstants.PRESSURE,3);
        detailIntent.putExtras(b);
        mActivity.startActivity(detailIntent);

    }
}
