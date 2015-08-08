package ca.mitchhentges.positionmock;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Mitch
 * on 8/7/2015.
 */
public class PositionMockActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new DummyLocationListener());

        final String PROVIDER_NAME = LocationManager.GPS_PROVIDER;
        locationManager.addTestProvider(PROVIDER_NAME, false, false, false, false, true, true, true, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);

        locationManager.setTestProviderEnabled(PROVIDER_NAME, true);
        locationManager.setTestProviderStatus(PROVIDER_NAME, LocationProvider.AVAILABLE, null, System.currentTimeMillis());

        final Location location = new Location(PROVIDER_NAME);
        location.setLatitude(15);
        location.setLongitude(15);
        location.setAltitude(1.58);
        location.setBearing(14);
        location.setSpeed(80);
        location.setTime(System.currentTimeMillis());
        location.setAccuracy(100);
        Method locationJellyBeanFixMethod = null; //beautiful
        try {
            locationJellyBeanFixMethod = Location.class.getMethod("makeComplete");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (locationJellyBeanFixMethod != null) {
            try {
                locationJellyBeanFixMethod.invoke(location);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        locationManager.setTestProviderLocation(PROVIDER_NAME, location);

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    locationManager.setTestProviderLocation(PROVIDER_NAME, location);
                }
            }
        }.start();
    }
}
