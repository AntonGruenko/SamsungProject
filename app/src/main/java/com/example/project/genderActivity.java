package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GenderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gender_check);
        RadioGroup genderRadio = (RadioGroup)findViewById(R.id.genderRadio);
        Button button4 = (Button) findViewById(R.id.button4);
        Bundle bundle = getIntent().getExtras();

        final Boolean[] isMale = new Boolean[1];
        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.male: isMale[0] = true;
                    break;
                    case R.id.female: isMale[0] = false;
                    break;
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(genderRadio.getCheckedRadioButtonId() != -1) {
                    bundle.putBoolean("gender", isMale[0]);
                    Intent intent = new Intent(GenderActivity.this, AgeActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Выберите свой пол", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
