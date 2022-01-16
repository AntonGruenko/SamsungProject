package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class heightActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        Button button2 = (Button) findViewById(R.id.button2);
        EditText editHeight = (EditText) findViewById(R.id.edit_Height);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(editHeight.getText().toString().equals(""))
                        && Integer.parseInt(editHeight.getText().toString()) > 100
                        && Integer.parseInt(editHeight.getText().toString()) < 210) {
                    String res = editHeight.getText().toString();
                    Intent intent = new Intent(heightActivity.this, weightActivity.class);
                    intent.putExtra("height", res);
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите корректное значение", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
}