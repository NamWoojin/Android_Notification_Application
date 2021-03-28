package com.example.android_notification_application;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class Notification {

    private static final String CHANNEL_ID = "Main";
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;

    public Notification(Context context){
        builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("basic notification")
                .setContentText("this is basic notification!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("확장했을 때 보여지는 글입니다."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

//        builder.setColor(Color.BLUE);
        // 사용자가 탭을 클릭하면 자동 제거
//        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Oreo 이상 버전은 notification channel 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        Log.i("TAG", "makeNotification: "+notificationManager);

    }

    public void startNotification(){
        notificationManager.notify(1,builder.build());
    }
}
