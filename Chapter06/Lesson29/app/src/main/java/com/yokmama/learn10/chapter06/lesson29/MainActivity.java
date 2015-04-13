package com.yokmama.learn10.chapter06.lesson29;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.yokmama.learn10.chapter06.lesson29.net.CustomSearchApiItem;
import com.yokmama.learn10.chapter06.lesson29.net.RequestGoogleCustomSearchApi;

import java.util.List;


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

        RequestGoogleCustomSearchApi api = new RequestGoogleCustomSearchApi(this);
        api.setMock(true);
        api.reqCustomSearchApi("Android", new RequestGoogleCustomSearchApi.RestResultCallback<List<CustomSearchApiItem>>() {
            @Override
            public void onCompletion(List<CustomSearchApiItem> result, Throwable error) {
                if (error == null) {
                    for (CustomSearchApiItem item : result) {
                        
                    }
                } else {
                    Log.d("MainActivity", "通信エラー", error);
                }
            }
        });
    }
}
