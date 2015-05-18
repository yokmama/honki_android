package com.yokmama.learn10.chapter07.lesson34.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.Button;

import com.yokmama.learn10.chapter07.lesson34.R;
import com.yokmama.learn10.chapter07.lesson34.ui.TransitionsAfterActivity;

/**
 * Created by kayo on 15/04/15.
 */
public abstract class TransitionsBaseActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions_base);

        Button btnPink = (Button) findViewById(R.id.action_pink);
        Button btnLightGreen = (Button) findViewById(R.id.action_light_green);
        Button btnBlue = (Button) findViewById(R.id.action_blue);

        btnPink.setBackgroundResource(R.drawable.fab_pink);
        btnLightGreen.setBackgroundResource(R.drawable.fab_light_green);
        btnBlue.setBackgroundResource(R.drawable.fab_blue);

        btnPink.setOnClickListener(this);
        btnLightGreen.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 各種ボタンを押下した時に実行される
        Button vButton = (Button) v;
        int id = v.getId();
        if (id == R.id.action_pink) {
            onClickCircleButton(vButton, R.color.md_pink_500, R.color.md_pink_700, R.drawable.fab_pink);
        } else if (id == R.id.action_light_green) {
            onClickCircleButton(vButton, R.color.md_light_green_500, R.color.md_light_green_700, R.drawable.fab_light_green);
        } else if (id == R.id.action_blue) {
            onClickCircleButton(vButton, R.color.md_blue_500, R.color.md_blue_700, R.drawable.fab_blue);
        } else {
            // いずれにも、ボタン押下
            throw new RuntimeException("No match btnId");
        }
    }

    protected void onClickCircleButton(@IdRes Button buttonView, @ColorRes int colorPrimaryResId, @ColorRes int colorPrimaryDarkResId, @DrawableRes int backgroundResId) {
        // 遷移先渡す情報をIntentに詰める
        Intent intent = new Intent(this, TransitionsAfterActivity.class);
        intent.putExtra(TransitionsAfterActivity.EXTRA_COLOR_PRIMARY_ID, colorPrimaryResId);
        intent.putExtra(TransitionsAfterActivity.EXTRA_COLOR_PRIMARY_DARK_ID, colorPrimaryDarkResId);
        intent.putExtra(TransitionsAfterActivity.EXTRA_BACKGROUND_ID, backgroundResId);

        // 遷移先の android:transitionName とマッチする
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, buttonView, getString(R.string.transition_name_fab));
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

}
