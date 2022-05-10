package com.example.project;


import android.app.Activity;
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
        if(isUserMale)
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
        Calendar calendar = Calendar.getInstance();

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e.putInt("glasses", userGlasses);
                e.putInt("kcal", userCalories);
                e.putInt("proteins", userProteins);
                e.putInt("fats", userFats);
                e.putInt("carbohydrates", userCarbohydrates);
                e.putLong("date", calendar.getTimeInMillis());
                e.putBoolean("firstLaunch", false);
                e.commit();
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}