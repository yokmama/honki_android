package com.yokmama.learn10.chapter06.lesson25;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText mEditText;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mEditText = (EditText) findViewById(R.id.edit);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);

        setSupportActionBar(mToolbar);

        // キーボードを表示させる
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onClick(View v) {
        String keyword = mEditText.getText().toString();

        int id = v.getId();
        if (id == R.id.button1) {
            // Google検索
            if (checkEmpty(keyword)) return;

            // https://www.google.co.jp/search?hl=ja&q=keyword のようなURLを構築する
            Uri uri = Uri.parse("https://www.google.co.jp/search?hl=ja")
                    .buildUpon().appendQueryParameter("q", keyword).build();
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        } else if (id == R.id.button2) {
            // Youtube検索
            if (checkEmpty(keyword)) return;

            Intent intent = new Intent(Intent.ACTION_SEARCH);
            intent.setPackage("com.google.android.youtube");
            intent.putExtra("query", keyword);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (id == R.id.button3) {
            // Playストア検索
            if (checkEmpty(keyword)) return;

            Uri uri = Uri.parse("https://play.google.com/store/search")
                    .buildUpon().appendQueryParameter("q", keyword).build();
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        } else if (id == R.id.button4) {
            // アラーム設定
            if (checkEmpty(keyword)) return;

            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, keyword);
            // 時間を予め適当に指定
            intent.putExtra(AlarmClock.EXTRA_HOUR, 8); // 時
            intent.putExtra(AlarmClock.EXTRA_MINUTES, 5); // 分
            startActivity(intent);

        } else if (id == R.id.button5) {
            // その他のアプリへ
            if (checkEmpty(keyword)) return;

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, keyword);
            startActivity(intent);

        } else {
            throw new RuntimeException("no match btnId");
        }
    }

    /**
     * キーワードが空(null、または空文字列)かどうかを調べる
     *
     * @param keyword キーワード
     * @return キーワードが空なら true
     */
    private boolean checkEmpty(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(this, getString(R.string.lb_please_input_keyword), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
