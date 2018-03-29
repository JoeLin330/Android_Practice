package com.example.joe.notify02;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private NotificationManager manager;

    private static final int ACTION_ID = 0;
    private static final int CUSTOM_EFFECT_ID = 1;
    private static final int CUSTOM_LAYOUT_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify02);

        // 取得NotificationManager物件
        manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void clickActionSend(View view) {
        // 建立NotificationCompat.Builder物件
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);

        // 設定小圖示、時間、標題和訊息
        builder.setSmallIcon(R.drawable.ic_perm_phone_msg_white_48dp)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Action Notification")
                .setContentText("Demo for notification action");

        // 建立點擊通知需要的PendingIntent物件
        // 第三個參數指定點擊以後要啟動的Activity元件，
        // 使用getIntent()設定為自己，就是Notify02Activity
        PendingIntent forwardIntent =
                PendingIntent.getActivity(this, 0, getIntent(), 0);
        // 設定點擊通知的PendingIntent物件
        builder.setContentIntent(forwardIntent);

        // 建立按鈕功能需要的Intent物件，點擊後直接撥打電話
        Intent intentCall = new Intent(Intent.ACTION_CALL);
        // 設定電話號碼
        intentCall.setData(Uri.parse("tel:0933111222"));
        // 建立啟動撥打電話元件需要的PendingIntent物件
        PendingIntent piCall =
                PendingIntent.getActivity(this, 0, intentCall, 0);
        // 設定撥打電話的PendingIntent物件
        builder.addAction(R.drawable.notify_call_button, "Call", piCall);

        // 建立按鈕功能需要的Intent物件，點擊後開啟網頁
        Intent intentView = new Intent(Intent.ACTION_VIEW);
        // 設定網址
        intentView.setData(Uri.parse("http://tw.linkedin.com/in/macdidi5/zh-tw"));
        // 建立啟動瀏覽器元件需要的PendingIntent物件
        PendingIntent piWeb =
                PendingIntent.getActivity(this, 0, intentView, 0);
        // 設定開啟網頁的PendingIntent物件
        builder.addAction(R.drawable.action_web_button, "Web", piWeb);

        // 建立通知物件
        Notification notification = builder.build();
        // 使用ACTION_ID為編號發出通知
        manager.notify(ACTION_ID, notification);
    }

    public void clickCustomEffectSend(View view) {
        // 建立NotificationCompat.Builder物件
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);

        // 設定小圖示、時間和標題
        builder.setSmallIcon(R.drawable.ic_android_white_48dp)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Custom effect");

        // 建立震動效果，陣列中元素依序為停止、震動的時間，單位是毫秒
        long[] vibrate_effect =
                {1000, 500, 1000, 400, 1000, 300, 1000, 200, 1000, 100};
        // 設定震動效果
        builder.setVibrate(vibrate_effect);

        // 建立音效效果，放在res/raw下的音效檔
        Uri sound_effect = Uri.parse(
                "android.resource://" + getPackageName() + "/raw/zeta");
        // 設定音效效果
        builder.setSound(sound_effect);

        // 設定閃燈效果，參數依序為顏色、打開與關閉時間，單位是毫秒
        builder.setLights(Color.GREEN, 1000, 1000);

        // 建立通知物件
        Notification notification = builder.build();
        // 使用CUSTOM_EFFECT_ID為編號發出通知
        manager.notify(CUSTOM_EFFECT_ID, notification);
    }

    public void clickCustomLayoutSend(View view) {
        // 建立NotificationCompat.Builder物件，
        // 因為需要在巢狀類別中使用，所以加入final關鍵字
        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);

        // 設定小圖示、時間和不可以清除
        builder.setSmallIcon(R.drawable.ic_timer_white_48dp)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);

        // 建立包裝畫面配置資源的RemoteViews物件
        RemoteViews remoteView = new RemoteViews(
                getPackageName(), R.layout.my_notification);
        // 設定通知使用自己設計的畫面
        builder.setContent(remoteView);

        // 建立通知物件，因為需要在巢狀類別中使用，所以加入final關鍵字
        final Notification notification = builder.build();

        // 設定RemoteViews物件中的畫面元件內容，開始倒數的時間
        notification.contentView.setTextViewText(
                R.id.start_text, "Start at " + getNow());

        // 建立與啟動倒數的執行緒
        new Thread() {
            @Override
            public void run() {
                // 倒數十六秒
                for (int seconds = 16; seconds > 0; seconds--) {
                    // 設定RemoteViews物件中的畫面元件內容，倒數的時間
                    notification.contentView.setTextViewText(
                            R.id.countdown_text, getTimeStr(seconds));
                    // 送出與更新CUSTOM_LAYOUT_ID編號的通知
                    manager.notify(CUSTOM_LAYOUT_ID, notification);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d("Notify02Activity", e.toString());
                    }
                }

                // 設定RemoteViews物件中的畫面元件內容，結束倒數的時間
                notification.contentView.setTextViewText(
                        R.id.end_text, "End at " + getNow());

                // 設定通知為可以清除
                builder.setOngoing(false);
                // 更新CUSTOM_LAYOUT_ID編號的通知

                manager.notify(CUSTOM_LAYOUT_ID, builder.build());
            }
        }.start();
    }

    // 傳回參數指定秒數的「時：分」格式字串
    private String getTimeStr(int seconds) {
        int minute;
        seconds--;

        minute = seconds / 60;
        seconds = seconds - (minute * 60);

        return String.format("%02d", minute) + ":" +
                String.format("%02d", seconds);
    }

    // 傳回目前時間
    private String getNow() {
        return String.format("%tT", new java.util.Date());
    }

}
