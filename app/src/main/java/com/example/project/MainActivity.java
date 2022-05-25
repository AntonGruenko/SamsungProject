package com.example.project;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.project.data.Database;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends FragmentActivity {

    private static final String SETTINGS = "SharedPreferences";

    Database db;
    Database.OpenHelper helper;


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        boolean firstLaunch = sp.getBoolean("firstLaunch", true);
        SharedPreferences.Editor e = sp.edit();
        if (firstLaunch) {
            Intent firstLaunchIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(firstLaunchIntent);
        }
        setContentView(R.layout.activity_main);
        changeFragment(new HomeFragment());
        helper = new Database.OpenHelper(this);
        db = new Database(this);

        changeDateIfNeeded();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                fragment = new HomeFragment();
                                break;
                            case R.id.nav_me:
                                fragment = new UserFragment();
                                break;
                            default:
                                break;
                        }
                        changeFragment(fragment);
                        return true;
                    }
                });

        /*createNotificationChannel();

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("notificationCode", 2);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        sendBroadcast(intent);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }*/
    }

    private boolean changeFragment(Fragment fragment) {
        if (fragment == null) {
            return false;
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
    }

    /*private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyDietChannel";
            String description = "channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/

    private void changeDateIfNeeded() {
        SharedPreferences sp = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        long date = sp.getLong("date", 0);
        Calendar currentCalendar = Calendar.getInstance();
        Calendar oldCalendar = Calendar.getInstance();
        oldCalendar.setTimeInMillis(date);

        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MILLISECOND, 0);
        oldCalendar.set(Calendar.HOUR_OF_DAY, 0);
        oldCalendar.set(Calendar.MINUTE, 0);
        oldCalendar.set(Calendar.SECOND, 0);
        oldCalendar.set(Calendar.MILLISECOND, 0);

        long period = currentCalendar.getTimeInMillis() - oldCalendar.getTimeInMillis();
        period /= UserFragment.getDayInMillis();
        date = date + period * UserFragment.getDayInMillis();
        e.putLong("date", date);
        e.commit();

    }

}




