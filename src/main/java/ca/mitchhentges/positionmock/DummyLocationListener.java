package ca.mitchhentges.positionmock;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Mitch
 * on 8/7/2015.
 */
public class DummyLocationListener implements LocationListener {
    public void onLocationChanged(Location location) {
        Log.i("MFW", "|" + location.getSpeed());
    }

    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    public void onProviderEnabled(String s) {

    }

    public void onProviderDisabled(String s) {

    }
}
