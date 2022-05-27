package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

//Первая активность с представлением приложения
public class StartActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button button = (Button) findViewById(R.id.button_next);
        button.setOnClickListener(view -> {
            intent = new Intent(StartActivity.this, HeightActivity.class);
            startActivity(intent);
        });
    }
}