package com.yokmama.learn10.chapter05.lesson23;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Applicationからカウントの値を取得して1つカウントアップ
        MyApplication myApplication = (MyApplication)getApplication();
        myApplication.setCount(myApplication.getCount()+1);

        return START_NOT_STICKY;
    }
}
