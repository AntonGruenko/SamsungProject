package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//Активность для ввода роста пользователя
public class HeightActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        Button button = findViewById(R.id.button_next);
        EditText editHeight = findViewById(R.id.edit_height);

        button.setOnClickListener(view -> {
            //Проверка корректности введённого значения
            if(!(editHeight.getText().toString().equals(""))
                    && Integer.parseInt(editHeight.getText().toString()) > 100
                    && Integer.parseInt(editHeight.getText().toString()) < 210) {
                String res = editHeight.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putInt("height", Integer.parseInt(res));
                Intent intent = new Intent(HeightActivity.this, WeightActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(), R.string.enterCorrectValue, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}