package com.vino.phe.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by PHE on 15/7/20.
 */
public class NotificationReceiver extends BroadcastReceiver{
    private static final String TAG = "NotificationReceiver";

    private Intent mIntent;
    private PendingIntent mContentIntent;
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "Received the alarm broadcast event");
        // The intent to be used when the user clicks on the notification
        mIntent = new Intent(context, MainActivity.class);

        // The pending intent that wraps the underlying intent
        mContentIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setContentText("Come on! Take a picture!")
                .setContentTitle("Daily Selfie")
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setContentIntent(mContentIntent)
                .setAutoCancel(true);

        // Get the notification manager
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Pass the notification to the notification manager
        mNotificationManager.notify(1, notificationBuilder.build());

        // Log occurence of notify() call
        Log.i(TAG, "Sending selfie notification at:"
                + DateFormat.getDateTimeInstance().format(new Date()));
    }
}
