package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DinnerActivity extends Activity {
    private static final String SETTINGS = "SharedPReferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        EditText editDinnerList = (EditText) findViewById(R.id.editDinnerList);
        EditText editDinnerKcal = (EditText) findViewById(R.id.editKcal);
        EditText editDinnerProteins = (EditText) findViewById(R.id.editProteins);
        EditText editDinnerFats = (EditText) findViewById(R.id.editFats);
        EditText editDinnerCarbohydrates = (EditText) findViewById(R.id.editCarbohydrates);
        Button saveButton = (Button) findViewById(R.id.enterButton);
        Button backButton = (Button) findViewById(R.id.backButton);
        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(editDinnerKcal.getText().toString().equals("") ||
                        editDinnerProteins.getText().toString().equals("") ||
                        editDinnerFats.getText().toString().equals("") ||
                        editDinnerCarbohydrates.getText().toString().equals(""))) {
                    int dinnerKcal = Integer.parseInt(String.valueOf(editDinnerKcal.getText()));
                    int dinnerProteins = Integer.parseInt(String.valueOf(editDinnerProteins.getText()));
                    int dinnerFats = Integer.parseInt(String.valueOf(editDinnerFats.getText()));
                    int dinnerCarbohydrates = Integer.parseInt(String.valueOf(editDinnerCarbohydrates.getText()));
                    Intent intent = new Intent(DinnerActivity.this, MainActivity.class);
                    e.putInt("kcalChange", dinnerKcal);
                    e.putInt("proteinsChange", dinnerProteins);
                    e.putInt("fatsChange", dinnerFats);
                    e.putInt("carbohydratesChange", dinnerCarbohydrates);
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
                Intent backIntent = new Intent(DinnerActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}