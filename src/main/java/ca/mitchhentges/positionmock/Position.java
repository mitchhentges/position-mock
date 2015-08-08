package ca.mitchhentges.positionmock;

import android.location.Location;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Mitch
 * on 8/7/2015.
 */
public class Position {

    private final double latitude;
    private final double longitude;

    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location toLocation() {
        Location location = new Location(PositionPublish.PROVIDER);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        //Fill with a bunch of dummy data that we don't care about
        location.setAltitude(0);
        location.setBearing(0);
        location.setSpeed(0);
        location.setTime(System.currentTimeMillis());
        location.setAccuracy(1);

        // See http://jgrasstechtips.blogspot.ca/2012/12/android-incomplete-location-object.html
        // If not "made complete", android will balk
        try {
            Method locationJellyBeanFixMethod = Location.class.getMethod("makeComplete");
            locationJellyBeanFixMethod.invoke(location);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return location;
    }
}
