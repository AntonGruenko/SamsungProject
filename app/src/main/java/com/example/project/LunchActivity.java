package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LunchActivity extends Activity {
    private static final String SETTINGS = "SharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        EditText editLunchList = (EditText) findViewById(R.id.editLunchList);
        EditText editLunchKcal = (EditText) findViewById(R.id.editKcal);
        EditText editLunchProteins = (EditText) findViewById(R.id.editProteins);
        EditText editLunchFats = (EditText) findViewById(R.id.editFats);
        EditText editLunchCarbohydrates = (EditText) findViewById(R.id.editCarbohydrates);
        Button saveButton = (Button) findViewById(R.id.enterButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lunchKcal = Integer.parseInt(String.valueOf(editLunchKcal.getText()));
                int lunchProteins = Integer.parseInt(String.valueOf(editLunchProteins.getText()));
                int lunchFats = Integer.parseInt(String.valueOf(editLunchFats.getText()));
                int lunchCarbohydrates = Integer.parseInt(String.valueOf(editLunchCarbohydrates.getText()));
                Intent intent = new Intent(LunchActivity.this, MainActivity.class);
                if(!(editLunchKcal.getText().toString().equals("") ||
                        editLunchProteins.getText().toString().equals("") ||
                        editLunchFats.getText().toString().equals("") ||
                        editLunchCarbohydrates.getText().toString().equals(""))){
                    e.putInt("kcalChange", lunchKcal);
                    e.putInt("proteinsChange", lunchProteins);
                    e.putInt("fatsChange", lunchFats);
                    e.putInt("carbohydratesChange", lunchCarbohydrates);
                    e.commit();
                startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.enterValue, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(LunchActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}