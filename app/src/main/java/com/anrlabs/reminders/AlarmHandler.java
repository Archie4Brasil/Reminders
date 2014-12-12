package com.anrlabs.reminders;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Archie on 12/12/2014.
 */
public class AlarmHandler extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(arg0)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

        // For our recurring task, we'll just display a message
        Toast.makeText(arg0, "test", Toast.LENGTH_SHORT).show();
    }
}
