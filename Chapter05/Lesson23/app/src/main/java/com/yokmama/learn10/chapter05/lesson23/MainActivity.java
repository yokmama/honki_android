package com.yokmama.learn10.chapter05.lesson23;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ボタンのインスタンスを取得
        mButton = (Button) findViewById(R.id.button);

        //ボタンのクリック処理
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //サービスを起動
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);

                //ボタンのテキストを更新
                updateButtonText();
            }
        });

        updateButtonText();
    }

    /**
     * ボタンのテキスト表示を更新.
     */
    private void updateButtonText() {
        //アプリケーションからカウントの値を取得してボタンにセット
        MyApplication myApplication = (MyApplication) getApplication();
        mButton.setText("count=" + myApplication.getCount());
    }
}
