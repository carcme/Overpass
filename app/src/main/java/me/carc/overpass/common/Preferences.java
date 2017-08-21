package me.carc.overpass.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.osmdroid.api.IGeoPoint;

/**
 * Created by bamptonm on 21/08/2017.
 */

public class Preferences {

    private final static String PREFS = "carc.me.Prefs";
    private final static String PREF_ZOOM = "pref_key_zoom";
    private final static String PREF_LAT = "pref_key_latitude";
    private final static String PREF_LNG = "pref_key_longtitude";
    private final static float BERLIN_LAT = 52.517f;
    private final static float BERLIN_LNG = 13.350f;


    public static void setMapPrefs(Context context, IGeoPoint point, int zoom) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(PREF_ZOOM, zoom);
        editor.putFloat(PREF_LAT, (float)point.getLatitude());
        editor.putFloat(PREF_LNG, (float) point.getLongitude());
        editor.apply();
    }


    public static int getZoom(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        return settings.getInt(PREF_ZOOM, 15);
    }

    public static double getLat(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        return settings.getFloat(PREF_LAT, BERLIN_LAT);
    }

    public static double getLng(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        return settings.getFloat(PREF_LNG, BERLIN_LNG);
    }
}
