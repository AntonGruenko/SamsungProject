package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

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

    /*private void setDailyReset() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 15);
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        Intent intent = new Intent(this, UpdateReceiver.class);
        intent.putExtra("triggerTime", calendar);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }*/

}



    
