package com.yokmama.learn10.chapter06.lesson29;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kayo on 15/04/08.
 */
public class PreferenceDao {
    /** キーワード */
    private static final String PREF_KEYWORD = "pref.KEYWORD";
    /** 現在表示中の壁紙の位置 */
    private static final String PREF_WALLPAPER_POSITION = "pref.WALLPAPER_POSITION";
    /** 自動で壁紙を変更することを許可するかどうか。 */
    private static final String PREF_AUTO_WALLPAPER = "pref.AUTO_WALLPAPER";
    private final SharedPreferences mPrefs;

    public PreferenceDao(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getKeyword() {
        String data = mPrefs.getString(PREF_KEYWORD, "");
        return data;
    }

    public void putKeyword(String keyword) {
        mPrefs.edit().putString(PREF_KEYWORD, keyword).apply();
    }

    public int getWallpaperPosition() {
        int data = mPrefs.getInt(PREF_WALLPAPER_POSITION, -1);
        return data;
    }

    public void putWallpaperPosition(int position) {
        mPrefs.edit().putInt(PREF_WALLPAPER_POSITION, position).apply();
    }

    public boolean isAutoWallpaperEnabled() {
        boolean data = mPrefs.getBoolean(PREF_AUTO_WALLPAPER, false);
        return data;
    }

    public void setAutoWallpaperEnabled(Context context, boolean isChecked) {
        mPrefs.edit().putBoolean(PREF_AUTO_WALLPAPER, isChecked).apply();
    }
}
