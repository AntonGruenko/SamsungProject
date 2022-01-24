package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.age_check);

        Intent intent = getIntent();
        String userHeight = intent.getStringExtra("height");
        String userWeight = intent.getStringExtra("weight");
        String userGender = intent.getStringExtra("gender");

        Button button5 = (Button) findViewById(R.id.button5);
        EditText editAge = (EditText) findViewById(R.id.edit_Age);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(editAge.getText().toString().equals(""))
                        && Integer.parseInt(editAge.getText().toString()) > 0
                        && Integer.parseInt(editAge.getText().toString()) < 105) {
                    String res = editAge.getText().toString();
                    Intent intent = new Intent(ageActivity.this, activenessActivity.class);
                    intent.putExtra("height", userHeight);
                    intent.putExtra("weight", userWeight);
                    intent.putExtra("gender", userGender);
                    intent.putExtra("age", res);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите корректное значение", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
