package com.yokmama.learn10.chapter05.lesson24;

import android.app.Application;

/**
 * Created by yokmama on 15/03/13.
 */
public class MyApplication extends Application{
    private int mCount;

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }
}
