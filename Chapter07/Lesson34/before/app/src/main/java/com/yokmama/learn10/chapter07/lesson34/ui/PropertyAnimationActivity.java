package com.yokmama.learn10.chapter07.lesson34.ui;

import com.yokmama.learn10.chapter07.lesson34.R;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class PropertyAnimationActivity extends Activity implements View.OnClickListener {

    private ImageButton mBtnXXml;
    private ImageButton mBtnXCode;
    private GridLayout mGridLayout;

    private boolean isShowingGridLayout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);

        mBtnXXml = (ImageButton) findViewById(R.id.btn_x_xml);
        mBtnXCode = (ImageButton) findViewById(R.id.btn_x_code);
        mGridLayout = (GridLayout) findViewById(R.id.grid);

        findViewById(R.id.btn_grid).setOnClickListener(this);
        mBtnXXml.setOnClickListener(this);
        mBtnXCode.setOnClickListener(this);

        // GridLayoutにボタンを複数追加
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
        if (id == R.id.btn_x_xml) {
            doAnimationToBtnXXml();
        } else if (id == R.id.btn_x_code) {
            doAnimationToBtnXCode();
        } else if (id == R.id.btn_grid) {
            doAnimationToGridLayout();
        }
    }

    /** GridLayout のアニメーション */
    private void doAnimationToGridLayout() {
        for (int i = 0, iL = mGridLayout.getChildCount(); i < iL; i++) {
            // 左上を基準として、距離を計算する。
            int vi = i % mGridLayout.getColumnCount();
            int hi = i / mGridLayout.getRowCount();
            int dist = vi + hi;

            // childを読み込み
            View childAt = mGridLayout.getChildAt(i);

            // アニメーション読み込み、設定
        }
        isShowingGridLayout = !isShowingGridLayout;
    }

    /** XMLで定義したプロパティアニメーションを実行 */
    private void doAnimationToBtnXXml() {
        Animator anim = AnimatorInflater.loadAnimator(
                this,R.animator.activity_property_animation_move_x);
        anim.setTarget(mBtnXXml);
        anim.start();
    }

    /** プログラムで定義したプロパティアニメーションを実行 */
    private void doAnimationToBtnXCode() {
    }
}