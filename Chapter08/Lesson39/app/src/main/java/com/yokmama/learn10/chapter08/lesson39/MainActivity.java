package com.yokmama.learn10.chapter08.lesson39;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonRegister).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        registerInBackground(MainActivity.this);
                    }
                });

        findViewById(R.id.buttonUnRegister).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        unrgisterInBackground(MainActivity.this);
                    }
                });
    }
    private void unrgisterInBackground(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Log.d(TAG, "GCM解除開始");
                try {
                    // GoogleCloudMessagingインスタンスの取得
                    GoogleCloudMessaging gcm = GoogleCloudMessaging
                            .getInstance(context);
                    if (gcm != null) {
                        // GCM解除処理
                        gcm.unregister();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(null, null, null);
    }

    private void registerInBackground(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Log.d(TAG, "GCM登録開始");
                try {
                    // GoogleCloudMessagingインスタンスの取得
                    GoogleCloudMessaging gcm = GoogleCloudMessaging
                            .getInstance(context);
                    if (gcm != null) {
                        // GCM登録処理
                        String regid = gcm.register(getString(R.string.google_play_service_project_number));
                        Log.d(TAG, "デバイス登録完了　登録ID=" + regid);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(null, null, null);
    }

    // @formatter:off
    //curl -X POST \
    //        -H "Authorization: key= YOUR_AUTHORIZATION_KEY" \
    //        -H "Content-Type: application/json" \
    //        -d '{
    //        "registration_ids": [
    //        "YOUR_DEVICE_TOKEN"
    //        ],
    //        "data": {
    //    "message": "YOUR_MESSAGE"
    //}
    //}' \
    // https://android.googleapis.com/gcm/send
    // @formatter:on
}
