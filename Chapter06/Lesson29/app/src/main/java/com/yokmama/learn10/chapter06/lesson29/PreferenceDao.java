package com.yokmama.learn10.chapter06.lesson29;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kayo on 15/04/08.
 */
public class PreferenceDao {
    private static final String PREF_WALLPAPER_POSITION = "pref.WALLPAPER_POSITION";
    private static final String PREF_AUTO_WALLPAPER = "pref.AUTO_WALLPAPER";
    private final SharedPreferences mPrefs;

    public PreferenceDao(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
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
