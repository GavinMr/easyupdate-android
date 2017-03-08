package com.github.garymr.android.easyupdate.sample;

import android.app.Application;

import com.github.garymr.android.easyupdate.EasyUpdate;
import com.github.garymr.android.easyupdate.EasyUpdateConfig;
import com.github.garymr.android.easyupdate.baidu.BaiduUpdatePlugin;
import com.github.garymr.android.easyupdate.xiaomi.XiaomiUpdatePlugin;

/**
 * Created by gang on 2017/3/8.
 */

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EasyUpdate.initialize(new EasyUpdateConfig.Builder()
                    .setDeltaUpdate(false) // 是否开启增量更新，默认false
                    .setUpdateOnlyWifi(true) // 是否仅在WIFI环境下检查更新，默认true
                    .setUpdateAutoPopup(true) // 是否检查更新自动弹出更新对话框，默认true
//                    .setUpdatePlugin(new XiaomiUpdatePlugin()) // 设置检查更新使用的插件，必须设置
                    .setUpdatePlugin(new BaiduUpdatePlugin()) // 可根据渠道设置不同的检查更新
                    .create()
        );
    }
}
