package com.yokmama.learn10.chapter05.lesson23;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

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
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);

                //Buttonのテキストを更新
                updateButtonText();
            }
        });

        updateButtonText();
    }

    /**
     * ボタンのテキスト表示を更新.
     */
    private void updateButtonText() {
        //Applicationからカウントの値を取得してButtonにセット
        MyApplication myApplication = (MyApplication) getApplication();
        mButton.setText("count=" + myApplication.getCount());
    }
}
