package com.yokmama.learn10.chapter07.lesson33;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yokmama on 15/03/10.
 */
public class SampleDataGenerator extends AsyncTaskLoader<List<BaseItem>> {
    public SampleDataGenerator(Context context) {
        super(context);
    }

    @Override
    public List<BaseItem> loadInBackground() {

        List<BaseItem> list = new ArrayList<>();

        String name;
        for (int i = 0; i < 17; i++) {
            name = "cat" + (i + 1);
            int id = getContext().getResources().getIdentifier(name, "drawable", BuildConfig.APPLICATION_ID);
            ImageItem item = new ImageItem();
            item.setId(id);
            item.setName(name);
            list.add(item);
        }

        return list;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
