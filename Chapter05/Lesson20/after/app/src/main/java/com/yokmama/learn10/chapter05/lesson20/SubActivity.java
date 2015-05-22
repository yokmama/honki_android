package com.yokmama.learn10.chapter05.lesson20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class SubActivity extends Activity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //EditTextのインスタンスを取得
        mEditText = (EditText) findViewById(R.id.editText);

        //パラメータを取得
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("text")) {
            mEditText.setText(intent.getStringExtra("text"));
        }

        //リスナーをセット
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditTextに入力されているテキストをMainActivityに渡す
                Intent data = new Intent();
                data.putExtra("text", mEditText.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
