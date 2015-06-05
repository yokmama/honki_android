package com.yokmama.learn10.chapter07.lesson33;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yokmama.learn10.chapter07.lesson33.adapter.MyGridAdapter;
import com.yokmama.learn10.chapter07.lesson33.adapter.MyListAdapter;
import com.yokmama.learn10.chapter07.lesson33.adapter.MyStaggeredAdapter;
import com.yokmama.learn10.chapter07.lesson33.item.BaseItem;

import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BaseItem>> {
    private RecyclerView mRecyclerView;
    private RadioGroup mListType;
    private List<BaseItem> mItems;
    private RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson33_activity_main);

        mListType = (RadioGroup) findViewById(R.id.listType);
        mListType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //RecyclerViewにクリック処理とロングクリック処理を設定
        mRecyclerView.addOnItemTouchListener(new ItemClickListener(mRecyclerView) {

            @Override
            boolean performItemClick(RecyclerView parent, View view, int position, long id) {
                BaseItem item = mItems.get(position);
                Toast.makeText(MainActivity.this, "Click " + item.getName(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            boolean performItemLongClick(RecyclerView parent, View view, int position, long id) {
                BaseItem item = mItems.get(position);
                Toast.makeText(MainActivity.this, "Long Click " + item.getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        getSupportLoaderManager().initLoader(0, null, this);
        updateAdapter();
    }

    @Override
    public void onDestroy() {
        getSupportLoaderManager().destroyLoader(0);
        super.onDestroy();
    }


    @Override
    public Loader<List<BaseItem>> onCreateLoader(int id, Bundle args) {
        return new SampleDataGenerator(getApplicationContext(), mListType.getCheckedRadioButtonId());
    }

    @Override
    public void onLoadFinished(Loader<List<BaseItem>> loader, List<BaseItem> data) {
        mItems = data;
        updateAdapter();
    }

    @Override
    public void onLoaderReset(Loader<List<BaseItem>> loader) {

    }

    private void updateAdapter() {
        if (mListType.getCheckedRadioButtonId() == R.id.radioList) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //RecyclerViewに罫線を設定
            mItemDecoration = new DividerItemDecoration(getResources());
            //Dividerが設定されていなければ設定
            if (mItemDecoration == null) {
                mItemDecoration = new DividerItemDecoration(getResources());
                mRecyclerView.addItemDecoration(mItemDecoration);
            }
            //ListViewスタイルのAdapetrを設定
            mRecyclerView.setAdapter(new MyListAdapter(this, mItems));
        } else if (mListType.getCheckedRadioButtonId() == R.id.radioGrid) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            //グリッドの個数返却処理
            ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {
                    int type = mRecyclerView.getAdapter().getItemViewType(i);
                    if (type == R.id.index_type) {
                        return ((GridLayoutManager) mRecyclerView.getLayoutManager()).getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
            //Dividerが設定されていれば解除
            if (mItemDecoration != null) {
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mItemDecoration = null;
            }
            //GridViewスタイルのAdapetrを設定
            mRecyclerView.setAdapter(new MyGridAdapter(this, mItems));
        } else {
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            //Dividerが設定されていれば解除
            if (mItemDecoration != null) {
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mItemDecoration = null;
            }
            //千鳥状スタイルのAdapetrを設定
            mRecyclerView.setAdapter(new MyStaggeredAdapter(this, mItems));
        }

    }
}
