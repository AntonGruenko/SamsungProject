package com.example.project;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DinnerAlarmReceiver extends BroadcastReceiver {
    Intent notificationIntent;
    PendingIntent notificationPendingIntent;
    Calendar notificationCalendar;
    String notificationText;
    String notificationTitle;
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;
    int id;

    @Override
    public void onReceive(Context context, Intent intent) {

        notificationIntent = new Intent(context, MealActivity.class);
        notificationIntent.putExtra("path",intent.getIntExtra("notificationCode", 3));
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationCalendar = Calendar.getInstance();
        notificationText = context.getString(R.string.dontForgetToEnerYourData);
        notificationTitle = context.getString(R.string.timeToHaveDinner);
        builder = new NotificationCompat.Builder(context, "Notification")
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setChannelId("Notification")
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_DEFAULT);

        notificationManager = NotificationManagerCompat.from(context);
        id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.ENGLISH).format(new Date()));
        notificationManager.notify((int) id, builder.build());
    }
}
