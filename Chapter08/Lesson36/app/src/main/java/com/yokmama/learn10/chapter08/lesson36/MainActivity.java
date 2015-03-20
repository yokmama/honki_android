package com.yokmama.learn10.chapter08.lesson36;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import greendao.Box;
import greendao.BoxDao;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testWrite();
        testRead();
    }

    private void testWrite(){
        ContentValues values = new ContentValues();
        values.put(BoxDao.Properties.Name.columnName, "Name "+System.currentTimeMillis());
        getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
    }

    private void testRead(){
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);
        BoxDao dao = ((MyApplication)getApplication()).getDaoSession().getBoxDao();
        while(cursor.moveToNext()){
            Box box = dao.readEntity(cursor, 0);
            Log.d(TAG, box.getName());

        }
    }
}
