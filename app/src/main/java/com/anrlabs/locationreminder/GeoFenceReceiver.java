package com.anrlabs.locationreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.anrlabs.reminders.DatabaseHelper;
import com.anrlabs.reminders.R;
import com.anrlabs.reminders.ShowReminder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.List;

/**
 * Created by sandeepkannan on 12/9/14.
 */
public class GeoFenceReceiver extends BroadcastReceiver {
    Context context;
    String[] strinIds;
    Intent broadcastIntent = new Intent();
    private long dataID;
    public SharedPreferences pref;


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        //pref = new Activity().getSharedPreferences("alert", 0);
        Toast.makeText(context, "made it", Toast.LENGTH_LONG).show();
        if (intent.getAction().equals("com.anrlabs.ACTION_RECEIVE_GEOFENCE")) {
            // if (intent  instanceof )
            broadcastIntent.addCategory(GeoFenceConsants.CATEGORY_LOCATION_SERVICES);
            Toast.makeText(context, "made it", Toast.LENGTH_LONG).show();
            handleEnter(intent);
        } else  if (intent.getAction().equals("android.intent.action.RUN")) {
            //call timer
            dataID = intent.getLongExtra("idNumber", -1);

            if(dataID>(-1))
            {
                Cursor constantsCursor = DatabaseHelper.getInstance(context).loadReminderDetails(dataID);
                constantsCursor.move(1);

                strinIds = new String[]{Long.toString(dataID)};
                sendNotification(constantsCursor.getString(constantsCursor.getColumnIndex(DatabaseHelper.TITLE)));

                constantsCursor.close();
            }
        }

    }




    private void handleEnter(Intent intent) {
        int transition = LocationClient.getGeofenceTransition(intent);
        if (transition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Post a notification
            List<Geofence> geofences = LocationClient
                    .getTriggeringGeofences(intent);
            strinIds = new String[geofences.size()];
            int index=0;
           for (Geofence geofence : geofences) {

               strinIds[index]= geofence.getRequestId();

           }
            index=0;
            List<String> lst = DatabaseHelper.getInstance(this.context).loadTitlesForNotification(strinIds);
           for (String  str :lst) {
               sendNotification((String)lst.get(index));
           }
        } else {
            // Always log as an error
         }
    }

/*
    private void sendNotificationNew(String title) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "New Notification";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        long[] vibrate = {0,100,200,200,200,200};
        notification.vibrate = vibrate;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        CharSequence contentTitle = "Title";
        CharSequence contentText = "Text";
        Intent notificationIntent = new Intent(context, ShowReminder.class);
        notificationIntent.putExtra("myId", 4);
        notificationIntent.putExtra("name", "Sandeep");
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent contentIntent = PendingIntent.getActivity(context, iUniqueId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        int mynotification_id = 1;

        mNotificationManager.notify(mynotification_id, notification);
    }
*/
    private void sendNotification(String title) {

        Intent notificationIntent = new Intent(context, ShowReminder.class);
        notificationIntent.putExtra("notificationId",strinIds[0]);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ShowReminder.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
       // PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, iUniqueId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText( "Click here to open app")
                .setContentIntent(notificationPendingIntent)
                .setTicker("You have a new Reminder");
        builder.setAutoCancel(true);
        pref = context.getSharedPreferences("alert", 0);
        if(pref.getBoolean("check",false)){
            long[] vibrate = {0, 100, 200, 200, 200, 200};
            builder.setVibrate(vibrate);

        }
        else
            builder.setDefaults(Notification.DEFAULT_SOUND);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
        /*
        if(pref.getBoolean("check", false))
            mode = pref.getInt("vibrate", 0);
        else
            mode = pref.getInt("loud", 0);
        audioMgr.setRingerMode(mode);
*/
    }


    public static String getErrorString(Context context, int errorCode) {

        // Get a handle to resources, to allow the method to retrieve messages.
        Resources mResources = context.getResources();

        // Define a string to contain the error message
        String errorString;

        // Decide which error message to get, based on the error code.
        switch (errorCode) {

            case ConnectionResult.DEVELOPER_ERROR:
                errorString = "DEVELOPER ERROR";
                break;

            case ConnectionResult.INTERNAL_ERROR:
                errorString = "INTERNAL ERROR";
                break;

            case ConnectionResult.INVALID_ACCOUNT:
                errorString = "The Account is invalid";
                break;

            case ConnectionResult.LICENSE_CHECK_FAILED:
                errorString = "The licence check failed";
                break;

            case ConnectionResult.NETWORK_ERROR:
                errorString = "There was a problem connecting to network";
                break;

            case ConnectionResult.RESOLUTION_REQUIRED:
                errorString = "Addition information needed";
                break;

            case ConnectionResult.SERVICE_DISABLED:
                errorString = "Google play serivce disabled";
                break;

            case ConnectionResult.SERVICE_INVALID:
                errorString = "The version of Google play on this device is not matching";
                break;

            case ConnectionResult.SERVICE_MISSING:
                errorString = "Google play service missing";
                break;

            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                errorString = "Please update the google play";
                break;

            case ConnectionResult.SIGN_IN_REQUIRED:
                errorString = "Please sign in to your google service";
                break;

            default:
                errorString = "Unknown error";
                break;
        }

        // Return the error message
        return errorString;
    }
}