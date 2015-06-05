package com.yokmama.learn10.chapter05.lesson24;

import android.app.IntentService;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class MyIntentService extends IntentService {
    public static final String ACTION_COUNT_UP = "com.yokmama.learn10.chapter05.lesson24.action.CountUp";
    public static final String ACTION_UPDATE_VALUE = "com.yokmama.learn10.chapter05.lesson24.action.updateValue";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_COUNT_UP.equals(action)) {
                //Applicationからカウントの値を取得して1つカウントアップ
                MyApplication myApplication = ((MyApplication)getApplication());
                myApplication.setCount(myApplication.getCount() + 1);

                //更新をブロードキャスト
                sendBroadcast(new Intent(ACTION_UPDATE_VALUE));
            }
        }
    }
}
