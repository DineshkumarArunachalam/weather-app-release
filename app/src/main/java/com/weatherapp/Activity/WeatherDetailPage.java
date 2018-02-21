package com.weatherapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.weatherapp.R;
import com.weatherapp.constants.DataConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dineshkumar_a on 2/20/2018.
 */

public class WeatherDetailPage extends AppCompatActivity {

    @BindView(R.id.humidity)
    TextView humidity;
    @BindView(R.id.wind)
    TextView wind;
    @BindView(R.id.pressure)
    TextView pressure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);
        ButterKnife.bind(this);
        Bundle bundle =getIntent().getExtras();
        if(bundle!=null ) {
            String humid=getString(R.string.humidity) + bundle.getInt(DataConstants.HUMIDITY);
            String pressureString=getString(R.string.pressure) + bundle.getDouble(DataConstants.PRESSURE);
            String windString=getString(R.string.wind) + bundle.getInt(DataConstants.WIND);
            humidity.setText(humid);
            pressure.setText(pressureString);
            wind.setText(windString);
        }

    }
}
