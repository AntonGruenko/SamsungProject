package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);

        Button button5 = (Button) findViewById(R.id.button5);
        EditText editAge = (EditText) findViewById(R.id.edit_Age);
        Bundle bundle = getIntent().getExtras();

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(editAge.getText().toString().equals(""))
                        && Integer.parseInt(editAge.getText().toString()) > 0
                        && Integer.parseInt(editAge.getText().toString()) < 105) {
                    int res = Integer.parseInt(editAge.getText().toString());
                    bundle.putInt("age", res);
                    Intent intent = new Intent(AgeActivity.this, ActivenessActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите корректное значение", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
