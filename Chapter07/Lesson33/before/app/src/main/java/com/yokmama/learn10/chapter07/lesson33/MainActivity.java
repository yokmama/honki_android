package com.yokmama.learn10.chapter07.lesson33;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BaseItem>> {
    private ListView mListView;
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

        mListView = (ListView)findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseItem item = mItems.get(position);
                Toast.makeText(MainActivity.this, "Click " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BaseItem item = mItems.get(position);
                Toast.makeText(MainActivity.this, "Long Click " + item.getName(), Toast.LENGTH_SHORT).show();
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
        updateAdapter();
    }

    private void updateAdapter(){
        MyAdapter adapter = new MyAdapter(this);
        adapter.setItems(mItems);
        mListView.setAdapter(adapter);
    }
}
