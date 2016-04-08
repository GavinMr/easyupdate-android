package com.gary.android.easyupdate.plugin.umeng;

import android.content.Context;

import com.gary.android.easyupdate.EasyUpdateConfig;
import com.gary.android.easyupdate.EasyUpdateListener;
import com.gary.android.easyupdate.EasyUpdatePlugin;
import com.gary.android.easyupdate.EasyUpdateStatus;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

/**
 * 友盟检查更新插件
 */
public class UmengUpdatePlugin implements EasyUpdatePlugin {

    @Override
    public String getPluginId() {
        return "Umeng";
    }

    @Override
    public void checkUpdate(final Context context, final EasyUpdateListener listener, final EasyUpdateConfig config) {
        UmengUpdateListener updateListener = new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus,
                                         UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case 0: // has update
                        if (listener != null) {
                            listener.onUpdate(EasyUpdateStatus.HasUpdate, updateInfo);
                        }
                        if (config.isUpdateAutoPopup()) {
                            showUpdateDialog(context, updateInfo);
                        }
                        break;
                    case 1: // has no update
                        if (listener != null) {
                            listener.onUpdate(EasyUpdateStatus.NoUpdate, null);
                        }
                        break;
//			case 2: // none wifi
//			case 3: // time out
                    default:
                        if (listener != null) {
                            listener.onUpdate(EasyUpdateStatus.Error, null);
                        }
                        break;
                }
            }
        };
        UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(updateListener);
        UmengUpdateAgent.forceUpdate(context);
    }

    @Override
    public void showUpdateDialog(Context context, Object updateInfo) {
        UmengUpdateAgent.showUpdateDialog(context, (UpdateResponse) updateInfo);
    }

    @Override
    public void silentUpdate(Context context) {
        UmengUpdateAgent.silentUpdate(context);
    }
}