package com.yokmama.learn10.chapter05.lesson20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {
    public static final int CALL_RESULT_CODE = 100;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextViewのインスタンスを取得
        mTextView = (TextView) findViewById(R.id.textView);

        //リスナーをセット
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CALL_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //SubActivityから受け取ったテキストを表示
                String text = data.getStringExtra("text");
                mTextView.setText(text);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            //SubActivityを呼び出すIntentを生成
            Intent intent = new Intent(this, SubActivity.class);
            //textというパラメータを設定
            intent.putExtra("text", getString(R.string.hello_world));
            //startActivityで起動
            startActivity(intent);
        } else if (v.getId() == R.id.button2) {
            //SubActivityを呼び出すIntentを生成
            Intent intent = new Intent(this, SubActivity.class);
            //textというパラメータを設定
            intent.putExtra("text", getString(R.string.hello_world));
            //startActivityForResultで起動
            startActivityForResult(intent, CALL_RESULT_CODE);
        }
    }
}
