package com.yokmama.learn10.chapter06.lesson29.net;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by kayo on 15/04/13.
 */
public class DownloadService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
