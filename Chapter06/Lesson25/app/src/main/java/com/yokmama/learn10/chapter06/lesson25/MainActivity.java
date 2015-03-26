package com.yokmama.learn10.chapter06.lesson25;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = (Toolbar) findViewById(R.id.tool_bar);
        mEditText = (EditText) findViewById(R.id.edit);

        setSupportActionBar(toolBar);

        // キーボードを表示させる
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String keyword = mEditText.getText().toString();
        int itemId = item.getItemId();
        try {
            if (itemId == R.id.action_send) {
                if (checkEmpty(keyword)) return true;

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, keyword);
                startActivity(intent);
            } else if (itemId == R.id.action_google) {
                if (checkEmpty(keyword)) return true;

                // https://www.google.co.jp/search?hl=ja&q=keyword のようなURLを構築する
                Uri uri = Uri.parse("https://www.google.co.jp/search?hl=ja")
                .buildUpon().appendQueryParameter("q", keyword).build();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else if (itemId == R.id.action_store) {
                // Playストア検索
                if (checkEmpty(keyword)) return true;

                Uri uri = Uri.parse("https://play.google.com/store/search")
                        .buildUpon().appendQueryParameter("q", keyword).build();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else {

            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.lb_activity_not_found), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
