package com.gary.android.easyupdate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.gary.android.easyupdate.plugin.umeng.UmengUpdatePlugin;
import com.gary.android.easyupdate.sample.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyUpdate.registerUpdatePlugin(new UmengUpdatePlugin());

        findViewById(R.id.umeng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyUpdate.silentUpdate(v.getContext(), UmengUpdatePlugin.PLUGIN_ID);
            }
        });
    }
}
