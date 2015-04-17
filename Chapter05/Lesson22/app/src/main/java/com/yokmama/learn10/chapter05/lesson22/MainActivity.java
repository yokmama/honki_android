package com.yokmama.learn10.chapter05.lesson22;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ボタンにカウントをセット
        Button button = (Button) findViewById(R.id.button);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int count = preferences.getInt("count", 0);
        button.setText("Count:" + count);

        //ボタンのクリック処理
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ブロードキャストレシーバを使ってカウントアップを実施
                Intent intent = new Intent(MainActivity.this, MyReceiver.class);
                intent.setAction("up");
                sendBroadcast(intent);
            }
        });

    }
}
