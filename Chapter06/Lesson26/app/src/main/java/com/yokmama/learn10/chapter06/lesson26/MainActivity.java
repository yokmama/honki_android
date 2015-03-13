package com.yokmama.learn10.chapter06.lesson26;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //時計を動かす
        new Timer(true).schedule(new ClockTask(), 0, 1000);
    }

    /**
     * 時計を更新するタイマータスククラス.
     */
    private class ClockTask extends TimerTask {

        Handler handler = new Handler();

        @Override
        public void run() {
            //UIスレッドにpost
            handler.post(new Runnable() {
                public void run() {
                    //時間を取得
                    Calendar c = Calendar.getInstance();
                    String hour = String.format("%02d", c.get(Calendar.HOUR_OF_DAY));
                    String minute = String.format("%02d", c.get(Calendar.MINUTE));
                    String second = String.format("%02d", c.get(Calendar.SECOND));

                    //時間を表示
                    StringBuilder sb = new StringBuilder();
                    sb.append(hour).append(":").append(minute).append(":").append(second);
                    TextView tvClock = (TextView) findViewById(R.id.clock);
                    tvClock.setText(sb.toString());
                }
            });
        }
    }
}