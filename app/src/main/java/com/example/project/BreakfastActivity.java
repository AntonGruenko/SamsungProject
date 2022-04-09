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

public class BreakfastActivity extends Activity {
    private static final String SETTINGS = "SharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        EditText editBreakfastList = (EditText) findViewById(R.id.editBreakfastList);
        EditText editBreakfastKcal = (EditText) findViewById(R.id.editKcal);
        EditText editBreakfastProteins = (EditText) findViewById(R.id.editProteins);
        EditText editBreakfastFats = (EditText) findViewById(R.id.editFats);
        EditText editBreakfastCarbohydrates = (EditText) findViewById(R.id.editCarbohydrates);
        Button enterButton = (Button) findViewById(R.id.enterButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(editBreakfastKcal.getText().toString().equals("") ||
                        editBreakfastProteins.getText().toString().equals("") ||
                        editBreakfastFats.getText().toString().equals("") ||
                        editBreakfastCarbohydrates.getText().toString().equals(""))) {
                    int breakfastKcal = Integer.parseInt(String.valueOf(editBreakfastKcal.getText()));
                    int breakfastProteins = Integer.parseInt(String.valueOf(editBreakfastProteins.getText()));
                    int breakfastFats= Integer.parseInt(String.valueOf(editBreakfastFats.getText()));
                    int breakfastCarbohydrates = Integer.parseInt(String.valueOf(editBreakfastCarbohydrates.getText()));
                    Intent intent = new Intent(BreakfastActivity.this, MainActivity.class);
                    e.putInt("kcalChange", breakfastKcal);
                    e.putInt("proteinsChange", breakfastProteins);
                    e.putInt("fatsChange", breakfastFats);
                    e.putInt("carbohydratesChange", breakfastCarbohydrates);
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
            public void onClick(View view){
                Intent backIntent = new Intent(BreakfastActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}