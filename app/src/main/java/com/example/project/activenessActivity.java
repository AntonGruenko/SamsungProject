package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class ActivenessActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activeness);

        Button button6 = (Button) findViewById(R.id.button6);
        RadioGroup activenessRadio = (RadioGroup) findViewById(R.id.activenessRadio);
        Bundle bundle = getIntent().getExtras();

        final double[] userActiveness = new double[1];
        activenessRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.activenessVariant1: userActiveness[0] = 1.2;
                        break;
                    case R.id.activenessVariant2: userActiveness[0] = 1.38;
                        break;
                    case R.id.activenessVariant3: userActiveness[0] = 1.46;
                        break;
                    case R.id.activenessVariant4: userActiveness[0] = 1.55;
                        break;
                    case R.id.activenessVariant5: userActiveness[0] = 1.64;
                        break;
                    case R.id.activenessVariant6: userActiveness[0] = 1.73;
                        break;
                    case R.id.activenessVariant7: userActiveness[0] = 1.9;
                        break;
                }
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activenessRadio.getCheckedRadioButtonId() != -1) {
                    bundle.putDouble("activeness",userActiveness[0]);
                    Intent intent = new Intent(ActivenessActivity.this, ResultActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}