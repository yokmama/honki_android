package com.yokmama.learn10.chapter07.lesson34;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yokmama.learn10.chapter07.lesson34.ui.transition.TransitionActivity;

/**
 * Created by kayo on 15/04/15.
 */
public class MainActivity extends ActionBarActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(android.R.id.list);

        // 一覧の作成
        final ArrayAdapter<Activities> adapter = new ArrayAdapter<Activities>(
                this, android.R.layout.simple_list_item_1, Activities.values());
        mListView.setAdapter(adapter);

        // アイテム
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activities item = adapter.getItem(position);

                // エラーチェック
                if (Activities.Transition.equals(item)) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        String msg = "Android Lolipop以降でのみ実行可能な項目です。";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Intent発行
                Intent intent = new Intent(MainActivity.this, item.activityClass);
                startActivity(intent);
            }
        });
    }

    public enum Activities {
        PropertyAnimation(PropertyAnimationActivity.class),
        TranslateAnimation(TranslateAnimationActivity.class),
        Transition(TransitionActivity.class),
        ;
        private final Class<? extends Activity> activityClass;

        Activities(Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

}
