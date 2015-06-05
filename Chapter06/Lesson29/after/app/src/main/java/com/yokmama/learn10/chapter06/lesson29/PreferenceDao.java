package com.yokmama.learn10.chapter06.lesson29;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kayo on 15/04/08.
 */
public class PreferenceDao {

    /** 現在表示中の壁紙の位置 */
    private static final String PREF_WALLPAPER_POSITION = "pref.WALLPAPER_POSITION";

    /** 自動で壁紙を変更することを許可するかどうか。 */
    private static final String PREF_AUTO_WALLPAPER = "pref.AUTO_WALLPAPER";

    /** プリファレンス */
    private final SharedPreferences mPrefs;

    public PreferenceDao(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 壁紙の位置を取得.
     * @return
     */
    public int getWallpaperPosition() {
        return mPrefs.getInt(PREF_WALLPAPER_POSITION, -1);
    }

    /**
     * 壁紙の位置を設定.
     * @param position 位置
     */
    public void putWallpaperPosition(int position) {
        mPrefs.edit().putInt(PREF_WALLPAPER_POSITION, position).apply();
    }

    /**
     * 壁紙変更の有効/無効を確認
     */
    public boolean isAutoWallpaperEnabled() {
        return mPrefs.getBoolean(PREF_AUTO_WALLPAPER, false);
    }

    /**
     * 壁紙変更を有効/無効
     */
    public void setAutoWallpaperEnabled(boolean isChecked) {
        mPrefs.edit().putBoolean(PREF_AUTO_WALLPAPER, isChecked).apply();
    }
}
