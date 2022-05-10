package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WeightActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        Button button3 = (Button) findViewById(R.id.button3);
        EditText editWeight = (EditText) findViewById(R.id.edit_Weight);
        Bundle bundle = getIntent().getExtras();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
    }
}