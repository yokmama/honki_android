package com.yokmama.learn10.chapter05.lesson25;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttonのリスナーを設定
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyApplicationを取得しCountUpを実行
                MyApplication myApplication = (MyApplication) getApplication();
                myApplication.countUp();
            }
        });
    }

}
