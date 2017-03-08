package com.github.garymr.android.easyupdate;

/**
 * 检查更新回调接口
 */
public interface EasyUpdateListener {

    void onUpdate(EasyUpdateStatus status, Object updateInfo);
}
