package com.yokmama.learn10.chapter05.lesson22;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttonのインスタンスを取得
        Button button = (Button) findViewById(R.id.button);

        //Buttonにカウントをセット
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int count = preferences.getInt("count", 0);
        button.setText("Count:" + count);

        //リスナーをセット
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BroadcastReceiverでカウントアップを実行
                Intent intent = new Intent(MainActivity.this, MyReceiver.class);
                intent.setAction("up");
                sendBroadcast(intent);
            }
        });

    }
}
