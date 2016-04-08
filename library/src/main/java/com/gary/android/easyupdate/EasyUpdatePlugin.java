package com.gary.android.easyupdate;

import android.content.Context;

/**
 * 检查更新插件接口
 */
public interface EasyUpdatePlugin {

    public String getPluginId();

    /**
     * 检查是否有新版本，立即联网检查更新。
     * @param context
     * @param listener
     */
    public void checkUpdate(final Context context, final EasyUpdateListener listener);

    /**
     * 静默更新，如果处于wifi环境检测更新，如果有更新，后台下载新版本，如果下载成功，则进行通知栏展示，用户点击通知栏开始安装。
     * @param context
     */
    public void silentUpdate(final Context context);
}
