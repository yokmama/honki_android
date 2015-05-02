package com.yokmama.learn10.chapter08.lesson39;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class GcmIntentService extends IntentService {
    private static final String TAG = GcmIntentService.class.getSimpleName();

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        // GoogleCloudMessagingインスタンスの取得
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        if (gcm != null) {
            // GCM受信データを表示
            StringBuilder log = new StringBuilder();
            log.append("MessageType:").append(gcm.getMessageType(intent));
            if (!extras.isEmpty()) {
                log.append("\n").append("Extras:").append(extras.toString());
            }
            Log.d(TAG, log.toString());
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
