package com.yokmama.learn10.chapter07.lesson33;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.yokmama.learn10.chapter07.lesson33.item.BaseItem;
import com.yokmama.learn10.chapter07.lesson33.item.BigImageItem;
import com.yokmama.learn10.chapter07.lesson33.item.ImageItem;
import com.yokmama.learn10.chapter07.lesson33.item.IndexItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yokmama on 15/03/10.
 */
public class SampleDataGenerator extends AsyncTaskLoader<List<BaseItem>> {
    private int mViewStyle;

    public SampleDataGenerator(Context context, int viewStyle) {
        super(context);
        mViewStyle = viewStyle;
    }

    @Override
    public List<BaseItem> loadInBackground() {

        List<BaseItem> list = new ArrayList<>();

        String name;
        for (int i = 0; i < 21; i++) {
            name = "dog" + (i + 1);
            int id = getContext().getResources().getIdentifier(name, "drawable", "com.yokmama.learn10.demo");
            if (mViewStyle == R.id.radioList || mViewStyle == R.id.radioGrid) {
                //５個単位で目次を生成
                if (i % 3 == 0) {
                    IndexItem item = new IndexItem();
                    item.setName("Index Title[" + i + "]");
                    list.add(item);
                }
                ImageItem item = new ImageItem();
                item.setId(id);
                item.setName(name);
                list.add(item);
            } else if (mViewStyle == R.id.radioStaggered) {
                //３個ずつ並べるうち、一つ目を大きい画像に設定
                if (i % 3 == 0) {
                    BigImageItem item = new BigImageItem();
                    item.setId(id);
                    item.setName(name);
                    list.add(item);
                } else {
                    ImageItem item = new ImageItem();
                    item.setId(id);
                    item.setName(name);
                    list.add(item);
                }
            } else{
                ImageItem item = new ImageItem();
                item.setId(id);
                item.setName(name);
                list.add(item);
            }
        }

        return list;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
