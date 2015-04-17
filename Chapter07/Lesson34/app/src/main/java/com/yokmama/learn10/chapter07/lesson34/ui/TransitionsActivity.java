package com.yokmama.learn10.chapter07.lesson34.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.yokmama.learn10.chapter07.lesson34.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kayo on 15/04/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TransitionsActivity extends ActionBarActivity {
    public static final String EXTRA_THEME_ID = "extra.THEME_ID";

    /** 表示するボタンとそれに関連づいたリソース一覧を定義 */
    private final List<ResIdHolder> BTN_LIST = new ArrayList<ResIdHolder>() {
        {
            add(new ResIdHolder(R.id.action_pink, R.color.md_pink_500, R.drawable.fab_pink));
            add(new ResIdHolder(R.id.action_light_green, R.color.md_light_green_500, R.drawable.fab_light_green));
            add(new ResIdHolder(R.id.action_blue, R.color.md_blue_500, R.drawable.fab_blue));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 遷移元から指定されたテーマをセット
        int themeId = getIntent().getIntExtra(EXTRA_THEME_ID, 0);
        setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions);

        for (final ResIdHolder rh : BTN_LIST) {
            View v = findViewById(rh.viewId);
            v.setBackgroundResource(rh.backgroundId);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionsActivity self = TransitionsActivity.this;

                    // 遷移先渡す情報をIntentに詰める
                    Intent intent = new Intent(self, TransitionsAfterActivity.class);
                    intent.putExtra(TransitionsAfterActivity.EXTRA_COLOR_ACCENT_ID, rh.colorResId);
                    intent.putExtra(TransitionsAfterActivity.EXTRA_BACKGROUND_ID, rh.backgroundId);

                    // 遷移先の android:transitionName とマッチする
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            self, v, getString(R.string.transition_name_fab));
                    ActivityCompat.startActivity(self, intent, options.toBundle());
                }
            });
        }
    }

    /** ViewのIDと各種リソースを紐付けるためのクラス */
    private static class ResIdHolder {
        int viewId;
        int colorResId;
        int backgroundId;

        public ResIdHolder(int viewId, int colorResId, int backgroundId) {
            this.viewId = viewId;
            this.colorResId = colorResId;
            this.backgroundId = backgroundId;
        }
    }
}
