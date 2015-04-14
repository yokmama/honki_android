package com.yokmama.learn10.chapter06.lesson29;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.yokmama.learn10.chapter06.lesson29.net.RequestDownloadImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 定期的に壁紙を変更するクラス
 * Created by kayo on 15/04/08.
 */
public class WallpaperBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = WallpaperBroadcastReceiver.class.getSimpleName();
    private PreferenceDao mPrefs;
    private Handler mHandler;
    private static Thread sThread;

    @Override
    public void onReceive(final Context context, Intent intent) {
        mPrefs = new PreferenceDao(context);
        mHandler = new Handler(context.getMainLooper());

        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Log.v(TAG, "端末起動完了");

            if (mPrefs.isAutoWallpaperEnabled()) {
                startPolling(context);
            }
        } else {
            Log.v(TAG, "壁紙変更");

            if (sThread == null) {
                sThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.v(TAG, "最適な壁紙を取得");
                            final Bitmap wallpaper = getWallpaper(context);

                            // メインスレッドで実行
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        WallpaperManager wm = WallpaperManager.getInstance(context);
                                        wm.setBitmap(wallpaper);
                                        Log.v(TAG, "壁紙変更");
                                    } catch (IOException e) {
                                        Log.e(TAG, "壁紙の変更に失敗", e);
                                    }
                                }
                            });
                        } catch (IOException e) {
                            Log.e(TAG, "壁紙の取得に失敗", e);
                        }
                        sThread = null;
                    }
                });
                sThread.start();
            }
        }
    }

    private Bitmap getWallpaper(Context context) throws IOException {
        RequestDownloadImage requestDownloadImage = new RequestDownloadImage(context);
        File imageDir = requestDownloadImage.getImageDir();

        // どの画像を表示するのかを選択
        String filePath = nextWallpaperPath(imageDir);

        // 元画像を取得
//        InputStream inputStream = assets.open(filePath);
        InputStream inputStream = new FileInputStream(filePath);
        Bitmap originalWallpaper = BitmapFactory.decodeStream(inputStream);
        inputStream.close();

        // 画面サイズに合わせて縮小した画像を作成
        Bitmap resizedWallpaper = createResizedWallpaper(context, originalWallpaper);

        // 元画像はもう必要ないので破棄
        originalWallpaper.recycle();

        return resizedWallpaper;
    }

    private String nextWallpaperPath(File imageDir) throws IOException {
//        final String ASSETS_WALLPAPERS_DIR_NAME = "wallpapers";
//
        // 画像一覧を取得
//        String[] wallpapers = assets.list(ASSETS_WALLPAPERS_DIR_NAME);
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
//        return ASSETS_WALLPAPERS_DIR_NAME + '/' + wallpaperName;
    }

    private Bitmap createResizedWallpaper(Context context, Bitmap originalWallpaper) {
        // 画面のサイズを取得
        Point displaySize = getDisplaySize(context);

        // 壁紙画像を画面サイズにあわせて縮小する
        float scaleFactor = ((float) displaySize.y) / originalWallpaper.getHeight();
        Matrix scale = new Matrix();
        scale.postScale(scaleFactor, scaleFactor);
        Bitmap resizeBitmap = Bitmap.createBitmap(originalWallpaper,
                0, 0, originalWallpaper.getWidth(), originalWallpaper.getHeight(),
                scale, false);
        return resizeBitmap;
    }

    private Point getDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // ディスプレイのインスタンス生成
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        return size;
    }

    public static Intent createChangeWallpaperIntent(Context context) {
        Intent intentSelf = new Intent(context, WallpaperBroadcastReceiver.class);
        intentSelf.setAction(context.getString(R.string.intent_action_change_wallpaper));

        return intentSelf;
    }

    /** 定期的にこのクラスの {@link #onReceive(android.content.Context, android.content.Intent)} を呼ぶ */
    public static void startPolling(Context context) {
        // 壁紙が切り替わるまでの時間を設定
        final long intervalMillis;
        if (BuildConfig.DEBUG) {
            // デバッグ時は10秒おきに変更
            intervalMillis = 10 * 1000;
        } else {
            // リリース時は6時間おきに変更
            intervalMillis = 6 * 60 * 60 * 1000;
        }
        Intent intent = createChangeWallpaperIntent(context);
        stopPolling(context);

        PendingIntent pIntent = getPendingIntent(context, intent);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long current = SystemClock.elapsedRealtime();
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, current,
                intervalMillis,
                pIntent);

        context.sendBroadcast(intent);
        Log.v(TAG, "壁紙の自動変更を開始");
    }

    /** 定期実行している Intentを停止します。 */
    public static void stopPolling(Context context) {
        Intent intent = createChangeWallpaperIntent(context);
        PendingIntent pIntent = getPendingIntent(context, intent);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        am.cancel(pIntent);
        Log.v(TAG, "壁紙の自動変更を停止");
    }

    private static PendingIntent getPendingIntent(Context context, Intent intent) {
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, 0);
        return pending;
    }
}
