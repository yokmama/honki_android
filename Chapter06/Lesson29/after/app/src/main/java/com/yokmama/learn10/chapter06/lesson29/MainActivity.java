package com.yokmama.learn10.chapter06.lesson29;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.yokmama.learn10.chapter06.lesson29.net.ConnectionService;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToggleButtonのインスタンスを取得
        ToggleButton tb = (ToggleButton) findViewById(R.id.switchAutoWallpaper);

        // ToggleButtonに前回の値をセット
        final PreferenceDao pref = new PreferenceDao(this);
        tb.setChecked(pref.isAutoWallpaperEnabled());

        //リスナーをセット
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 壁紙の変更を開始
                    startSearch();
                } else {
                    // 壁紙の変更を停止
                    stopSearch();
                }
                //壁紙変更の設定を保存
                pref.setAutoWallpaperEnabled(isChecked);
            }
        });
    }

    /**
     * 壁紙の変更を開始
     */
    private void startSearch() {
        // 検索キーワード固定
        final String keyword = "スマホ壁紙";

        // バックグラウンドで検索と画像の取得を行う
        Intent intent = new Intent(this, ConnectionService.class);
        intent.setAction(ConnectionService.ACTION_START);
        intent.putExtra(ConnectionService.EXTRA_SEARCH_KEYWORD, keyword);
        startService(intent);
    }

    /**
     * 壁紙の変更を停止
     */
    private void stopSearch() {
        Intent intent = new Intent(this, ConnectionService.class);
        intent.setAction(ConnectionService.ACTION_STOP);
        startService(intent);
    }

}
