package com.yokmama.learn10.chapter05.lesson21;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null){
            if ("show".equals(intent.getAction())){
                // 通知を表示
                showNotification();
            }else if ("hide".equals(intent.getAction())){
                // 通知を非表示
                hideNotification();
            }
        }

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Notificationを作成.
     */
    private void showNotification(){
        // Intentの作成
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // LargeIconのBitmapを作成
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.dog);

        // NotificationBuilderを作成
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentIntent(contentIntent);
        // ステータスバーに表示されるテキスト
        builder.setTicker("Ticker");
        // アイコン
        builder.setSmallIcon(R.drawable.ic_stat_small);
        // Notificationを開いたときに表示されるタイトル
        builder.setContentTitle("ContentTitle");
        // Notificationを開いたときに表示されるサブタイトル
        builder.setContentText("ContentText");
        // Notificationを開いたときに表示されるアイコン
        builder.setLargeIcon(largeIcon);
        // 通知するタイミング
        builder.setWhen(System.currentTimeMillis());
        // 通知時の音・バイブ・ライト
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        // タップするとキャンセル(消える)
        builder.setAutoCancel(true);

        // NotificationManagerを取得
        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        // Notificationを作成して通知
        manager.notify(0, builder.build());
    }

    /**
     * Notificationを消去
     */
    private void hideNotification(){
        // NotificationManagerを取得
        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        // Notificationを作成して通知
        manager.cancel(0);
    }
}
