package com.yokmama.learn10.chapter07.lesson34;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by kayo on 15/04/15.
 */
public class MainActivity extends ActionBarActivity {
    private static final String ITEM_OBJECT_ANIMATION = "ObjectAnimation";
    private static final String ITEM_TRANSLATE_ANIMATION = "TranslateAnimation";

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(android.R.id.list);

        // 一覧の作成
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.add(ITEM_OBJECT_ANIMATION);
        adapter.add(ITEM_TRANSLATE_ANIMATION);
        mListView.setAdapter(adapter);

        // アイテム
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);

                // クラスを取得
                final Class<?> activityClass;
                if (ITEM_OBJECT_ANIMATION.equals(item)) {
                    activityClass = ObjectAnimationActivity.class;
                } else if (ITEM_TRANSLATE_ANIMATION.equals(item)) {
                    activityClass = TranslateAnimationActivity.class;
                } else {
                    throw new RuntimeException("No match item.");
                }

                // Intent発行
                Intent intent = new Intent(MainActivity.this, activityClass);
                startActivity(intent);
            }
        });
    }

}
