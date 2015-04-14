package com.yokmama.learn10.chapter06.lesson29.net;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.yokmama.learn10.chapter06.lesson29.storage.FileUtils;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * バックグラウンドで通信を行うサービス
 *
 * Created by kayo on 15/04/13.
 */
public class ConnectionService extends IntentService {
    private static final String TAG = ConnectionService.class.getSimpleName();

    public static final String EXTRA_SEARCH_KEYWORD = "extra.SEARCH_KEYWORD";
    public static final String EXTRA_IMAGE_URL = "extra.IMAGE_URL";
    private RequestGoogleCustomSearchApi mApi;
    private RequestDownloadImage mRequestDownloadImage;

    public ConnectionService() {
        super("ConnectionThread");
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
        if (intent == null) {
            Log.i(TAG, "onHandleIntent");
            return;
        }

        // 画像のダウンロード
        String url = intent.getStringExtra(EXTRA_IMAGE_URL);
        if (!TextUtils.isEmpty(url)) {
            startDownloadImage(url);
            return;
        }

        // キーワードで検索
        String keyword = intent.getStringExtra(EXTRA_SEARCH_KEYWORD);
        if (!TextUtils.isEmpty(keyword)) {
            startSearch(keyword);
            return;
        }

        Log.i(TAG, "onHandleIntent");
    }

    private void startSearch(String keyword) {
        Log.v(TAG, "Start search: keyword=" + keyword);
        final List<CustomSearchApiItem> items;
        try {
            FileUtils.delete(mRequestDownloadImage.getImageDir());
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

        // ダウンロードする画像一覧をキューに詰める
        for (CustomSearchApiItem item : items) {
            String link = item.getLink();
            Intent intent = new Intent(this, getClass());
            intent.putExtra(EXTRA_IMAGE_URL, link);

            this.startService(intent);
        }
    }

    private void startDownloadImage(String url) {
        Log.v(TAG, "ダウンロード開始: url=" + url);

        try {
            File file = mRequestDownloadImage.reqDownloadImageSync(url);
            Log.i(TAG, "ダウンロード終了: file=" + file);
        } catch (IOException e) {
            Log.i(TAG, "ダウンロード失敗");
        }
    }

}
