package com.gary.android.easyupdate;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * 提供客户端检查更新功能
 */
public final class EasyUpdate {

    /** 客户端最近一次检查更新时间 */
    private static final String LATEST_CLIENT_CHECKUPDATE_TIME = "easyupdate_last_client_checkupdate_time";

    private static final HashMap<String, EasyUpdatePlugin> pluginMap = new HashMap<>();

    // 每次检查更新的间隔时间
    private static long sUpdateCheckDuration = 1000 * 60 * 60 * 24;

    /**
     * 设置检查更新间隔时间，默认24小时
     * @param duration 单位：毫秒
     */
    public static void setUpdateCheckDuration(long duration) {
        sUpdateCheckDuration = duration;
    }

    private static boolean isNeedToCheckClientUpdate(Context context) {
        long time = PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(LATEST_CLIENT_CHECKUPDATE_TIME, -1);
        if (System.currentTimeMillis() > time + sUpdateCheckDuration) {
            return true;
        }
        return false;
    }

    /**
     * 注册更新插件
     * @param plugin
     */
    public static void registerUpdatePlugin(EasyUpdatePlugin plugin) {
        if (plugin == null || TextUtils.isEmpty(plugin.getPluginId())) {
            throw new IllegalArgumentException("Plugin or Plugin Id is null.");
        }

        pluginMap.put(plugin.getPluginId(), plugin);
    }

    /**
     * 取消更新插件注册
     * @param plugin
     */
    public static void unregisterUpdatePlugin(EasyUpdatePlugin plugin) {
        if (plugin == null || TextUtils.isEmpty(plugin.getPluginId())) {
            return;
        }

        pluginMap.remove(plugin.getPluginId());
    }

    /**
     * 取消更新插件注册
     * @param pluginId
     */
    public static void unregisterUpdatePlugin(String pluginId) {
        if (TextUtils.isEmpty(pluginId)) {
            return;
        }
        pluginMap.remove(pluginId);
    }

    /**
     * 静默更新，如果处于wifi环境检测更新，如果有更新，后台下载新版本，如果下载成功，则进行通知栏展示，用户点击通知栏开始安装。
     * @param context
     */
    public static void silentUpdate(final Context context, String pluginId) {
        if (context == null || TextUtils.isEmpty(pluginId)) {
            return;
        }

        if (isNeedToCheckClientUpdate(context)) {
            EasyUpdatePlugin plugin = pluginMap.get(pluginId);
            if (plugin != null) {
                plugin.silentUpdate(context);
            }
        }
    }

    /**
     * 检查是否有新版本，会判断上次检查更新时间，未到设定日期不会联网检查。
     * @param context
     * @param pluginId
     * @param listener
     */
    public static void checkUpdate(final Context context, String pluginId, final EasyUpdateListener listener) {
        if (context == null || TextUtils.isEmpty(pluginId)) {
            return;
        }

        if (isNeedToCheckClientUpdate(context)) {
            checkUpdateForce(context, pluginId, listener);
        } else {
            if (listener != null) {
                listener.onUpdate(EasyUpdateStatus.NoUpdate);
            }
        }
    }

    /**
     * 检查是否有新版本，强制立即联网检查更新。
     * @param context
     * @param pluginId
     * @param listener
     */
    public static void checkUpdateForce(final Context context, String pluginId, final EasyUpdateListener listener) {
        if (context == null || TextUtils.isEmpty(pluginId)) {
            return;
        }

        EasyUpdatePlugin plugin = pluginMap.get(pluginId);
        if (plugin != null) {
            plugin.checkUpdate(context, new EasyUpdateListenerWrapper(context, listener));
        }
    }

    static class EasyUpdateListenerWrapper implements EasyUpdateListener {

        private Context context;
        private EasyUpdateListener listener;

        protected EasyUpdateListenerWrapper(Context context, EasyUpdateListener listener) {
            this.context = context;
            this.listener = listener;
        }

        @Override
        public void onUpdate(EasyUpdateStatus status) {
            if (status == EasyUpdateStatus.HasUpdate || status == EasyUpdateStatus.NoUpdate) {
                PreferenceManager.getDefaultSharedPreferences(context)
                        .edit()
                        .putLong(LATEST_CLIENT_CHECKUPDATE_TIME, System.currentTimeMillis())
                        .apply();
            }

            if (this.listener != null) {
                this.listener.onUpdate(status);
            }
        }
    }
}
