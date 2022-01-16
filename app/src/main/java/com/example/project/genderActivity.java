package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class genderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userHeight = intent.getStringExtra("height");
        String userWeight = intent.getStringExtra("weight");
        setContentView(R.layout.gender_check);
        RadioGroup genderRadio = (RadioGroup)findViewById(R.id.genderRadio);
        Button button4 = (Button) findViewById(R.id.button4);

        final String[] isMale = new String[1];
        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.male: isMale[0] = "true";
                    break;
                    case R.id.female: isMale[0] = "false";
                    break;
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(genderRadio.getCheckedRadioButtonId() != -1) {
                    Intent intent = new Intent(genderActivity.this, ageActivity.class);
                    intent.putExtra("height",userHeight);
                    intent.putExtra("weight", userWeight);
                    intent.putExtra("gender", isMale[0]);
                    startActivity(intent);
                }
            }
        });
    }

}
