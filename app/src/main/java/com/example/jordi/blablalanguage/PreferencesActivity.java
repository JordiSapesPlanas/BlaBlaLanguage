package com.example.jordi.blablalanguage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void emitNotification(String title, String text ){
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, SearchMeetingActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification n  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                n = new Notification.Builder(this)
                        .setContentTitle("New mail from " + "test@gmail.com")
                        .setContentText("Subject")
                        .setColor(R.color.colorPrimary)
                        .setSmallIcon(R.drawable.icon_bla_bla_language)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).build();
            }
        }
        notificationManager.notify(0, n);
    }

}
