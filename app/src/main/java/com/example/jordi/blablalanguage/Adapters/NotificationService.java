package com.example.jordi.blablalanguage.Adapters;

/**
 * Created by oscar on 11/01/16.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.jordi.blablalanguage.R;

public class NotificationService extends Service {




    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String lang = (String)intent.getExtras().getString("language");
        String time = (String)intent.getExtras().getString("date");
        String tim = time.split(" ")[3].substring(0,5);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(lang+" Meeting")
                        .setContentText("Today at "+tim);

        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        mBuilder.setLights(Color.MAGENTA, 3000, 3000);

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(01234, mBuilder.build());


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}

