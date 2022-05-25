package com.example.project;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class ResultActivity extends Activity {
    private static final String SETTINGS = "SharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Button button = (Button) findViewById(R.id.button7);

        TextView caloriesRes = (TextView) findViewById(R.id.caloriesNum);
        TextView proteinsRes = (TextView) findViewById(R.id.proteinsNum);
        TextView fatsRes = (TextView) findViewById(R.id.fatsNum);
        TextView carbohydratesNum = (TextView) findViewById(R.id.carbohydratesNum);
        TextView glassesNum = (TextView) findViewById(R.id.glassesNum);

        Bundle bundle = getIntent().getExtras();
        int userHeight = bundle.getInt("height");
        int userWeight = bundle.getInt("weight");
        boolean isUserMale = bundle.getBoolean("gender");
        int userAge = bundle.getInt("age");
        double userActiveness = bundle.getDouble("activeness");

        int userCalories;
        if (isUserMale)
            userCalories = (int) (((userWeight * 10) + (userHeight * 6.25) - (userAge * 5) + 5) * userActiveness);
        else
            userCalories = (int) (((userWeight * 10) + (userHeight * 6.25) - (userAge * 5) - 161) * userActiveness);

        int userProteins = (int) (userCalories * 0.3 / 4);
        int userFats = (int) (userCalories * 0.2 / 4.5);
        int userCarbohydrates = (int) (userCalories * 0.5 / 4);
        int userGlasses = (int) (userWeight * 0.15);

        caloriesRes.setText(String.valueOf(userCalories));
        proteinsRes.setText(String.valueOf(userProteins));
        fatsRes.setText(String.valueOf(userFats));
        carbohydratesNum.setText(String.valueOf(userCarbohydrates));
        glassesNum.setText(String.valueOf(userGlasses));
        Calendar dateCalendar = Calendar.getInstance();

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        createNotificationChannel();

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        Intent intent = new Intent(this, BreakfastAlarmReceiver.class);
        intent.putExtra("notificationCode", 3);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e.putInt("glasses", userGlasses);
                e.putInt("kcal", userCalories);
                e.putInt("proteins", userProteins);
                e.putInt("fats", userFats);
                e.putInt("carbohydrates", userCarbohydrates);
                e.putLong("date", dateCalendar.getTimeInMillis());
                e.putBoolean("firstLaunch", false);
                e.commit();
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                setNotifications();
            }
        });
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyDietChannel";
            String description = "channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setNotifications(){
        createNotificationChannel();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if(calendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()){
            calendar.add(Calendar.DATE, 1);
        }

        Intent breakfastIntent = new Intent(this, BreakfastAlarmReceiver.class);
        PendingIntent breakfastPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, breakfastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, breakfastPendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent lunchIntent = new Intent(this, LunchAlarmReceiver.class);
        PendingIntent lunchPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, lunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, lunchPendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent dinnerIntent = new Intent(this, DinnerAlarmReceiver.class);
        PendingIntent dinnerPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, dinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, dinnerPendingIntent);
    }
}