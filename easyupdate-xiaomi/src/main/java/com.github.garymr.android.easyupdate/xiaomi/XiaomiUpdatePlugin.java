package com.github.garymr.android.easyupdate.xiaomi;

import android.content.Context;

import com.github.garymr.android.easyupdate.EasyUpdateConfig;
import com.github.garymr.android.easyupdate.EasyUpdateListener;
import com.github.garymr.android.easyupdate.EasyUpdatePlugin;
import com.github.garymr.android.easyupdate.EasyUpdateStatus;
import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.UpdateStatus;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;

public class XiaomiUpdatePlugin implements EasyUpdatePlugin {

    private final int FROM_CHECK_UPDATE = 0;
    private final int FROM_SHOW_UPDATE_DIALOG = 1;
    private final int FROM_SILENT_UPDATE = 2;

    @Override
    public String getPluginId() {
        return "Xiaomi";
    }

    @Override
    public void checkUpdate(final Context context, final EasyUpdateListener listener, final EasyUpdateConfig config) {
        XiaomiUpdateAgent.setUpdateListener(new UpdateListener(context, FROM_CHECK_UPDATE, listener, config));
        XiaomiUpdateAgent.setCheckUpdateOnlyWifi(false);
        XiaomiUpdateAgent.setUpdateAutoPopup(false);
        XiaomiUpdateAgent.update(context);
    }

    @Override
    public void showUpdateDialog(Context context, Object o) {
        // 小米没有提供显示对话框功能，所以重新走一遍检查更新逻辑
        XiaomiUpdateAgent.setUpdateListener(new UpdateListener(context, FROM_SHOW_UPDATE_DIALOG));
        XiaomiUpdateAgent.setCheckUpdateOnlyWifi(false);
        XiaomiUpdateAgent.setUpdateAutoPopup(true);
        XiaomiUpdateAgent.update(context);
    }

    @Override
    public void silentUpdate(Context context) {
        XiaomiUpdateAgent.setUpdateListener(new UpdateListener(context, FROM_SILENT_UPDATE));
        XiaomiUpdateAgent.setCheckUpdateOnlyWifi(true);
        XiaomiUpdateAgent.setUpdateAutoPopup(true);
        XiaomiUpdateAgent.update(context);
    }

    class UpdateListener implements com.xiaomi.market.sdk.XiaomiUpdateListener {

        private Context context;
        private EasyUpdateListener listener;
        private EasyUpdateConfig config;
        private int from;

        public UpdateListener(Context context, int from) {
            this.context = context;
            this.from = from;
        }

        public UpdateListener(Context context, int from, EasyUpdateListener listener, EasyUpdateConfig config) {
            this.context = context;
            this.from = from;
            this.listener = listener;
            this.config = config;
        }

        @Override
        public void onUpdateReturned(int status, UpdateResponse updateResponse) {
            if (status == UpdateStatus.STATUS_UPDATE) {
                if (listener != null) {
                    listener.onUpdate(EasyUpdateStatus.HasUpdate, updateResponse);
                }

                if (from == FROM_SILENT_UPDATE) { // 静默更新会自动弹出更新对话框
                    XiaomiUpdateAgent.arrange();
                } else if (from == FROM_CHECK_UPDATE && config.isUpdateAutoPopup()) {
                    showUpdateDialog(context, updateResponse);
                } else if (from == FROM_SHOW_UPDATE_DIALOG) {
                    XiaomiUpdateAgent.arrange();
                }
            } else {
                if (listener != null) {
                    if (status == UpdateStatus.STATUS_NO_UPDATE) {
                        listener.onUpdate(EasyUpdateStatus.NoUpdate, null);
                    } else {
                        listener.onUpdate(EasyUpdateStatus.Error, null);
                    }
                }
            }
        }
    }
}
