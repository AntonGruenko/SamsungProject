package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.Calendar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity {

    private static final String SETTINGS = "SharedPreferences";


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        boolean firstLaunch = sp.getBoolean("firstLaunch", true);
        if (firstLaunch) {
            Intent firstLaunchIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(firstLaunchIntent);
        }
        setContentView(R.layout.activity_main);
        changeFragment(new HomeFragment());
        changeDateIfNeeded();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
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
    //Смена старой даты на актуальную, если она изменилась
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
        e.apply();

    }

}




