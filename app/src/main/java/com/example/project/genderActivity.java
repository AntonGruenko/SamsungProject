package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
//Активность для выбора пола пользователя
public class GenderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        RadioGroup genderRadio = findViewById(R.id.genderRadio);
        Button button4 = findViewById(R.id.button_next);
        Bundle bundle = getIntent().getExtras();

        final Boolean[] isMale = new Boolean[1];
        genderRadio.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.male: isMale[0] = true;
                break;
                case R.id.female: isMale[0] = false;
                break;
            }
        });

        button4.setOnClickListener(view -> {
            if(genderRadio.getCheckedRadioButtonId() != -1) {
                bundle.putBoolean("gender", isMale[0]);
                Intent intent = new Intent(GenderActivity.this, AgeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), R.string.ChooseYourGender, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}
