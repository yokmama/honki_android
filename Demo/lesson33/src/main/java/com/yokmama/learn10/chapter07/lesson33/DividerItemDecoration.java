package com.yokmama.learn10.chapter07.lesson33;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerViewの罫線を描画するデコレータークラス
 *
 * Created by yokmama on 15/05/11.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public DividerItemDecoration(Resources resources) {
        //罫線用の画像を取得
        mDivider = resources.getDrawable(R.drawable.line_divider);
    }

    /***
     * 罫線を描画
     *
     * @param c
     * @param parent
     * @param state
     */
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //描画する罫線のパディングの値を取得
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        //行の個数分罫線を描画
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
