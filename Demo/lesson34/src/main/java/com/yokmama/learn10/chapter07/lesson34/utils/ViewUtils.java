package com.yokmama.learn10.chapter07.lesson34.utils;

import android.annotation.TargetApi;
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
    public static <T extends View> void callOnLayout(final T v, final OnLayoutCallback<T> callback) {
        ViewTreeObserver.OnGlobalLayoutListener l = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                callback.onLayout(v);
                removeOnGlobalLayoutListener(v.getViewTreeObserver(), this);
            }
        };
        v.getViewTreeObserver().addOnGlobalLayoutListener(l);
    }

    /**
     * 登録したリスナーを削除する
     *
     * @param observer
     * @param listener
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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

    /**
     * コールバックのためのインターフェース
     * @param <T>
     */
    public interface OnLayoutCallback<T> {
        void onLayout(T v);
    }
}
