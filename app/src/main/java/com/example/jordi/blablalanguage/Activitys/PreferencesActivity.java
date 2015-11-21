package com.example.jordi.blablalanguage.Activitys;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.jordi.blablalanguage.R;

public class PreferencesActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void emitNotification(String title, String text) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, SearchMeetingActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification n = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                n = new Notification.Builder(this)
                        .setContentTitle("New mail from " + "test@gmail.com")
                        .setContentText("Subject")
                        //.setColor(R.color.colorPrimary)
                        .setSmallIcon(R.drawable.icon_bla_bla_language)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).build();
            //}
        }
        notificationManager.notify(0, n);
    }
    @Override
    public void onResume(){
        super.onResume();
        this.emitNotification("Hello user", "bla bla language");

    }



}
