package com.yokmama.learn10.chapter07.lesson34;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yokmama.learn10.chapter07.lesson34.ui.FragmentTransitionsActivity;
import com.yokmama.learn10.chapter07.lesson34.ui.PropertyAnimationActivity;
import com.yokmama.learn10.chapter07.lesson34.ui.TransitionsActivity;
import com.yokmama.learn10.chapter07.lesson34.ui.TranslateAnimationActivity;

/**
 * Created by kayo on 15/04/15.
 */
public class MainActivity extends Activity {

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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 選択した項目の情報を取り出す
                Activities item = adapter.getItem(position);

                // エラーチェック
                if (item.name().startsWith("Transition")) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        String msg = "Android Lolipop以降でのみ実行可能です。";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else if (item == Activities.PropertyAnimation) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        String msg = "Android Honeycomb以降でのみ実行可能です。";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Intent発行
                Intent intent = new Intent(MainActivity.this, item.activityClass);
                if (item == Activities.Transitions_Fade) {
                    intent.putExtra(TransitionsActivity.EXTRA_THEME_ID, R.style.Lesson34_Transition_Fade);
                } else if (item == Activities.Transitions_Explode) {
                    intent.putExtra(TransitionsActivity.EXTRA_THEME_ID, R.style.Lesson34_Transition_Explode);
                } else if (item == Activities.Transitions_Slide) {
                    intent.putExtra(TransitionsActivity.EXTRA_THEME_ID, R.style.Lesson34_Transition_Slide);
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 項目一覧
     */
    public enum Activities {
        PropertyAnimation("Property Animation", PropertyAnimationActivity.class),
        TranslateAnimation("Translate Animation", TranslateAnimationActivity.class),
        Transitions_Fade("Activity Transitions (fade)", TransitionsActivity.class),
        Transitions_Explode("Activity Transitions (explode)", TransitionsActivity.class),
        Transitions_Slide("Activity Transitions (slide)", TransitionsActivity.class),
        Transitions_Fragment("Fragment Transitions", FragmentTransitionsActivity.class),
        ;
        private final String title;
        private final Class<? extends Activity> activityClass;

        Activities(String title, Class<? extends Activity> activityClass) {
            this.title = title;
            this.activityClass = activityClass;
        }

        @Override
        public String toString() {
            return this.title;
        }
    }

}
