package ca.mitchhentges.positionmock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.GroundOverlayOptions;

/**
 * Created by Mitch
 * on 8/7/2015.
 */
public class PositionMockActivity extends ActionBarActivity implements OnMapReadyCallback {

    private CurrentLocation currentLocation;
    private Map map;
    private TitleBarAnimator titleBarAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        titleBarAnimator = new TitleBarAnimator(this);

        try {
            map = new Map();
            currentLocation = new CurrentLocation((LocationManager) getSystemService(LOCATION_SERVICE));
            currentLocation.init();

            ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        } catch (SecurityException e) {
            currentLocation.setCanMock(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentLocation.canMock()) {
            return;
        }

        Log.i("PositionMock", "Don't have mocking permission, can't run");
        AlertDialog. Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Missing permissions")
                .setMessage("(Android 6+) Set as \"mock location app\" in Developer Options\n" +
                        "(Android 5-) Enable \"Allow Mock Locations\"")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        finish();
                    }
                })
                .create()
                .show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.apply:
                applyMockLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map.setGoogleMap(googleMap);
    }

    public void applyMockLocation() {
        titleBarAnimator.animateTitleBar();
        map.applyTarget(currentLocation);
    }
}
