package com.example.project;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BreakfastAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, MealActivity.class);
        notificationIntent.putExtra("path", 1);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent notificationPendingIntent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        }else{
            notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        String notificationText = context.getString(R.string.dontForgetToEnterYourData);
        String notificationTitle = context.getString(R.string.timeToHaveBreakfast);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MyDietChannelId")
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setChannelId("MyDietChannelId")
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.ENGLISH).format(new Date()));
        notificationManager.notify(id, builder.build());
    }
}

