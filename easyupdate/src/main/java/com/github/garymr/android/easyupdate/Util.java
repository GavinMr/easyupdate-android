package com.github.garymr.android.easyupdate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

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

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static void logD(String msg) {
        Log.d(TAG, msg);
    }
}
