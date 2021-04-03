package com.example.android_notification_application.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.android_notification_application.R;

public class DownloadNotification {

    private static final String CHANNEL_ID = "Main";
    private int notificationId = 1;
    private static final int PROGRESS_MAX = 100;
    private String fileName;
    private String path;
    private NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;
//    private NotificationManager notificationManager;

    public DownloadNotification(Context context,int id){
        notificationId = id;
        notificationManager = NotificationManagerCompat.from(context);
        builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Share Hi")
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

    public void setInfo(String fileName,String path){
        this.fileName = fileName;
        this.path = path;
        builder.setContentText(fileName + "을 다운받는 중입니다.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(fileName+"을 "+path+"에 다운받는 중입니다."));
    }

    public void startNotification(int PROGRESS_CURRENT){
        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        if(PROGRESS_CURRENT == PROGRESS_MAX){
            Log.i("TAG", "startNotification: "+PROGRESS_CURRENT);
            builder.setContentText(fileName + "을 다운받았습니다.")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(fileName+"을 "+path+"에 다운받았습니다."));
            notificationManager.notify(notificationId, builder.build());
        }
        notificationManager.notify(notificationId, builder.build());
    }
}
