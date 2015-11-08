package ca.mitchhentges.positionmock;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mitch
 * on 8/7/2015.
 */
public class CurrentLocation {
    public static final String PROVIDER = LocationManager.GPS_PROVIDER;
    private final LocationManager locationManager;
    private final TimerTask publishTask = new TimerTask() {
        @Override
        public void run() {
            applyToSystem();
        }
    };

    private Position current;
    private boolean canMock = true;

    public CurrentLocation(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void init() {
        if (!canMock) {
            return;
        }

        locationManager.addTestProvider(PROVIDER, false, false, false, false, true, true, true, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        locationManager.setTestProviderEnabled(PROVIDER, true);
        locationManager.setTestProviderStatus(PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(publishTask, 0, 1000);
    }

    public void set(Position position) {
        current = position;
    }

    public void clean() {
        if (!canMock) {
            return;
        }

        locationManager.removeTestProvider(PROVIDER);
        publishTask.cancel();
    }

    private void applyToSystem() {
        if (!canMock) {
            return;
        }

        if (current != null) {
            locationManager.setTestProviderLocation(PROVIDER, current.toLocation());
        }
    }

    public void setCanMock(boolean canMock) {
        this.canMock = canMock;
    }

    public boolean canMock() {
        return canMock;
    }
}
