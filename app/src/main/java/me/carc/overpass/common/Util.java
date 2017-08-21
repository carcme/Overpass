package me.carc.overpass.common;

import android.support.v7.appcompat.*;

/**
 * Created by bamptonm on 21/08/2017.
 */

public class Util {

    /**
     * Build a TAG for debugging
     *
     * @return
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

}
