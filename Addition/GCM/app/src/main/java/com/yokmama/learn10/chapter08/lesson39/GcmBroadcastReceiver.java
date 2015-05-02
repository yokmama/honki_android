package com.yokmama.learn10.chapter08.lesson39;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = GcmBroadcastReceiver.class.getSimpleName();

    public GcmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive:" + intent.getAction());
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // GcmIntentServiceを起動、サービス処理中はWakeLockを保持する
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
