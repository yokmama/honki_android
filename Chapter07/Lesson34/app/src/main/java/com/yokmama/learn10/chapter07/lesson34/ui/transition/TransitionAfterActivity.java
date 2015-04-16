package com.yokmama.learn10.chapter07.lesson34.ui.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.yokmama.learn10.chapter07.lesson34.R;

/**
 * Created by kayo on 15/04/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TransitionAfterActivity extends ActionBarActivity {
    public static final String EXTRA_COLOR_ACCENT_ID = "extra.COLOR_ACCENT_ID";
    public static final String EXTRA_BACKGROUND_ID = "extra.BACKGROUND_ID";

    private Toolbar mToolbar;
    private Button mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_after);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (Button) findViewById(R.id.action);

        setSupportActionBar(mToolbar);

        mToolbar.setBackgroundColor(getResources().getColor(getIntent().getIntExtra(EXTRA_COLOR_ACCENT_ID, 0)));
        mFab.setBackgroundResource(getIntent().getIntExtra(EXTRA_BACKGROUND_ID, 0));

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Reveal Effect を実施
        int centerX = mToolbar.getWidth() / 2;
        int centerY = mToolbar.getHeight() / 2;
        Animator animator = ViewAnimationUtils.createCircularReveal(
                mToolbar,
                centerX, centerY,
                0,
                (float) Math.hypot(centerX, centerY));

        // 長さ
        Interpolator interpolator = AnimationUtils.loadInterpolator(this,
                android.R.interpolator.accelerate_cubic);
        animator.setInterpolator(interpolator);

        // アニメーション開始
        animator.start();
    }
}
