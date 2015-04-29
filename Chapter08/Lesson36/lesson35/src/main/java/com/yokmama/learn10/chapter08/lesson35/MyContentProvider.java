package com.yokmama.learn10.chapter08.lesson35;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import greendao.DaoMaster;
import greendao.MemoDao;

/**
 * Created by yokmama on 15/03/19.
 */
public class MyContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://com.yokmama.learn10.chapter08.lesson35/memo");
    private static final int MEMO = 1;
    private static final int MEMO_ID = 2;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(CONTENT_URI.getAuthority(), "memo", MEMO);
        matcher.addURI(CONTENT_URI.getAuthority(), "memo/#", MEMO_ID);
    }

    private SQLiteDatabase mDatabase;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int type = matcher.match(uri);
        switch (type) {
            case MEMO:
                return mDatabase.delete(MemoDao.TABLENAME, selection, selectionArgs);

            case MEMO_ID:
                return mDatabase.delete(MemoDao.TABLENAME, "_ID = ?",
                        new String[]{uri.getLastPathSegment()});

            default:
                return 0;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mDatabase.insert(MemoDao.TABLENAME, null, values);

        if (id >= 0) {
            return Uri.withAppendedPath(uri, String.valueOf(id));
        } else {
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "example-db", null);
        mDatabase = helper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int type = matcher.match(uri);
        switch (type) {
            case MEMO:
                return mDatabase.query(MemoDao.TABLENAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
            case MEMO_ID:
                return mDatabase.query(MemoDao.TABLENAME, projection, "_ID = ?",
                        new String[]{uri.getLastPathSegment()}, null, null, sortOrder);
            default:
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int result = matcher.match(uri);
        switch (result) {
            case MEMO:
                return mDatabase.update(MemoDao.TABLENAME, values, selection, selectionArgs);

            case MEMO_ID:
                return mDatabase.update(MemoDao.TABLENAME, values, "_ID = ?",
                        new String[]{uri.getLastPathSegment()});

            default:
                return 0;
        }
    }

}
