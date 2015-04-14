package com.yokmama.learn10.chapter06.lesson29.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.yokmama.learn10.chapter06.lesson29.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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

    public RequestGoogleCustomSearchApi(Context context) {
        mContext = context;
    }

    public void setMock(boolean isMock) {
        mMock = isMock;
    }

    /**
     * 同期で検索を行います。
     *
     * @param searchWord 検索ワード
     * @return
     * @throws IOException
     * @throws JSONException
     * @see "https://developers.google.com/custom-search/json-api/v1/reference/cse/list"
     */
    public List<CustomSearchApiItem> reqCustomSearchApiSync(final String searchWord) throws IOException, JSONException {
        // URLの構築
        Uri uri = Uri.parse("https://www.googleapis.com/customsearch/v1").buildUpon()
                .appendQueryParameter("key", mContext.getString(R.string.api_custom_search_api_key))
                .appendQueryParameter("cx", mContext.getString(R.string.api_custom_search_engine_id))
                .appendQueryParameter("q", searchWord)
                .appendQueryParameter("searchType", "image") // 画像検索にする
                .appendQueryParameter("imgSize", "huge") // 巨大な画像のみをヒットさせる
                .appendQueryParameter("safe", "high") // セーフサーチレベル
                .appendQueryParameter("alt", "json") // JSON形式で結果を受け取る
                .build();
        final URL url = new URL(uri.toString());

        // HTTP通信
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
                JSONObject jsRoot = new JSONObject(json);
                JSONArray jsItems = jsRoot.getJSONArray("items");
                ArrayList<CustomSearchApiItem> items = CustomSearchApiItem.parse(jsItems);

                return items;
            } catch (FileNotFoundException e) {
                // ステータスコードが 4xx や 5xx などのエラーの場合に通る
                if (conn == null) {
                    throw new FileNotFoundException("コネクションのオープンに失敗しました。");
                }
                stream = new BufferedInputStream(conn.getErrorStream());

                // レスポンスの内容を読み取る
                String s = getString(stream);
                throw new FileNotFoundException("通信ステータスエラー: Error=" + s);
            }
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
        }
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
