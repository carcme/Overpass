package me.carc.overpass.common;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;

/**
 * Common utils for map
 * Created by bamptonm on 21/08/2017.
 */

public class Util {

    private static final double LIMIT = 0.00012;

    /**
     * Build a TAG for debugging
     *
     * @return the debug tag
     */
    public static String getTag() {
        String tag = "";
        if (android.support.v7.appcompat.BuildConfig.DEBUG) {
            final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
            for (int i = 0; i < ste.length; i++) {
                if (ste[i].getMethodName().equals("getTag")) {
                    tag = "(" + ste[i + 1].getFileName() + ":" + ste[i + 1].getLineNumber() + ")";
                }
            }
        }
        return tag;
    }

    public static BoundingBox getBoundsFromPoint(GeoPoint p) {
        return new BoundingBox(
                p.getLatitude() + LIMIT,
                p.getLongitude() + LIMIT,
                p.getLatitude() - LIMIT,
                p.getLongitude() - LIMIT);
    }
}
