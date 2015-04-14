package com.yokmama.learn10.chapter06.lesson29.net;

import android.content.Context;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Created by kayo on 15/04/14.
 */
public class RequestDownloadImage {

    private final File mImageDir;

    public RequestDownloadImage(Context context) {
        mImageDir = new File(context.getFilesDir(), "images");
    }

    public File getImageDir() {
        return mImageDir;
    }

    public File reqDownloadImageSync(final String urls) throws IOException {
        final URL url = new URL(urls);

        // 保存先
        final File imageFile = new File(mImageDir, UUID.randomUUID().toString());
        if (!mImageDir.exists()) {
            if (!mImageDir.mkdirs()) {
                throw new IOException("ディレクトリの作成に失敗しました。容量エラー？");
            }
        }
        imageFile.createNewFile();

        // HTTP通信
        HttpURLConnection conn = null;
        InputStream stream = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            stream = new BufferedInputStream(conn.getInputStream());

            // ファイルとして保存
            FileOutputStream output = new FileOutputStream(imageFile);

            // 書き込み
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = stream.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }

            return imageFile;
        } catch (IOException e) {
            // 通信失敗したら、中途半端なファイルを削除
            imageFile.delete();
            return null;
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
}
