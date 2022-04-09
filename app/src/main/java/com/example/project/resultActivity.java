package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
        if(isUserMale)
            userCalories = (int) (((userWeight * 10) + (userHeight * 6.25) - (userAge * 5) + 5) * userActiveness);
        else
            userCalories = (int) (((userWeight * 10) + (userHeight * 6.25) - (userAge * 5) - 161) * userActiveness);

        int userProteins = (int) (userCalories * 0.3 / 4);
        int userFats = (int) (userCalories * 0.2 / 4.5);
        int userCarbohydrates = (int) (userCalories * 0.5 / 4);
        int userGlasses = (int) (userWeight * 30 / 200 - 4);

        caloriesRes.setText(String.valueOf(userCalories));
        proteinsRes.setText(String.valueOf(userProteins));
        fatsRes.setText(String.valueOf(userFats));
        carbohydratesNum.setText(String.valueOf(userCarbohydrates));
        glassesNum.setText(String.valueOf(userGlasses));

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e.putInt("kcal", userCalories);
                e.putInt("proteins", userProteins);
                e.putInt("fats", userFats);
                e.putInt("carbohydrates", userCarbohydrates);
                e.putInt("kcalRemain", userCalories);
                e.putInt("proteinsRemain", userProteins);
                e.putInt("fatsRemain", userFats);
                e.putInt("carbohydratesRemain", userCarbohydrates);
                e.putBoolean("firstLaunch", false);
                e.commit();
                registerAlarms();
                setDailyReset();
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerAlarms(){
        Calendar breakfastCalendar = Calendar.getInstance();
        breakfastCalendar.set(Calendar.HOUR_OF_DAY, 8);
        breakfastCalendar.set(Calendar.MINUTE, 0);
        breakfastCalendar.set(Calendar.SECOND, 0);
        Intent breakfastIntent = new Intent(ResultActivity.this, BreakfastAlarmReceiver.class);
        PendingIntent breakfastPendingIntent = PendingIntent.getBroadcast(ResultActivity.this, 0, breakfastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager breakfastAlarm = (AlarmManager) ResultActivity.this.getSystemService(ResultActivity.this.ALARM_SERVICE);
        breakfastAlarm.setRepeating(AlarmManager.RTC_WAKEUP, breakfastCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, breakfastPendingIntent);

        Calendar lunchCalendar = Calendar.getInstance();
        lunchCalendar.set(Calendar.HOUR_OF_DAY, 13);
        lunchCalendar.set(Calendar.MINUTE, 0);
        lunchCalendar.set(Calendar.SECOND, 0);
        Intent lunchIntent = new Intent(ResultActivity.this, LunchAlarmReceiver.class);
        PendingIntent lunchPendingIntent = PendingIntent.getBroadcast(ResultActivity.this, 0,lunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager lunchAlarmManager = (AlarmManager) ResultActivity.this.getSystemService(ResultActivity.this.ALARM_SERVICE);
        lunchAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, lunchCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, lunchPendingIntent);

        Calendar dinnerCalendar = Calendar.getInstance();
        dinnerCalendar.set(Calendar.HOUR_OF_DAY, 19);
        dinnerCalendar.set(Calendar.MINUTE, 0);
        dinnerCalendar.set(Calendar.SECOND, 0);
        Intent dinnerIntent = new Intent(ResultActivity.this, DinnerAlarmReceiver.class);
        PendingIntent dinnerPendingIntent = PendingIntent.getBroadcast(ResultActivity.this, 0,dinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager dinnerAlarmManager = (AlarmManager) ResultActivity.this.getSystemService(ResultActivity.this.ALARM_SERVICE);
        lunchAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dinnerCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, dinnerPendingIntent);
    }

    private void setDailyReset(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(ResultActivity.this, UpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ResultActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) ResultActivity.this.getSystemService(ResultActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}