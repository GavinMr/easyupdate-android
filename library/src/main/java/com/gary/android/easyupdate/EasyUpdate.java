package com.gary.android.easyupdate;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * 提供客户端检查更新功能
 */
public final class EasyUpdate {

    private static EasyUpdateConfig config;


    public static void initialize(EasyUpdateConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("EasyUpdateConfig is null");
        }
        if (config.getUpdatePlugin() == null) {
            throw new IllegalArgumentException("UpdatePlugin was not seted!");
        }
        EasyUpdate.config = config;
    }

    /**
     * 静默更新，如果处于wifi环境检测更新，如果有更新，后台下载新版本，如果下载成功，则进行通知栏展示，用户点击通知栏开始安装。
     * @param context
     */
    public static void silentUpdate(final Context context) {
        if (config == null) {
            throw new IllegalArgumentException("EasyUpdate was not initialized!");
        }
        if (context == null) {
            throw new IllegalArgumentException("Context is null");
        }

        config.getUpdatePlugin().silentUpdate(context);
    }

    /**
     * 检查是否有新版本。
     * @param context
     * @param listener
     */
    public static void checkUpdate(final Context context, final EasyUpdateListener listener) {
        if (config == null) {
            throw new IllegalArgumentException("EasyUpdate was not initialized!");
        }
        if (context == null) {
            throw new IllegalArgumentException("Context is null");
        }

        if (!config.isUpdateOnlyWifi() || Util.isWifiNetworkAvailable(context)) {
            checkUpdateForce(context, listener);
        } else {
            if (listener != null) {
                listener.onUpdate(EasyUpdateStatus.NoUpdate);
            }
        }
    }

    /**
     * 检查是否有新版本，强制立即联网检查更新。该方法不会检查UpdateOnlyWifi设置。
     * @param context
     * @param listener
     */
    public static void checkUpdateForce(final Context context, final EasyUpdateListener listener) {
        if (config == null) {
            throw new IllegalArgumentException("EasyUpdate was not initialized!");
        }
        if (context == null) {
            throw new IllegalArgumentException("Context is null");
        }

        config.getUpdatePlugin().checkUpdate(context, listener);
    }
}
