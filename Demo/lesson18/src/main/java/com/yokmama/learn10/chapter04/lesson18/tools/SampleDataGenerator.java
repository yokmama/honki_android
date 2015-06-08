package com.yokmama.learn10.chapter04.lesson18.tools;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.AsyncTaskLoader;

import com.yokmama.learn10.chapter04.lesson18.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yokmama on 15/03/10.
 */
public class SampleDataGenerator extends AsyncTaskLoader<List<ListItem>> {
    public enum DataType{
        Text,
        Image
    }

    private DataType mType;

    public SampleDataGenerator(Context context, DataType type) {
        super(context);
        mType = type;
    }

    @Override
    public List<ListItem> loadInBackground() {

        List<ListItem> list = new ArrayList<>();

        if(mType == DataType.Text){
            try {
                load("names.txt", list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            load(list);
        }

        return list;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    private void load(List<ListItem> col) {
        String name;
        for(int i=0; i<21;i++) {
            name = "dog"+(i+1);
            int id = getContext().getResources().getIdentifier(name, "drawable", "com.yokmama.learn10.demo");
            ListItem item = new ListItem();
            item.setId(id);
            item.setName(name);
            col.add(item);
        }
    }

    private void load(String name, List<ListItem> col) throws IOException {
        AssetManager am = getContext().getAssets();
        BufferedReader r = new BufferedReader(new InputStreamReader(am.open(name), "UTF-8"));
        String line;
        while ((line = r.readLine()) != null) {
            ListItem item = new ListItem();
            item.setName(line);
            col.add(item);
        }
    }
}
