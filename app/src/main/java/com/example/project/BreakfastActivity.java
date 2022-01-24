package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BreakfastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        EditText editBreakfastList = (EditText) findViewById(R.id.breakfastList);
        EditText editBreakfastKcal = (EditText) findViewById(R.id.breakfastKcal);
        EditText editBreakfastProteins = (EditText) findViewById(R.id.breakfastProteins);
        EditText editBreakfastFats = (EditText) findViewById(R.id.breakfastFats);
        EditText editBreakfastCarbohydrates = (EditText) findViewById(R.id.breakfastCarbohydrates);
        Button backButton = (Button) findViewById(R.id.backButton);

        final String[] breakfastKcal = new String[1];
        final String[] breakfastProteins = new String[1];
        final String[] breakfastFats = new String[1];
        final String[] breakfastCarbohydrates = new String[1];


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                breakfastKcal[0] = String.valueOf(editBreakfastKcal.getText());
                breakfastProteins[0] = String.valueOf(editBreakfastProteins.getText());
                breakfastFats[0] = String.valueOf(editBreakfastFats.getText());
                breakfastCarbohydrates[0] = String.valueOf(editBreakfastCarbohydrates.getText());
                Intent intent = new Intent(BreakfastActivity.this, MainActivity2.class);
                intent.putExtra("Kcal", breakfastKcal[0]);
                intent.putExtra("Proteins", breakfastProteins[0]);
                intent.putExtra("Fats", breakfastFats[0]);
                intent.putExtra("Carbohydrates", breakfastCarbohydrates[0]);
                startActivity(intent);

            }
        });
    }
}