package com.yokmama.learn10.chapter08.lesson36;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import greendao.BoxDao;
import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by yokmama on 15/03/19.
 */
public class MyContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://com.yokmama.learn10.chapter08.lesson36/box");
    private static final int BOX = 1;
    private static final int BOX_ID = 2;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(CONTENT_URI.getAuthority(), "box", BOX);
        matcher.addURI(CONTENT_URI.getAuthority(), "box/#", BOX_ID);
    }

    private SQLiteDatabase mDatabase;


    @Override
    public boolean onCreate() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "example-db", null);
        mDatabase = helper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int type = matcher.match(uri);
        switch (type) {
            case BOX:
                return mDatabase.query(BoxDao.TABLENAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
            case BOX_ID:
                return mDatabase.query(BoxDao.TABLENAME, projection, "_ID = ?",
                        new String[]{uri.getLastPathSegment()}, null, null, sortOrder);
            default:
                return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mDatabase.insert(BoxDao.TABLENAME, null, values);

        if (id >= 0) {
            return Uri.withAppendedPath(uri, String.valueOf(id));
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int type = matcher.match(uri);
        switch (type) {
            case BOX:
                return mDatabase.delete(BoxDao.TABLENAME, selection, selectionArgs);

            case BOX_ID:
                return mDatabase.delete(BoxDao.TABLENAME, "_ID = ?",
                        new String[]{uri.getLastPathSegment()});

            default:
                return 0;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int result = matcher.match(uri);
        switch (result) {
            case BOX:
                return mDatabase.update(BoxDao.TABLENAME, values, selection, selectionArgs);

            case BOX_ID:
                return mDatabase.update(BoxDao.TABLENAME, values, "_ID = ?",
                        new String[]{uri.getLastPathSegment()});

            default:
                return 0;
        }
    }
}
