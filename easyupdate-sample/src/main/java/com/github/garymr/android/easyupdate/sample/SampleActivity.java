package com.github.garymr.android.easyupdate.sample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.garymr.android.easyupdate.EasyUpdate;
import com.github.garymr.android.easyupdate.EasyUpdateListener;
import com.github.garymr.android.easyupdate.EasyUpdateStatus;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViewById(R.id.silent_update_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyUpdate.silentUpdate(view.getContext());
            }
        });

        findViewById(R.id.check_update_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("检查更新中，请稍等…");
                dialog.show();

                EasyUpdate.checkUpdate(view.getContext(), new EasyUpdateListener() {
                    @Override
                    public void onUpdate(EasyUpdateStatus status, Object updateInfo) {
                        dialog.dismiss();

                        if (status == EasyUpdateStatus.HasUpdate) {
                            // 如果updateAutoPopup设置为true，不需要处理
                            // 如果设置为false，需要调用 EasyUpdate.showUpdateDialog(view.getContext(), updateInfo);
                        } else if (status == EasyUpdateStatus.NoUpdate) {
                            Toast.makeText(view.getContext(), "已是最新版本", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "检查新版本失败，请稍候重试。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
