package com.weatherapp.constants;

import com.google.android.gms.location.LocationRequest;

/**
 * Created by dineshkumar_a on 2/22/2018.
 */

public class LocationHelper {

    private static final String TAG = "LocationHelper";
    private static final int UPDATE_INTERVAL = 4000;
    private static final int FASTEST_UPDATE_INTERVAL = 2000;


    public static LocationRequest createLRequest() {

        LocationRequest request= new LocationRequest();
        request.setInterval(UPDATE_INTERVAL);
        request.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return request;


    }
}
