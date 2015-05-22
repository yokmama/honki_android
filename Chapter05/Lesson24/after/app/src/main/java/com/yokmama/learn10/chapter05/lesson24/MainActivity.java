package com.yokmama.learn10.chapter05.lesson24;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttonのインスタンスを取得
        mButton = (Button) findViewById(R.id.button);

        //リスナーをセット
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Serviceを起動
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                intent.setAction(MyIntentService.ACTION_COUNT_UP);
                startService(intent);
            }
        });

        //Buttonのテキストを更新
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //更新用BroadcastReceiverの登録
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyIntentService.ACTION_UPDATE_VALUE);
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //更新用BroadcastReceiverの解除
        unregisterReceiver(myReceiver);
    }

    private void updateUI(){
        //Applicationからカウントの値を取得してButtonにセット
        MyApplication myApplication = (MyApplication) getApplication();
        mButton.setText("count=" + myApplication.getCount());
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI();
        }
    };
}
