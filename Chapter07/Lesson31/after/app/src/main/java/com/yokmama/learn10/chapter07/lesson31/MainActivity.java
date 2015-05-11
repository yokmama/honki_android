package com.yokmama.learn10.chapter07.lesson31;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ScrollView mScrollView;
    private Toolbar mToolbar;
    private float mToolbarBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScrollView = (ScrollView) findViewById(R.id.scrollView);

        //Toolbarのインスタンスを取得
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //ToolbarをActionbarとして設定
        setSupportActionBar(mToolbar);

        //Toolbarのアイコンを設定
        mToolbar.setLogo(R.mipmap.ic_launcher);
        //Toolbarのタイトルを設定
        mToolbar.setTitle("タイトル名");
        //Toolbarのサブタイトルを設定
        mToolbar.setSubtitle("サブタイトル名");

        final TypedArray mTypedArray = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        mToolbarBarHeight = mTypedArray.getDimension(0, 0);
        mTypedArray.recycle();

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener);

        //検索による画面呼び出しかどうか判定
        if (getIntent() != null) {
            if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
                //検索なので検索処理を実施
                doSearch(getIntent());
                return;
            }
        }

        //画面のリストを作成
        updateList(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        //SearchViewがMenuにあるかどうか判定し、あるならSearchViewの設定を行う
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {
                //システムからSearchManagerを取得
                SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

                //SearchViewに検索情報を設定
                searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
                //SearchViewのクローズ時の処理を設定
                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        updateList(null);
                        return false;
                    }
                });
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        doSearch(intent);
    }

    /**
     * 検索処理
     *
     * @param intent
     */
    private void doSearch(Intent intent) {
        String queryString = intent.getStringExtra(SearchManager.QUERY);
        Log.d(TAG, "search value:" + queryString);
        //画面のリストを作成
        updateList(queryString);
    }

    /**
     * パラメータに一致する画像、あるいはNULLの場合は全ての画像を一覧に追加
     *
     * @param searchName
     */
    private void updateList(String searchName) {
        Log.d(TAG, "updateList value:" + searchName);

        LinearLayout contents = (LinearLayout) findViewById(R.id.contents);
        //一旦全てのアイテムを削除
        contents.removeAllViews();
        //ヘッダー用の空白を追加
        View brankHeader = new View(this);
        brankHeader.setMinimumHeight((int) mToolbarBarHeight);
        contents.addView(brankHeader, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        //17個の画像をScrollViewのItemとして追加
        for (int i = 1; i <= 17; i++) {
            String resName = "cat" + i;
            if (TextUtils.isEmpty(searchName) || resName.contains(searchName)) {
                View view = getLayoutInflater().inflate(R.layout.scroll_item, contents, false);
                ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                TextView textView = (TextView) view.findViewById(R.id.textView);

                //名前をTextViewに設定
                textView.setText(resName);

                //文字列からリソースのIDを取得
                int imageId = getResources().getIdentifier(resName, "drawable", getPackageName());
                //画像をImageViewに設定
                imageView.setImageResource(imageId);
                contents.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    /**
     * ScrollViewのスクロール変化時の処理
     */
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
        private boolean isHide = false;

        @Override
        public void onScrollChanged() {
            float y = mScrollView.getScrollY();
            if (y >= mToolbarBarHeight && !isHide) {
                isHide = true;
                mToolbar.animate()
                        .translationY(-mToolbar.getBottom())
                        .setInterpolator(new DecelerateInterpolator());
            } else if (y == 0 && isHide) {
                isHide = false;
                mToolbar.animate().translationY(0);
            }
        }
    };
}
