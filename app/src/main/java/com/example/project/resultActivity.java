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
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;
//Активность для вычисления и вывода необходимого количества калорий и питательных веществ
public class ResultActivity extends Activity {
    private static final String SETTINGS = "SharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Button button = findViewById(R.id.button7);

        TextView caloriesRes = findViewById(R.id.caloriesNum);
        TextView proteinsRes = findViewById(R.id.proteinsNum);
        TextView fatsRes = findViewById(R.id.fatsNum);
        TextView carbohydratesNum = findViewById(R.id.carbohydratesNum);
        TextView glassesNum = findViewById(R.id.glassesNum);

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

        button.setOnClickListener(view -> {
            e.putInt("glasses", userGlasses);
            e.putInt("kcal", userCalories);
            e.putInt("proteins", userProteins);
            e.putInt("fats", userFats);
            e.putInt("carbohydrates", userCarbohydrates);
            e.putLong("date", dateCalendar.getTimeInMillis());
            e.putBoolean("firstLaunch", false);
            e.apply();
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            setNotifications();
        });
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "MyDietChannel";
            String description = getString(R.string.sendsNotification);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MyDietChannelId", name, importance);
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
        PendingIntent breakfastPendingIntent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            breakfastPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, breakfastIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        }else{
            breakfastPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, breakfastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, breakfastPendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent lunchIntent = new Intent(this, LunchAlarmReceiver.class);
        PendingIntent lunchPendingIntent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            lunchPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, lunchIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        }else{
            lunchPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, lunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, lunchPendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent dinnerIntent = new Intent(this, DinnerAlarmReceiver.class);
        PendingIntent dinnerPendingIntent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            dinnerPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, dinnerIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        }else{
            dinnerPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, dinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, dinnerPendingIntent);
    }
}