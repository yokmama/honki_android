package com.yokmama.learn10.chapter06.lesson29;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.yokmama.learn10.chapter06.lesson29.net.RequestDownloadImage;

import java.io.File;
import java.io.IOException;

/**
 * 定期的に壁紙を変更するクラス
 * Created by kayo on 15/04/08.
 */
public class WallpaperBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_CHANGE_WALLPAPER = "action.CHANGE_WALLPAPER";
    private static final String TAG = WallpaperBroadcastReceiver.class.getSimpleName();

    private PreferenceDao mPrefs;

    @Override
    public void onReceive(final Context context, Intent intent) {
        mPrefs = new PreferenceDao(context);

        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Log.v(TAG, "端末起動完了");

            if (mPrefs.isAutoWallpaperEnabled()) {
                startPolling(context);
            }
        } else if (ACTION_CHANGE_WALLPAPER.equals(action)) {
            try {
                File imageDir = new RequestDownloadImage(context).getImageDir();

                // どの画像を表示するのかを選択
                String filePath = nextWallpaperPath(imageDir);
                if (TextUtils.isEmpty(filePath)) {
                    // 表示する画像がなかった場合
                    Log.i(TAG, "壁紙がまだダウンロードされていない");
                    return;
                }

                // 壁紙の変更を行う
                //TODO:レッスンではここにプログラムを追加

                Log.v(TAG, "壁紙変更完了");
            } catch (IOException e) {
                Log.e(TAG, "壁紙の変更に失敗", e);
            }
        }
    }

    private String nextWallpaperPath(File imageDir) throws IOException {
        // 画像一覧を取得
        String[] wallpapers = imageDir.list();
        if (wallpapers == null || wallpapers.length == 0) {
            // 壁紙が見つからなかった。
            return null;
        }

        // 以前表示していた壁紙の位置を取得
        int oldPosition = mPrefs.getWallpaperPosition();
        final int newPosition;
        if (wallpapers.length > oldPosition + 1) {
            // 配列の範囲内であれば、以前の位置+1を表示する画像とする
            newPosition = oldPosition + 1;
            mPrefs.putWallpaperPosition(newPosition);
        } else {
            // 範囲外であれば0に戻す
            newPosition = 0;
            mPrefs.putWallpaperPosition(newPosition);
        }

        final String wallpaperName = wallpapers[newPosition];
        return new File(imageDir, wallpaperName).getAbsolutePath();
    }

    /** 定期的にこのクラスの {@link #onReceive(android.content.Context, android.content.Intent)} を呼ぶ */
    public static void startPolling(Context context) {
        // 壁紙が切り替わるまでの時間を設定
        final long intervalMillis = 10 * 1000; // テストとして10秒おきに切替え

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intentSelf = createChangeWallpaperIntent(context);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intentSelf, 0);
        am.cancel(pIntent);

        long current = SystemClock.elapsedRealtime();
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, current, intervalMillis, pIntent);

        Log.v(TAG, "壁紙の自動変更を開始");
    }

    public static void cancelPolling(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intentSelf = createChangeWallpaperIntent(context);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intentSelf, 0);
        am.cancel(pIntent);
    }

    public static Intent createChangeWallpaperIntent(Context context) {
        Intent intentSelf = new Intent(context, WallpaperBroadcastReceiver.class);
        intentSelf.setAction(ACTION_CHANGE_WALLPAPER);

        return intentSelf;
    }
}
