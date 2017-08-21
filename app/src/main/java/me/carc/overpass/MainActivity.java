package me.carc.overpass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import butterknife.ButterKnife;
import me.carc.overpass.common.Preferences;
import me.carc.overpass.common.Util;

public class MainActivity extends AppCompatActivity implements MapEventsReceiver {


    private static final String TAG = Util.getTag();

    protected MapView mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initMap();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mMap.setTilesScaledToDpi(true); // moved to here so set on return from other activities

        mMap.getController().setZoom(Preferences.getZoom(this));

        GeoPoint savedCenter = new GeoPoint(Preferences.getLat(this), Preferences.getLng(this));
        mMap.getController().animateTo(savedCenter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Preferences.setMapPrefs(this, mMap.getMapCenter(), mMap.getZoomLevel());
        super.onBackPressed();
    }

    /**
     * Init the map
     */
    private void initMap() {

        mMap = (MapView) findViewById(R.id.mapView);

//        map.setUseDataConnection(false);


        mMap.setBuiltInZoomControls(false);
        mMap.setMultiTouchControls(true);

        // Needed to get map events
        MapEventsOverlay overlay = new MapEventsOverlay(this);
        mMap.getOverlays().add(overlay);

        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mMap);
        mMap.getOverlays().add(scaleBarOverlay);

        mMap.setOnTouchListener(onMapTouchListener);
    }


    private MapView.OnTouchListener onMapTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "Press event");
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d(TAG, "Move event");
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "Release event");
                    break;
                case MotionEvent.ACTION_CANCEL:
                    Log.d(TAG, "Cancel event");
                    break;
            }
            return false;
        }
    };

    /**
     * Map event receiver
     * @param p point of touch
     * @return if handled
     */
    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {

        Toast.makeText(this, p.toDoubleString(), Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Map event receiver
     * @param p point of touch
     * @return if handled
     */
    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
    }
}
