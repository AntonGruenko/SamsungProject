package com.example.project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import com.example.project.data.Database;

import java.util.concurrent.TimeUnit;

public class UpdateReceiver extends BroadcastReceiver {
    private Database db;
    private AlarmManager am;
    private static final String SETTINGS = "SharedPreferences";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        db = new Database(context);
        long triggerTime = intent.getLongExtra("triggerTime", System.currentTimeMillis());
        triggerTime += TimeUnit.HOURS.toMillis(1);
        int kcalRemain = sp.getInt("kcal", 0);
        int proteinsRemain = sp.getInt("proteins", 0);
        int fatsRemain = sp.getInt("fats", 0);
        int carbohydratesRemain = sp.getInt("carbohydrates", 0);
        int glassesRemain = sp.getInt("glasses", 0);
        boolean isSuccessful = false;

        int kcalRange = (int) (sp.getInt("kcal", 0) * 0.2);
        int proteinsRange = (int) (sp.getInt("proteins", 0) * 0.1);
        int fatsRange = (int) (sp.getInt("fats", 0) * 0.1);
        int carbohydratesRange = (int) (sp.getInt("carbohydrates", 0) * 0.1);

        if((kcalRemain <= kcalRange && kcalRemain >= -kcalRange) &&
                (proteinsRemain <= proteinsRange && proteinsRemain >= -proteinsRange) &&
                (fatsRemain <= fatsRange && fatsRemain >= -fatsRange) &&
                (carbohydratesRemain <= carbohydratesRange && carbohydratesRemain >= -carbohydratesRange) &&
                (glassesRemain >= --glassesRemain)){
            isSuccessful = true;
        }
        db.insert(sp.getLong("date", 0), kcalRemain, proteinsRemain, fatsRemain, carbohydratesRemain, glassesRemain, isSuccessful);

        Intent alarmIntent = new Intent(context, UpdateReceiver.class);
        intent.putExtra("triggerTime", triggerTime);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    triggerTime, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            am.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }

    }
}
