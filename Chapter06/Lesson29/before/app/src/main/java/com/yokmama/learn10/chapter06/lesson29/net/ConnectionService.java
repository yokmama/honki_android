package com.yokmama.learn10.chapter06.lesson29.net;

import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.yokmama.learn10.chapter06.lesson29.WallpaperBroadcastReceiver;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * バックグラウンドで通信を行うサービス
 * Created by kayo on 15/04/13.
 */
public class ConnectionService extends IntentService {
    private static final String TAG = ConnectionService.class.getSimpleName();

    public static final String ACTION_START = "extra.START";
    public static final String ACTION_STOP = "extra.STOP";
    public static final String EXTRA_SEARCH_KEYWORD = "extra.SEARCH_KEYWORD";
    public static final String EXTRA_IMAGE_URL = "extra.IMAGE_URL";
    private RequestGoogleCustomSearchApi mApi;
    private RequestDownloadImage mRequestDownloadImage;

    public ConnectionService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Google APIにアクセスするためのクラス呼び出し
        mApi = new RequestGoogleCustomSearchApi(this);
        // 画像ダウンロードのためのクラス呼び出し
        mRequestDownloadImage = new RequestDownloadImage(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //TODO:レッスンではここにプログラムを追加
    }

    /**
     * 壁紙の検索を開始.
     * @param keyword
     */
    private void startSearch(String keyword) {
        Log.v(TAG, "Start search: keyword=" + keyword);
        final List<CustomSearchApiItem> items;
        try {
            items = mApi.reqCustomSearchApiSync(keyword);
        } catch (MalformedURLException e) {
            Log.e(TAG, "URLの形式が不正です。", e);
            return;
        } catch (JSONException e) {
            Log.e(TAG, "JSONのパースに失敗", e);
            return;
        } catch (IOException e) {
            Log.e(TAG, "通信エラー", e);
            return;
        }

        // 既存の画像(別のキーワードで検索した時の一覧)を削除
        File[] files = mRequestDownloadImage.getImageDir().listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

        // ダウンロードする画像一覧をキューに詰める
        //TODO:レッスンではここにプログラムを追加
    }

    /**
     * 画像のダウンロードを開始.
     * @param url URL
     */
    private void startDownloadImage(String url) {
        Log.v(TAG, "ダウンロード開始: url=" + url);

        try {
            // 画像を取得
            File file = mRequestDownloadImage.reqDownloadImageSync(url);

            // 元画像はディスプレイに合っていない場合があるので、
            // 任意でリサイズ処理を挟む。
            Log.v(TAG, "ダウンロード終了: file=" + file);
        } catch (IOException e) {
            Log.i(TAG, "ダウンロード失敗", e);
        }
    }
}