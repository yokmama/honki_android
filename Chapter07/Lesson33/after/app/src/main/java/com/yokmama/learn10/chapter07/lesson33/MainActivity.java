package com.yokmama.learn10.chapter07.lesson33;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BaseItem>> {
    private RecyclerView mRecyclerView;
    private List<BaseItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup listType = (RadioGroup) findViewById(R.id.listType);
        listType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateListType(R.id.radioList == checkedId);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.addOnItemTouchListener(new ItemClickListener(mRecyclerView) {

            @Override
            boolean performItemClick(RecyclerView parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Click " + position, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            boolean performItemLongClick(RecyclerView parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Long Click " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        updateListType(true);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onDestroy() {
        getSupportLoaderManager().destroyLoader(0);
        super.onDestroy();
    }


    @Override
    public Loader<List<BaseItem>> onCreateLoader(int id, Bundle args) {
        return new SampleDataGenerator(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<BaseItem>> loader, List<BaseItem> data) {
        mItems = data;
        updateAdapter();
    }

    @Override
    public void onLoaderReset(Loader<List<BaseItem>> loader) {

    }

    private void updateListType(boolean isList) {
        if (isList) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            //グリッド幅の自動計算
            mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            mRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            int viewWidth = mRecyclerView.getMeasuredWidth();
                            float cardViewWidth = getResources().getDimension(R.dimen.grid_width);
                            int newSpanCount = (int) Math.ceil(viewWidth / cardViewWidth);
                            ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanCount(newSpanCount);
                        }
                    }
            );
            //グリッドの個数返却処理
            ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {
                    int type = mRecyclerView.getAdapter().getItemViewType(i);
                    if (type == 0) {
                        return ((GridLayoutManager) mRecyclerView.getLayoutManager()).getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }

        updateAdapter();
    }

    private void updateAdapter() {
        if (mItems != null) {
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                MyGridAdapter adapter = new MyGridAdapter(this);
                adapter.setItems(mItems);
                mRecyclerView.setAdapter(adapter);
            } else {
                MyListAdapter adapter = new MyListAdapter(this);
                adapter.setItems(mItems);
                mRecyclerView.setAdapter(adapter);
            }
        }
    }

}
