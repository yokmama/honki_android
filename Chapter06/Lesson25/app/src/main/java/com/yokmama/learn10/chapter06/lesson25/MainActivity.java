package com.yokmama.learn10.chapter06.lesson25;

<<<<<<< HEAD
import android.content.Intent;
import android.net.Uri;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
=======
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
>>>>>>> e4fd72a43cc7b855ec9230750fefc3f8b9606d1b


public class MainActivity extends ActionBarActivity {

<<<<<<< HEAD
    private EditText mEditText;
    private ListView mListView;

=======
>>>>>>> e4fd72a43cc7b855ec9230750fefc3f8b9606d1b
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        mEditText = (EditText) findViewById(R.id.edit);
        mListView = (ListView) findViewById(android.R.id.list);

        // キーボードを表示させる
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        // 機能一覧を作成する
        String[] list = getResources().getStringArray(R.array.list_search);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter adapter = (ArrayAdapter) parent.getAdapter();
                String item = (String) adapter.getItem(position);
                selectItem(item);
            }
        });
    }

    /**
     * アイテムが選択された時に呼ばれます。
     *
     * @param title タイトル選択
     */
    private void selectItem(String title) {
        String keyword = mEditText.getText().toString();

        if (!canSearchable(title, keyword)) {
            return;
        }

        // 各種機能で開く
        if (TextUtils.equals(title, getString(R.string.list_search_item_google))) {
            // https://www.google.co.jp/search?q=android&hl=ja のようなURLを構築する
            Uri uri = Uri.parse("https://www.google.co.jp/search?hl=ja")
                    .buildUpon().appendQueryParameter("q", keyword).build();
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        } else if (TextUtils.equals(title, getString(R.string.list_search_item_youtube))) {
            Intent intent = new Intent(Intent.ACTION_SEARCH);
            intent.setPackage("com.google.android.youtube");
            intent.putExtra("query", keyword);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (TextUtils.equals(title, getString(R.string.list_search_item_play_store))) {
            Uri uri = Uri.parse("https://play.google.com/store/search")
                    .buildUpon().appendQueryParameter("q", keyword).build();
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        } else if (TextUtils.equals(title, getString(R.string.list_search_item_alarm))) {
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, keyword);
            // 時間を予め適当に指定
            intent.putExtra(AlarmClock.EXTRA_HOUR, 8); // 時
            intent.putExtra(AlarmClock.EXTRA_MINUTES, 5); // 分
            startActivity(intent);

        } else if (TextUtils.equals(title, getString(R.string.list_search_item_misc))) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, keyword);
            startActivity(intent);
        }
    }

    /**
     * 検索可能かどうか調べる
     *
     * @param title   クリックした項目のタイトル
     * @param keyword キーワード
     * @return 検索可能状態なら true
     */
    private boolean canSearchable(String title, String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(this, getString(R.string.lb_please_input_keyword), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
=======
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
>>>>>>> e4fd72a43cc7b855ec9230750fefc3f8b9606d1b
    }
}
