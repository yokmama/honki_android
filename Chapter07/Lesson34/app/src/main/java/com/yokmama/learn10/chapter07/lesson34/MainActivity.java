package com.yokmama.learn10.chapter07.lesson34;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.jar.Attributes;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private GridLayout mGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridLayout = (GridLayout) findViewById(R.id.grid);
//        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.action).setOnClickListener(this);
        findViewById(R.id.btn_transit).setOnClickListener(this);

        int maxChild = mGridLayout.getRowCount() * mGridLayout.getColumnCount();
        for (int i = 0; i < maxChild; i++) {
            Button button = (Button) getLayoutInflater().inflate(R.layout.activity_main_button, mGridLayout, false);
            button.setText(i + "");
            mGridLayout.addView(button);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.action) {
            for (int i = 0, iL = mGridLayout.getChildCount(); i < iL; i++) {
                // 左上を基準として、距離を計算する。
                int vi = i % mGridLayout.getColumnCount();
                int hi = i / mGridLayout.getRowCount();
                int dist = Math.max(vi, hi);

                // childを読み込み
                View childAt = mGridLayout.getChildAt(i);

                // スケールなし状態にセット
                childAt.setScaleX(0.f);
                childAt.setScaleY(0.f);

                // アニメーション読み込み、設定
                Animator anim = AnimatorInflater.loadAnimator(this, R.animator.ht_button);
                anim.setTarget(childAt);
                anim.setStartDelay(dist * 80);
                anim.start();
            }
        } else if (id == R.id.btn_transit) {
//            getWindow().setEnterTransition(new Slide());
            getWindow().setEnterTransition(new Fade());
//            getWindow().setEnterTransition(new Explode());
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this, null).toBundle());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
