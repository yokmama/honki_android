package com.yokmama.learn10.chapter06.lesson29;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.service.wallpaper.WallpaperService;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final PreferenceDao preferenceDao = new PreferenceDao(this);

        setContentView(R.layout.activity_main);

        Switch s = (Switch) findViewById(R.id.switchAutoWallpaper);

        // チェックボックスを設定
        s.setChecked(preferenceDao.isAutoWallpaperEnabled());
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    WallpaperBroadcastReceiver.startPolling(getApplicationContext());
                } else {
                    WallpaperBroadcastReceiver.stopPolling(getApplicationContext());
                }
                preferenceDao.setAutoWallpaperEnabled(getApplicationContext(), isChecked);
            }
        });
    }
}
