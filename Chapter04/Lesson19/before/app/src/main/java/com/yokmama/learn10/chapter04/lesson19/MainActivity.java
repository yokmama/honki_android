package com.yokmama.learn10.chapter04.lesson19;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class MainActivity extends Activity implements View.OnClickListener {

    private LinearLayout mContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LinearLayoutのインスタンスを取得
        mContents = (LinearLayout) findViewById(R.id.contents);

        //リスナーをセット
        findViewById(R.id.addButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //挿入するViewをXMLから取得
        View view = getLayoutInflater().inflate(R.layout.content, mContents, false);

        //LinearLayoutにViewを追加
        mContents.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
