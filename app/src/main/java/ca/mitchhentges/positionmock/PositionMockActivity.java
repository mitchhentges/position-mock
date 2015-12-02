package ca.mitchhentges.positionmock;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

/**
 * Created by Mitch
 * on 8/7/2015.
 */
public class PositionMockActivity extends ActionBarActivity implements OnMapReadyCallback {

    private CurrentLocation currentLocation;
    private Map map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

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

    private void shoop() {
        // Initial colors of each system bar.
        final int toolbarColor = getResources().getColor(R.color.toolbar_color);
        final int toolbarToColor = getResources().getColor(R.color.toolbar_to_color);
        final int duration = 500;

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Use animation position to blend colors.
                float position = animation.getAnimatedFraction();

                if (position > 0.5) {
                    position = Math.abs(position - 1);
                }

                Log.i("DERP", animation.getAnimatedFraction() + "|" + position * 2);
                int blended = blendColors(toolbarColor, toolbarToColor, position * 2);
                ColorDrawable background = new ColorDrawable(blended);
                getSupportActionBar().setBackgroundDrawable(background);
            }
        });

        anim.setDuration(duration).start();
    }

    private int blendColors(int from, int to, float ratio) {
        final float inverseRatio = 1f - ratio;

        final float r = Color.red(to) * ratio + Color.red(from) * inverseRatio;
        final float g = Color.green(to) * ratio + Color.green(from) * inverseRatio;
        final float b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio;

        return Color.rgb((int) r, (int) g, (int) b);
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
                shoop();
                map.applyTarget(currentLocation);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map.setGoogleMap(googleMap);
    }
}
