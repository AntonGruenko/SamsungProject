package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class weightActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_check);
        Button button3 = (Button) findViewById(R.id.button3);
        EditText editWeight = (EditText) findViewById(R.id.edit_Weight);
        Intent intent = getIntent();
        String userHeight = intent.getStringExtra("height");

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(editWeight.getText().toString().equals(""))
                        && Integer.parseInt(editWeight.getText().toString()) > 25
                        && Integer.parseInt(editWeight.getText().toString()) < 200) {
                    String res = editWeight.getText().toString();
                    Intent intent = new Intent(weightActivity.this, genderActivity.class);
                    intent.putExtra("height", userHeight);
                    intent.putExtra("weight", res);
                    startActivity(intent);
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите корректное значение", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}