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
    public void checkUpdate(Context context, EasyUpdateListener listener, EasyUpdateConfig config);

    /**
     * 弹出更新提示对话框，配合EasyUpdateConfig.setUpdateAutoPopup(false)在更新回调中使用，调用该方法弹出更新提醒对话框，提醒用户有更新。
     * @param context
     * @param updateInfo
     */
    public void showUpdateDialog(Context context, Object updateInfo);

    /**
     * 静默更新，如果处于wifi环境检测更新，如果有更新，后台下载新版本，如果下载成功，则进行通知栏展示，用户点击通知栏开始安装。
     * @param context
     */
    public void silentUpdate(Context context);
}
