package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class resultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView caloriesRes = (TextView) findViewById(R.id.caloriesNum);
        TextView proteinsRes = (TextView) findViewById(R.id.proteinsNum);
        TextView fatsRes = (TextView) findViewById(R.id.fatsNum);
        TextView carbohydratesNum = (TextView) findViewById(R.id.carbohydratesNum);
        TextView glassesNum = (TextView) findViewById(R.id.glassesNum);

        Intent intent = getIntent();
        int userHeight = Integer.parseInt(intent.getStringExtra("height"));
        int userWeight = Integer.parseInt(intent.getStringExtra("weight"));
        boolean isUserMale = Boolean.parseBoolean(intent.getStringExtra("gender"));
        int userAge = Integer.parseInt(intent.getStringExtra("age"));
        double userActiveness = Double.parseDouble(intent.getStringExtra("activeness"));

        int userCalories;
        if(isUserMale)
            userCalories = (int) (((userWeight * 10) + (userHeight * 6.25) - (userAge * 5) + 5) * userActiveness);
        else
            userCalories = (int) (((userWeight * 10) + (userHeight * 6.25) - (userAge * 5) - 161) * userActiveness);

        int userProtein = (int) (userCalories * 0.3);
        int userFats = (int) (userCalories * 0.2);
        int userCarbohydrates = (int) (userCalories * 0.5);
        int userGlasses = (int) (userWeight * 30 / 200);

        caloriesRes.setText(String.valueOf(userCalories));
        proteinsRes.setText(String.valueOf(userProtein));
        fatsRes.setText(String.valueOf(userFats));
        carbohydratesNum.setText(String.valueOf(userCarbohydrates));
        glassesNum.setText(String.valueOf(userGlasses));


    }
}