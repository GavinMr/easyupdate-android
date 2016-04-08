package com.gary.android.easyupdate.plugin.baidu;

import android.content.Context;

import com.baidu.autoupdatesdk.AppUpdateInfo;
import com.baidu.autoupdatesdk.AppUpdateInfoForInstall;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.CPCheckUpdateCallback;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.gary.android.easyupdate.EasyUpdateConfig;
import com.gary.android.easyupdate.EasyUpdateListener;
import com.gary.android.easyupdate.EasyUpdatePlugin;
import com.gary.android.easyupdate.EasyUpdateStatus;
import com.gary.android.easyupdate.Util;

/**
 * 需要设置
 * <meta-data android:name="BDAPPID" android:value="YOUR APP ID "/>
 * <meta-data android:name="BDAPPKEY" android:value="YOUR APP KEY"/>
 */
public class BaiduUpdatePlugin implements EasyUpdatePlugin {

    @Override
    public String getPluginId() {
        return "Baidu";
    }

    @Override
    public void checkUpdate(final Context context, final EasyUpdateListener listener, final EasyUpdateConfig config) {
        BDAutoUpdateSDK.cpUpdateCheck(context, new CPCheckUpdateCallback() {
            @Override
            public void onCheckUpdateCallback(AppUpdateInfo appUpdateInfo, AppUpdateInfoForInstall appUpdateInfoForInstall) {
                if (appUpdateInfo != null || appUpdateInfoForInstall != null) {
                    if (listener != null) {
                        listener.onUpdate(EasyUpdateStatus.HasUpdate, appUpdateInfo);
                    }

                    if (config.isUpdateAutoPopup()) {
                        BDAutoUpdateSDK.uiUpdateAction(context, new UICheckUpdateCallback() {
                            @Override
                            public void onCheckComplete() {
                                // 当检测到无版本更新或者用户关闭版本更新提示框 或者用户点击了升级下载时会触发回调该方法

                                // 不传入该回调，会导致无法显示更新提示！！
                            }
                        });
                    }
                } else {
                    if (listener != null) {
                        if (Util.isNetworkAvailable(context)) {
                            listener.onUpdate(EasyUpdateStatus.NoUpdate, null);
                        } else {
                            listener.onUpdate(EasyUpdateStatus.Error, null);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void silentUpdate(Context context) {
        BDAutoUpdateSDK.silenceUpdateAction(context);
    }

    @Override
    public void showUpdateDialog(Context context, Object updateInfo) {
        BDAutoUpdateSDK.uiUpdateAction(context, new UICheckUpdateCallback() {
            @Override
            public void onCheckComplete() {
                // 当检测到无版本更新或者用户关闭版本更新提示框 或者用户点击了升级下载时会触发回调该方法

                // 不传入该回调，会导致无法显示更新提示！！
            }
        });
    }
}
