package com.example.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
public class UpdateReceiver extends BroadcastReceiver {
    private static final String SETTINGS = "SharedPreferences";
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt("kcalRemain", sp.getInt("kcal", 0));
        e.putInt("proteinsRemain", sp.getInt("proteins", 0));
        e.putInt("fatsRemain", sp.getInt("fats", 0));
        e.putInt("carbohydratesRemain", sp.getInt("carbohydrates", 0));
        e.commit();
    }
}
