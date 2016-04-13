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

        if (!Util.isNetworkAvailable(context)) {
            if (listener != null) {
                listener.onUpdate(EasyUpdateStatus.Error, null);
            }
            return;
        }

        if (!config.isUpdateOnlyWifi() || Util.isWifiNetworkAvailable(context)) {
            config.getUpdatePlugin().checkUpdate(context, listener, config);
        } else {
            if (listener != null) {
                listener.onUpdate(EasyUpdateStatus.NoUpdate, null);
            }
        }
    }

    /**
     * 弹出更新提示对话框，配合EasyUpdateConfig.setUpdateAutoPopup(false)在更新回调中使用，调用该方法弹出更新提醒对话框，提醒用户有更新。
     * @param context
     * @param updateInfo
     */
    public static void showUpdateDialog(Context context, Object updateInfo) {
        if (config == null) {
            throw new IllegalArgumentException("EasyUpdate was not initialized!");
        }
        if (context == null) {
            throw new IllegalArgumentException("Context is null");
        }
        if (updateInfo == null) {
            throw new IllegalArgumentException("UpdateInfo is null");
        }

        config.getUpdatePlugin().showUpdateDialog(context, updateInfo);
    }

}
