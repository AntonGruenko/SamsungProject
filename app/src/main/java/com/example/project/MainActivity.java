package com.example.project;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project.data.Database;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    private int kcalRemain;
    private int proteinsRemain;
    private int fatsRemain;
    private int carbohydratesRemain;
    private static final String SETTINGS = "SharedPreferences";

    Database db;
    Database.OpenHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new Database.OpenHelper(this);
        db = new Database(this);

    }
    @Override
    protected void onStart(){

        super.onStart();
        setContentView(R.layout.activity_main);

        TextView date = (TextView) findViewById(R.id.date);
        TextView kcalText = (TextView) findViewById(R.id.KcalRemain);
        TextView proteinsText = (TextView) findViewById(R.id.proteinsRemain);
        TextView fatsText = (TextView) findViewById(R.id.fatsRemain);
        TextView carbohydratesText = (TextView) findViewById(R.id.carbohydratesRemain);
        TextView kcalInfoText = (TextView) findViewById(R.id.caloriesRemain);
        TextView proteinsInfoText = (TextView) findViewById(R.id.proteins);
        TextView fatsInfoText = (TextView) findViewById(R.id.fats);
        TextView carbohydratesInfoText = (TextView) findViewById(R.id.fats);

        Button breakfastButton = (Button) findViewById(R.id.breakfastButton);
        Button lunchButton = (Button) findViewById(R.id.lunchButton);
        Button dinnerButton = (Button) findViewById(R.id.dinnerButton);
        Button snackButton = (Button) findViewById(R.id.snackButton);

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        boolean firstLaunch = sp.getBoolean("firstLaunch", true);
        SharedPreferences.Editor e = sp.edit();
        if(firstLaunch){
            Intent firstLaunchIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(firstLaunchIntent);
            e.commit();
        }

        String currentDateString = DateFormat.getDateInstance().format(new Date());
        date.setText(currentDateString);

        kcalRemain = sp.getInt("kcalRemain", 3000) - sp.getInt("kcalChange", 0);
        proteinsRemain = sp.getInt("proteinsRemain", 500) - sp.getInt("proteinsChange", 0);
        fatsRemain = sp.getInt("fatsRemain", 500) - sp.getInt("fatsChange", 0);
        carbohydratesRemain = sp.getInt("carbohydratesRemain", 300) - sp.getInt("carbohydratesChange", 0);

        if(kcalRemain >= 0){
            kcalText.setText(String.valueOf(kcalRemain));
        }else{
            kcalText.setText(String.valueOf(Math.abs(kcalRemain)));
            kcalInfoText.setText(R.string.kcalExcess);
        }
        if(proteinsRemain >= 0) {
            proteinsText.setText(String.valueOf(proteinsRemain));
        }else{
            String text = getString(R.string.proteins) + "\n" + "(" + getString(R.string.excess) + ")";
            proteinsInfoText.setText(text);
            proteinsText.setText(String.valueOf(Math.abs(proteinsRemain)));
        }
        if(fatsRemain >= 0){
            fatsText.setText(String.valueOf(fatsRemain));
        }else{
            String text = getString(R.string.fats) + "\n" + "(" + getString(R.string.excess) + ")";
            fatsInfoText.setText(text);
            fatsText.setText(String.valueOf(Math.abs(fatsRemain)));
        }
        if(carbohydratesRemain >= 0){
            carbohydratesText.setText(String.valueOf(carbohydratesRemain));
        }else{
            String text = getString(R.string.carbohydrates) + "\n" + "(" + getString(R.string.excess) + ")";
            carbohydratesInfoText.setText(text);
            carbohydratesText.setText(String.valueOf(Math.abs(carbohydratesRemain)));
        }
        e.putInt("kcalChange", 0);
        e.putInt("proteinsChange", 0);
        e.putInt("fatsChange", 0);
        e.putInt("carbohydratesChange", 0);
        e.putInt("kcalRemain", kcalRemain);
        e.putInt("proteinsRemain", proteinsRemain);
        e.putInt("fatsRemain", fatsRemain);
        e.putInt("carbohydratesRemain", carbohydratesRemain);
        e.commit();

        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BreakfastActivity.class);
                startActivity(intent);
            }
        });
        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LunchActivity.class);
                startActivity(intent);
            }
        });
        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DinnerActivity.class);
                startActivity(intent);
            }
        });
        snackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SnackActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerAlarms(){
        Calendar breakfastCalendar = Calendar.getInstance();
        breakfastCalendar.set(Calendar.HOUR_OF_DAY, 8);
        breakfastCalendar.set(Calendar.MINUTE, 0);
        breakfastCalendar.set(Calendar.SECOND, 0);
        Intent breakfastIntent = new Intent(MainActivity.this, BreakfastAlarmReceiver.class);
        PendingIntent breakfastPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, breakfastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager breakfastAlarm = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        breakfastAlarm.setRepeating(AlarmManager.RTC_WAKEUP, breakfastCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, breakfastPendingIntent);

        Calendar lunchCalendar = Calendar.getInstance();
        lunchCalendar.set(Calendar.HOUR_OF_DAY, 13);
        lunchCalendar.set(Calendar.MINUTE, 0);
        lunchCalendar.set(Calendar.SECOND, 0);
        Intent lunchIntent = new Intent(MainActivity.this, LunchAlarmReceiver.class);
        PendingIntent lunchPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,lunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager lunchAlarmManager = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        lunchAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, lunchCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, lunchPendingIntent);

        Calendar dinnerCalendar = Calendar.getInstance();
        dinnerCalendar.set(Calendar.HOUR_OF_DAY, 19);
        dinnerCalendar.set(Calendar.MINUTE, 0);
        dinnerCalendar.set(Calendar.SECOND, 0);
        Intent dinnerIntent = new Intent(MainActivity.this, DinnerAlarmReceiver.class);
        PendingIntent dinnerPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,dinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager dinnerAlarmManager = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        lunchAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dinnerCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, dinnerPendingIntent);
    }

    private void setDailyReset(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(MainActivity.this, UpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }



}
