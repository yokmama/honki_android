package com.yokmama.learn10.chapter06.lesson29;

import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.yokmama.learn10.chapter06.lesson29.net.ConnectionService;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText mEditText;
    private PreferenceDao mPref;
    private Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = new PreferenceDao(this);

        setContentView(R.layout.activity_main);

        mSwitch = (Switch) findViewById(R.id.switchAutoWallpaper);
        mEditText = (EditText) findViewById(R.id.edit);

        // チェックボックスを設定
        mSwitch.setChecked(mPref.isAutoWallpaperEnabled());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    WallpaperBroadcastReceiver.startPolling(getApplicationContext());
                } else {
                    WallpaperBroadcastReceiver.stopPolling(getApplicationContext());

                    WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
                    try {
                        wm.clear();
                    } catch (IOException e) {
                        Log.e(TAG, "壁紙のクリアに失敗しました。", e);
                    }
                }
                mPref.setAutoWallpaperEnabled(getApplicationContext(), isChecked);
            }
        });

        // 最後にセットしたキーワードを入力
        mEditText.setText(mPref.getKeyword());
    }

    @Override
    protected void onPause() {
        super.onPause();

        // キーワードを追加
        String keyword = mEditText.getText().toString();
        if (!TextUtils.isEmpty(keyword)) {
            if (!mPref.getKeyword().equals(keyword)) {
                // バックグラウンドで検索と画像の取得を行う
                Intent intent = new Intent(this, ConnectionService.class);
                intent.putExtra(ConnectionService.EXTRA_SEARCH_KEYWORD, keyword);
                startService(intent);

                WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
                try {
                    wm.clear();
                } catch (IOException e) {
                    Log.e(TAG, "壁紙のクリアに失敗しました。", e);
                }
            }
        }
        mPref.putKeyword(keyword);
    }
}
