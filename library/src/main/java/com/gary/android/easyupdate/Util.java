package com.gary.android.easyupdate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by gang on 16/4/8.
 */
public final class Util {

    private static final String TAG = "EasyUpdate";

    private Util() {}

    /**
     * Returns whether the network is available.
     */
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            logD("Unable to get a ConnectivityManager");
            return false;
        }

        NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
        if (activeInfo == null) {
            logD("Network is not available");
            return false;
        }

        return true;
    }

    /**
     * Returns whether the network is wifi connection.
     */
    public static boolean isWifiNetworkAvailable(Context context) {
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            logD("Unable to get a ConnectivityManager");
            return false;
        }

        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return false;
        }

        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }

        return false;
    }

    public static void logD(String msg) {
        Log.d(TAG, msg);
    }
}
