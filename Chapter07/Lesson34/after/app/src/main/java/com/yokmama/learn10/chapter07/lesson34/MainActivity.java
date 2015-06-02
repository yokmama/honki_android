package com.yokmama.learn10.chapter07.lesson34;

import com.yokmama.learn10.chapter07.lesson34.ui.FragmentTransitionsActivity;
import com.yokmama.learn10.chapter07.lesson34.ui.PropertyAnimationActivity;
import com.yokmama.learn10.chapter07.lesson34.ui.TransitionsExplodeActivity;
import com.yokmama.learn10.chapter07.lesson34.ui.TransitionsFadeActivity;
import com.yokmama.learn10.chapter07.lesson34.ui.TransitionsSlideActivity;
import com.yokmama.learn10.chapter07.lesson34.ui.TranslateAnimationActivity;
import com.yokmama.learn10.chapter07.lesson34.ui.TranslateAnimationDialogActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
                        String msg = "Android Lollipop以降でのみ実行可能です。";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else if (item != Activities.ViewAnimation_Activity) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        String msg = "Android Honeycomb以降でのみ実行可能です。";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Intent発行
                Intent intent = new Intent(MainActivity.this, item.activityClass);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this);
                ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
            }
        });
    }

    /**
     * 項目一覧
     */
    public enum Activities {
        PropertyAnimation("Property Animation", PropertyAnimationActivity.class),
        Transitions_Fade("Activity Transitions (fade)", TransitionsFadeActivity.class),
        Transitions_Explode("Activity Transitions (explode)", TransitionsExplodeActivity.class),
        Transitions_Slide("Activity Transitions (slide)", TransitionsSlideActivity.class),
        Transitions_Fragment("Fragment Transitions", FragmentTransitionsActivity.class),
        ViewAnimation_Activity("View Animation (Activity)", TranslateAnimationActivity.class),
        ViewAnimation_Dialog("View Animation (Dialog)", TranslateAnimationDialogActivity.class),
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
