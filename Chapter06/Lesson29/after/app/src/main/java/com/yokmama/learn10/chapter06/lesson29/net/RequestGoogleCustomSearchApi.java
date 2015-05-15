package com.yokmama.learn10.chapter06.lesson29.net;

import android.content.Context;
import android.net.Uri;

import com.yokmama.learn10.chapter06.lesson29.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
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
    private final Context mContext;

    public RequestGoogleCustomSearchApi(Context context) {
        mContext = context;
    }

    /**
     * [同期] Google画像検索を行います。
     *
     * @param searchWord 検索ワード
     * @return 取得結果
     * @throws IOException 通信に失敗した時
     * @throws JSONException 取得結果が意図しないものであった場合
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
                // 通信を開始
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                stream = new BufferedInputStream(conn.getInputStream());

                // 文字列に変換
                StringBuilder json = new StringBuilder();
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = stream.read(buffer)) != -1) {
                    String read = new String(buffer, 0, byteCount);
                    json.append(read);
                }

                // JSONのパース
                JSONObject jsRoot = new JSONObject(json.toString());
                JSONArray jsItems = jsRoot.getJSONArray("items");
                ArrayList<CustomSearchApiItem> items = CustomSearchApiItem.parse(jsItems);

                return items;
            } catch (FileNotFoundException e) {
                // ステータスコードが 4xx や 5xx などのエラーの場合に通る
                // APIのアクセスに失敗した場合や、制限に引っかかった場合はここを通ります。
                throw e;
            }
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
