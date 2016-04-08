package com.gary.android.easyupdate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gary.android.easyupdate.plugin.umeng.UmengUpdatePlugin;
import com.gary.android.easyupdate.sample.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyUpdateConfig config = EasyUpdateConfig.newBuilder()
                                        .setDeltaUpdate(false)
                                        .setUpdateOnlyWifi(true)
                                        .setUpdateAutoPopup(true)
                                        .setUpdatePlugin(new UmengUpdatePlugin())
                                        .build();

        EasyUpdate.initialize(config);

        findViewById(R.id.umeng_silentupdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyUpdate.silentUpdate(v.getContext());
            }
        });

        findViewById(R.id.umeng_checkupdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyUpdate.checkUpdate(v.getContext(), new EasyUpdateListener() {
                    @Override
                    public void onUpdate(EasyUpdateStatus status, Object updateInfo) {
                        if (status == EasyUpdateStatus.Error) {
                            showToast("检查更新失败！");
                        } else if (status == EasyUpdateStatus.NoUpdate) {
                            showToast("当前已经是最新版！");
                        } else {
                            // setUpdateAutoPopup 为true，无需处理，会自动弹出更新对话框
                        }
                    }
                });
            }
        });
    }

    public void showToast(CharSequence text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }
}
