package ca.mitchhentges.positionmock;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Mitch
 * on 8/7/2015.
 */
public class PositionMockActivity extends ActionBarActivity {

    private CurrentLocation currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        //todo remove this debugging stuff
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new DummyLocationListener());

        currentLocation = new CurrentLocation(locationManager);
        currentLocation.init();

        double latitude = Math.random() * 240 - 120;
        double longitude = Math.random() * 360 - 180;

        currentLocation.set(new Position(latitude, longitude));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentLocation.clean();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.apply:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
