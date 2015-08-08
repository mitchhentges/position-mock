package ca.mitchhentges.positionmock;

import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationProvider;

/**
 * Created by Mitch
 * on 8/7/2015.
 */
public class PositionPublish {
    public static final String PROVIDER = LocationManager.GPS_PROVIDER;

    private final LocationManager locationManager;

    public PositionPublish(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void setup() {
        locationManager.addTestProvider(PROVIDER, false, false, false, false, true, true, true, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        locationManager.setTestProviderEnabled(PROVIDER, true);
        locationManager.setTestProviderStatus(PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
    }

    public void publish(Position position) {
        locationManager.setTestProviderLocation(PROVIDER, position.toLocation());
    }

    public void clean() {
        locationManager.removeTestProvider(PROVIDER);
    }
}
