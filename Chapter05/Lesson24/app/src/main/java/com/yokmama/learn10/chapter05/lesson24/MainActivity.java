package com.yokmama.learn10.chapter05.lesson24;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import java.util.Random;


public class MainActivity extends Activity {

    private HandlerThread mHandlerThread;

    private Handler mHandler;

    private int mCounter;

    private static final String TAG = Task.class.getSimpleName();

    private Random mRand = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ハンドラーを生成
        mHandlerThread = new HandlerThread("my looper");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        //ボタンのクリック処理
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procText();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //ハンドラーを閉じる
        mHandlerThread.quit();
        mHandlerThread = null;
    }


    private void procText() {
        mHandler.post(new Task(mCounter++));
    }

    private class Task implements Runnable {

        private int mIndex;

        public Task(int index) {
            mIndex = index;
        }

        @Override
        public void run() {
            try {
                //スレッドをランダムにスリープ
                int sleepTime = mRand.nextInt(5) * 1000;
                Log.d(TAG, "sleep " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "My Index is " + mIndex);
        }
    }
}
