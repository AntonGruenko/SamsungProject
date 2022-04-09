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

public class SnackActivity extends Activity {
    private static final String SETTINGS = "SharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack);

        EditText editSnackList = (EditText) findViewById(R.id.editSnackList);
        EditText editSnackKcal = (EditText) findViewById(R.id.editKcal);
        EditText editSnackProteins = (EditText) findViewById(R.id.editProteins);
        EditText editSnackFats = (EditText) findViewById(R.id.editFats);
        EditText editSnackCarbohydrates = (EditText) findViewById(R.id.editCarbohydrates);
        Button saveButton = (Button) findViewById(R.id.enterButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int snackKcal = Integer.parseInt(String.valueOf(editSnackKcal.getText()));
                int snackProteins = Integer.parseInt(String.valueOf(editSnackProteins.getText()));
                int snackFats = Integer.parseInt(String.valueOf(editSnackFats.getText()));
                int snackCarbohydrates = Integer.parseInt(String.valueOf(editSnackCarbohydrates.getText()));
                Intent intent = new Intent(SnackActivity.this, MainActivity.class);
                if(!(editSnackKcal.getText().toString().equals("") ||
                        editSnackProteins.getText().toString().equals("") ||
                        editSnackFats.getText().toString().equals("") ||
                        editSnackCarbohydrates.getText().toString().equals("")))
                e.putInt("kcalChange", snackKcal);
                e.putInt("proteinsChange", snackProteins);
                e.putInt("fatsChange", snackFats);
                e.putInt("carbohydratesChange", snackCarbohydrates);
                e.commit();

                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(SnackActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}