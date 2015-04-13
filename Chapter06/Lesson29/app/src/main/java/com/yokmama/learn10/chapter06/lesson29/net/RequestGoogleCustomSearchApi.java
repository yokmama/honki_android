package com.yokmama.learn10.chapter06.lesson29.net;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.yokmama.learn10.chapter06.lesson29.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kayo on 15/04/13.
 */
public class RequestGoogleCustomSearchApi {
    private static final String TAG = RequestGoogleCustomSearchApi.class.getSimpleName();
    private final Context mContext;
    private boolean mMock = false;
    private Thread mThread;

    public RequestGoogleCustomSearchApi(Context context) {
        mContext = context;
    }

    public void setMock(boolean isMock) {
        mMock = isMock;
    }

    public boolean isRequesting() {
        return mThread != null;
    }

    public void reqCustomSearchApi(final String searchWord, final RestResultCallback<List<CustomSearchApiItem>> callback) {
        final String key = "AIzaSyDS23xN2CFJg6EPH9hsXRrWewER1ks9wNo";
        final String cseId = "017763129430879683958:8nd-pkaf4tq";

        if (mThread != null) {
            // リクエスト中
            return;
        }
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 仕様の詳細
                // https://developers.google.com/custom-search/json-api/v1/reference/cse/list
                Uri uri = Uri.parse("https://www.googleapis.com/customsearch/v1").buildUpon()
                        .appendQueryParameter("key", key)
                        .appendQueryParameter("cx", cseId)
                        .appendQueryParameter("q", searchWord)
                        .appendQueryParameter("searchType", "image") // 画像検索にする
                        .appendQueryParameter("imgSize", "huge") // 巨大な画像のみをヒットさせる
                        .appendQueryParameter("safe", "high") // セーフサーチレベル
                        .appendQueryParameter("alt", "json") // JSON形式で結果を受け取る
                        .build();
                final URL url;
                try {
                    url = new URL(uri.toString());
                } catch (MalformedURLException e) {
                    Log.e(TAG, "URLの形式が不正です。", e);
                    callback.onCompletion(null, e);
                    return;
                }

                HttpURLConnection conn = null;
                InputStream stream = null;
                try {
                    try {
                        final String json;
                        if (!mMock) {
                            conn = (HttpURLConnection) url.openConnection();
                            conn.addRequestProperty("Referrer", "http://www.google.co.jp/");
                            conn.setRequestMethod("GET");
                            conn.setRequestProperty("Accept", "application/json");
                            stream = new BufferedInputStream(conn.getInputStream());

                            // 文字列に変換
                            json = getString(stream);
                            stream.close();
                            stream = null;
                            conn.disconnect();
                        } else {
                            Log.i(TAG, "モックデータを利用して擬似的に通信を行います。");
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {
                            }
                            json = mContext.getString(R.string.mock_custom_search_api);
                        }

                        // JSONのパース
                        try {
                            JSONObject jsRoot = new JSONObject(json);

                            JSONArray jsItems = jsRoot.getJSONArray("items");
                            ArrayList<CustomSearchApiItem> items = CustomSearchApiItem.parse(jsItems);

                            callback.onCompletion(items, null);
                        } catch (JSONException e) {
                            callback.onCompletion(null, e);
                            return;
                        }
                    } catch (FileNotFoundException e) {
                        if (conn == null) {
                            callback.onCompletion(null, new IOException("コネクションのオープンに失敗しました。", e));
                            return;
                        }
                        stream = new BufferedInputStream(conn.getErrorStream());

                        // 4xx または 5xx なレスポンスのボディーを読み取る
                        String s = getString(stream);
                        callback.onCompletion(null, new IOException("通信ステータスエラー: Error=" + s));
                    }
                } catch (IOException ioex) {
                    callback.onCompletion(null, new IOException(ioex));
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                        }
                    }
                    mThread = null;
                }
            }
        });
        mThread.start();
    }

    public interface RestResultCallback<T> {
        /**
         * 通信完了後のコールバック
         *
         * @param result 結果のオブジェクト
         * @param error 途中でエラーが発生した場合はエラーオブジェクト、エラーが発生しなかった場合はnullが返ります。
         */
        void onCompletion(T result, Throwable error);
    }

    private static String getString(InputStream bis) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int byteCount = 0;
        while ((byteCount = bis.read(buffer)) != -1) {
            String read = new String(buffer, 0, byteCount);
            sb.append(read);
        }

        return sb.toString();
    }
}
