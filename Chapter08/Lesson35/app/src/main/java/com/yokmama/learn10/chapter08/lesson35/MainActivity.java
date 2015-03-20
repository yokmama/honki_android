package com.yokmama.learn10.chapter08.lesson35;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import greendao.Box;
import greendao.BoxDao;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //testWrite();
        testRead();
    }

    private void testRead(){
        List<Box> list = getBoxDao(this).loadAll();
        for(Box box : list){
            Log.d(TAG, box.getName());
        }
    }

    private void testWrite(){
        Box box = new Box();
        box.setId(5L); // if box with id 5 already exists in DB, it will be edited instead of created
        box.setName("My box");
        box.setSlots(39);
        box.setDescription("This is my box. I can put in it anything I wish.");
        getBoxDao(this).insertOrReplace(box);
    }

    private static BoxDao getBoxDao(Context c) {
        return ((MyApplication) c.getApplicationContext()).getDaoSession().getBoxDao();
    }
}
