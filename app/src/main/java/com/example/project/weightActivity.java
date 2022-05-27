package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//Активность для ввода веса пользователя
public class WeightActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        Button button = findViewById(R.id.button_next);
        EditText editWeight = findViewById(R.id.edit_Weight);
        Bundle bundle = getIntent().getExtras();

        button.setOnClickListener(view -> {
            //Проверка корректности введённого зачения
            if (!(editWeight.getText().toString().equals(""))
                    && Integer.parseInt(editWeight.getText().toString()) > 25
                    && Integer.parseInt(editWeight.getText().toString()) < 200) {
                String res = editWeight.getText().toString();
                bundle.putInt("weight", Integer.parseInt(res));
                Intent intent = new Intent(WeightActivity.this, GenderActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.enterCorrectValue, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}