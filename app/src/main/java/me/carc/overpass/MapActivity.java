package me.carc.overpass;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;


import java.util.HashMap;

import hugo.weaving.DebugLog;
import me.carc.overpass.common.Preferences;
import me.carc.overpass.common.Util;
import me.carc.overpass.map.POIInfoWindow;
import me.carc.overpass.test.TestData;
import me.carc.overpasslib.overpass.OverpassQueryResult;

/**
 *  Map sample to display overpass lookups
 */
public class MapActivity extends AppCompatActivity implements MapEventsReceiver {

    private static final String TAG = Util.getTag();

    protected MapView mMap;
    private TextView requestTimeTxt;
    private ProgressBar searching;

    private MapOverpassAdapter overApiAdapter;
    RadiusMarkerClusterer mPoiMarkers;

    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searching = (ProgressBar) findViewById(R.id.searching);
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
        requestTimeTxt = (TextView) findViewById(R.id.requestTimeTxt);

//        map.setUseDataConnection(false);


        mMap.setBuiltInZoomControls(false);
        mMap.setMultiTouchControls(true);

        // Needed to get map events
        MapEventsOverlay overlay = new MapEventsOverlay(this);
        mMap.getOverlays().add(overlay);

        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mMap);
        mMap.getOverlays().add(scaleBarOverlay);

        mMap.setOnTouchListener(onMapTouchListener);

        overApiAdapter = new MapOverpassAdapter();

        mPoiMarkers = new RadiusMarkerClusterer(this);
//        Drawable clusterIconD = ResourcesCompat.getDrawable(getResources(), R.drawable.marker_poi_cluster, null);
//        Bitmap clusterIcon = ((BitmapDrawable) clusterIconD).getBitmap();
//        mPoiMarkers.setIcon(clusterIcon);
        mPoiMarkers.mAnchorV = Marker.ANCHOR_BOTTOM;
        mPoiMarkers.mTextAnchorU = 0.70f;
        mPoiMarkers.mTextAnchorV = 0.27f;
        mPoiMarkers.getTextPaint().setTextSize(12 * getResources().getDisplayMetrics().density);
        mMap.getOverlays().add(mPoiMarkers);

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
     * Map event receiver - Get single POI at touch location
     *
     * @param p point of touch
     * @return if handled
     */
    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {

        if(mMap.getZoomLevel() > 16) {
            searching.setVisibility(View.VISIBLE);
            requestTimeTxt.setText(R.string.req_time);
            startTime = System.currentTimeMillis();
            new fetchPois().execute(Util.getBoundsFromPoint(p), TestData.SinglePoi());
            return true;
        }
        return false;
    }


    /**
     * Map event receiver - get all POIs in map screen area
     *
     * @param p point of touch
     * @return if handled
     */
    @Override
    public boolean longPressHelper(GeoPoint p) {

        if(mMap.getZoomLevel() > 15) {
            searching.setVisibility(View.VISIBLE);
            requestTimeTxt.setText(R.string.req_time);
            startTime = System.currentTimeMillis();
            new fetchPois().execute(mMap.getBoundingBox(), TestData.Amenity());
            return true;
        }
        return false;
    }


    @DebugLog
    private class fetchPois extends AsyncTask<Object, Void, OverpassQueryResult> {

        @Override
        protected OverpassQueryResult doInBackground(Object... params) {
            if (!(params[0] instanceof BoundingBox))
                throw new RuntimeException("1st Parameter must be BoundingBox");

            if (!(params[1] instanceof HashMap))
                throw new RuntimeException("1st Parameter must be HashMap");

            BoundingBox box = (BoundingBox) params[0];
            HashMap tags = (HashMap)params[1];

            return overApiAdapter.search(tags, box);
        }

        protected void onPostExecute(OverpassQueryResult res) {
            if (res != null && res.elements.size() > 0) {

                POIInfoWindow poiInfoWindow = new POIInfoWindow(mMap);
                Drawable poiIcon = ContextCompat.getDrawable(MapActivity.this, R.drawable.ic_pin_drop);
                for (OverpassQueryResult.Element poi : res.elements) {

                    if(poi.tags.name != null) {

                        Marker poiMarker = new Marker(mMap);
                        poiMarker.setTitle(poi.tags.name);
                        poiMarker.setSnippet(poi.tags.amenity);
                        poiMarker.setPosition(new GeoPoint(poi.lat, poi.lon));
                        poiMarker.setIcon(poiIcon);

                        poiMarker.setRelatedObject(poi);
                        poiMarker.setInfoWindow(poiInfoWindow);
                        //thumbnail loading moved in async task for better performances.
                        mPoiMarkers.add(poiMarker);
                    }
                }
                mPoiMarkers.invalidate();
                mMap.invalidate();

                // show lookup duration
                double endtime = System.currentTimeMillis() - startTime;
                requestTimeTxt.setText(String.valueOf(endtime / 1000));

            } else {
                requestTimeTxt.setText(R.string.not_found);
            }

            searching.setVisibility(View.GONE);
        }
    }
}
