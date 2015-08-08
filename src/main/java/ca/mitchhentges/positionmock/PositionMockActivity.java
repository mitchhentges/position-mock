package ca.mitchhentges.positionmock;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by Mitch
 * on 8/7/2015.
 */
public class PositionMockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //todo remove this debugging stuff
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new DummyLocationListener());

        final PositionPublish positionPublish = new PositionPublish(locationManager);

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    double latitude = Math.random() * 240 - 120;
                    double longitude = Math.random() * 360 - 180;

                    positionPublish.publish(new Position(latitude, longitude));
                }
            }
        }.start();
    }
}
