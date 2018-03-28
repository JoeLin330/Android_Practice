package com.example.joe.notify01;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Switch vibrate_switch, sound_switch, flash_switch,
            max_priority_switch;
    private NotificationManager manager;

    private Bitmap bigPicture;

    private static int downloadId = 10;
    private static final int BASIC_ID = 0;
    private static final int BIG_PICTURE_ID = 1;
    private static final int BIG_TEXT_ID = 2;
    private static final int INBOX_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processViews();

        // 建立大型圖片通知需要的Bitmap物件
        bigPicture = BitmapFactory.decodeResource(
                getResources(), R.drawable.notify_big_picture);
        // 取得NotificationManager物件
        manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void clickBasicSend(View view) {
        // 建立NotificationCompat.Builder物件
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);

        // 設定圖示、時間、標題、訊息和額外資訊
        builder.setSmallIcon(R.drawable.ic_android_white_48dp)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Basic Notification")
                .setContentText("Demo for basic notification control.")
                .setContentInfo("3");

        // 設定通知圖示背景顏色
        setColor(builder, Color.DKGRAY);

        // 設定通知分類目錄
        setCategoty(builder, Notification.CATEGORY_RECOMMENDATION);

        // 設定通知的優先順序
        if (max_priority_switch.isChecked()) {
            builder.setPriority(Notification.PRIORITY_MAX);
        }

        // 準備設定通知效果用的變數
        int defaults = 0;

        // 加入震動效果
        if (vibrate_switch.isChecked()) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }

        // 加入音效效果
        if (sound_switch.isChecked()) {
            defaults |= Notification.DEFAULT_SOUND;
        }

        // 加入閃燈效果
        if (flash_switch.isChecked()) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        // 設定通知效果
        builder.setDefaults(defaults);
        // 建立通知物件
        Notification notification = builder.build();
        // 使用BASIC_ID為編號發出通知
        manager.notify(BASIC_ID, notification);
    }

    public void clickBasicCancel(View view) {
        // 清除BASIC_ID編號的通知
        manager.cancel(BASIC_ID);
    }

    public void clickProgressSend(View view) {
        // 建立NotificationCompat.Builder物件
        final NotificationCompat.Builder builder
                = new NotificationCompat.Builder(this);

        // 設定圖示、時間、標題、訊息和不可清除
        builder.setSmallIcon(R.drawable.ic_file_download_white_48dp)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Download mypicture" + downloadId + ".jpg")
                .setContentText("Download in progress...")
                .setOngoing(true);

        // 設定通知圖示背景顏色
        setColor(builder, Color.DKGRAY);

        // 建立測試用的執行緒物件
        new Thread() {

            // 設定編號
            private int id = downloadId++;

            @Override
            public void run() {
                int incr;

                for (incr = 0; incr <= 100; incr += 5) {
                    // 設定進度
                    // 參數依序為最大值、目前進度與是否不確定
                    builder.setProgress(100, incr, false);
                    // 使用id為編號發出或更新通知
                    manager.notify(id, builder.build());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d("Notify01Activity", e.toString());
                    }
                }

                // 設定內容訊息為下載完成、進度為0和可以清除
                builder.setContentText("Download complete!")
                        .setProgress(0, 0, false)
                        .setOngoing(false);
                // 使用id為編號發出通知
                manager.notify(id, builder.build());
            }
        }.start();
    }

    public void clickBigPictureSend(View view) {
        // 建立Notification.Builder物件，因為要設定大型圖片樣式
        // 所以不能使用NotificationCompat.Builder
        Notification.Builder builder = new Notification.Builder(this);

        // 設定圖示、時間和標題
        builder.setSmallIcon(R.drawable.ic_insert_photo_white_48dp)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Big picture notification");

        // 設定通知圖示背景顏色
        setColor(builder, Color.DKGRAY);



        // 建立大型圖片樣式物件
        Notification.BigPictureStyle bigPictureStyle =
                new Notification.BigPictureStyle();
        // 設定圖片與簡介
        bigPictureStyle.bigPicture(bigPicture)
                .setSummaryText("The flowers");
        // 設定樣式為大型圖片
        builder.setStyle(bigPictureStyle);
        // 使用BIG_PICTURE_ID為編號發出通知
        manager.notify(BIG_PICTURE_ID, builder.build());
    }

    public void clickBigTextSend(View view) {
        // 建立Notification.Builder物件，因為要設定大型文字樣式
        // 所以不能使用NotificationCompat.Builder
        Notification.Builder builder = new Notification.Builder(this);

        // 設定圖示、時間和標題
        builder.setSmallIcon(R.drawable.ic_description_white_48dp)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Big text notification");

        // 設定通知圖示背景顏色
        setColor(builder, Color.DKGRAY);

        // 建立大型文字樣式物件
        Notification.BigTextStyle bigTextStyle =
                new Notification.BigTextStyle();
        // 設定文字與簡介
        bigTextStyle.bigText(getString(R.string.big_text))
                .setSummaryText("About notification");
        // 設定樣式為大型文字
        builder.setStyle(bigTextStyle);
        // 使用BIG_TEXT_ID為編號發出通知
        manager.notify(BIG_TEXT_ID, builder.build());
    }

    public void clickInboxSend(View view) {
        // 建立Notification.Builder物件，因為要設定列表樣式
        // 所以不能使用NotificationCompat.Builder
        Notification.Builder builder = new Notification.Builder(this);

        // 設定圖示、時間和標題
        builder.setSmallIcon(R.drawable.ic_view_list_white_48dp)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Inbox notification");

        // 設定通知圖示背景顏色
        setColor(builder, Color.DKGRAY);

        // 建立列表樣式物件
        Notification.InboxStyle inboxStyle =
                new Notification.InboxStyle();
        // 加入三行列表
        inboxStyle.addLine("You got a message from Ali");
        inboxStyle.addLine("You got a message from Andras");
        inboxStyle.addLine("You got a message from Megan");
        // 設定簡介
        inboxStyle.setSummaryText("Total 3 messages");
        // 設定樣式為列表
        builder.setStyle(inboxStyle);
        // 使用INBOX_ID為編號發出通知
        manager.notify(INBOX_ID, builder.build());
    }

    private void processViews() {
        vibrate_switch = findViewById(R.id.vibrate_switch);
        sound_switch = findViewById(R.id.sound_switch);
        flash_switch = findViewById(R.id.flash_switch);
        max_priority_switch = findViewById(R.id.max_priority_switch);
    }

    // 設定通知圖示背景顏色
    // 加入裝置版本的判斷，應用程式就不用把最低版本設定為API level 21
    private void setColor(Notification.Builder builder, int color) {
        // setColor 方法從 Android 5.0（API level 21）開始提供
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setColor(color);
        }
    }

    // 設定通知圖示背景顏色
    // 加入裝置版本的判斷，應用程式就不用把最低版本設定為API level 21
    private void setColor(NotificationCompat.Builder builder, int color) {
        // setColor 方法從 Android 5.0（API level 21）開始提供
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setColor(color);
        }
    }

    // 設定通知分類目錄
    // 加入裝置版本的判斷，應用程式就不用把最低版本設定為API level 21
    private void setCategoty(Notification.Builder builder, String category) {
        // setCategoty 方法從 Android 5.0（API level 21）開始提供
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(category);
        }
    }

    // 設定通知分類目錄
    // 加入裝置版本的判斷，應用程式就不用把最低版本設定為API level 21
    private void setCategoty(NotificationCompat.Builder builder, String category) {
        // setCategoty 方法從 Android 5.0（API level 21）開始提供
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(category);
        }
    }

}
