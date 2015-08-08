package ca.mitchhentges.positionmock;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * {@author Mitch} on 8/7/2015.
 */
public class Map {

    private GoogleMap googleMap;

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void applyTarget(CurrentLocation currentLocation) {
        if (googleMap == null) {
            return;
        }

        LatLng latLng = googleMap.getCameraPosition().target;
        currentLocation.set(new Position(latLng.latitude, latLng.longitude));
    }
}
