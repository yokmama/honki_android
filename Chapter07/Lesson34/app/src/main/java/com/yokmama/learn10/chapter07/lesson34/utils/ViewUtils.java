package com.yokmama.learn10.chapter07.lesson34.utils;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by kayo on 15/04/17.
 */
public class ViewUtils {

    /**
     * 指定したビューのサイズが計算出来たタイミングでコールバックを呼び出す
     *
     * @param v        ビュー
     * @param callback ビューがレイアウトされた時に呼び出される
     */
    public static void callOnLayout(final View v, final OnLayoutCallback callback) {
        ViewTreeObserver.OnGlobalLayoutListener l = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                callback.onLayout(v);
                removeOnGlobalLayoutListener(v.getViewTreeObserver(), this);
            }
        };
        v.getViewTreeObserver().addOnGlobalLayoutListener(l);
    }

    @SuppressWarnings("deprecation")
    private static void removeOnGlobalLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (observer == null) {
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            observer.removeGlobalOnLayoutListener(listener);
        } else {
            observer.removeOnGlobalLayoutListener(listener);
        }
    }

    public interface OnLayoutCallback {
        void onLayout(View v);
    }
}
