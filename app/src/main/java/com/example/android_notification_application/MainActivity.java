package com.example.android_notification_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android_notification_application.notification.DownloadNotification;

public class MainActivity extends AppCompatActivity {
    private int id = 1;
    private int OPEN_DIRECTORY_REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button)findViewById(R.id.basic_notification_button);
        button.setOnClickListener(v -> {
            Intent intent = null;
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
               intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
               startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE);
            } else {
            }
//            NotificationThread nt = new NotificationThread(this,id++);
//            nt.start();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TAG", "onActivityResult: ");
        if(requestCode == OPEN_DIRECTORY_REQUEST_CODE){
            Uri uri = data.getData();
            String path = uri.getPath();
            Log.i("TAG", "onActivityResult: "+path);
        }
    }
    public String getPathFromUri(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null );
        cursor.moveToNext();
        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
        cursor.close();
        return path;
    }



    class NotificationThread extends Thread{
        private Context context;
        private int id;

        public NotificationThread(Context context,int id){
            this.context = context;
            this.id = id;
        }

        @Override
        public void run() {
            super.run();
            DownloadNotification downloadNotification = new DownloadNotification(context,id);
            downloadNotification.setInfo("파일명","root위치");
            int num = 0;
            int max = 10000;
            while(num/100 <= 100){
                downloadNotification.startNotification(num/100);
                ++num;
            }
        }
    }

    private void makeNotification(){
        Notification notification = new Notification(this);
        notification.startNotification();
    }

    private void removeNotification() {

        // Notification 제거
        NotificationManagerCompat.from(this).cancel(1);
    }
}