package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity2 extends Activity {

    public int kcalRemain = 0;
    public int proteinsRemain = 0;
    public int fatsRemain = 0;
    public int carbohydratesRemain = 0;
    public boolean hasStartedFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main2);

        TextView kcalText = (TextView) findViewById(R.id.KcalRemain);
        TextView proteinsText = (TextView) findViewById(R.id.proteinsRemain);
        TextView fatsText = (TextView) findViewById(R.id.fatsRemain);
        TextView carbohydratesText = (TextView) findViewById(R.id.carbohydratesRemain);

        Button breakfastButton = (Button) findViewById(R.id.breakfastButton);
        Button lunchButton = (Button) findViewById(R.id.lunchButton);
        Button dinerButton = (Button) findViewById(R.id.dinerButton);
        Button snackButton = (Button) findViewById(R.id.snackButton);

        Intent intent = getIntent();

        TextView date = (TextView) findViewById(R.id.date);

        String currentDateString = DateFormat.getDateInstance().format(new Date());
        date.setText(currentDateString);

        kcalRemain = Integer.parseInt(intent.getStringExtra("Kcal"));
        proteinsRemain = Integer.parseInt(intent.getStringExtra("Proteins"));
        fatsRemain = Integer.parseInt(intent.getStringExtra("Fats"));
        carbohydratesRemain = Integer.parseInt(intent.getStringExtra("Carbohydrates"));

        kcalText.setText(String.valueOf(kcalRemain));
        proteinsText.setText(String.valueOf(proteinsRemain));
        fatsText.setText(String.valueOf(fatsRemain));
        carbohydratesText.setText(String.valueOf(carbohydratesRemain));

        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, BreakfastActivity.class);
                startActivity(intent);
                hasStartedFlag = true;
            }
        });
        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, LunchActivity.class);
                startActivity(intent);
                hasStartedFlag = true;
            }
        });
        dinerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, DinerActivity.class);
                startActivity(intent);
                hasStartedFlag = true;
            }
        });
        snackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, SnackActivity.class);
                startActivity(intent);
                hasStartedFlag = true;
            }
        });



    }

    /*@Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main2);

        TextView kcalText = (TextView) findViewById(R.id.KcalRemain);
        TextView proteinsText = (TextView) findViewById(R.id.proteinsRemain);
        TextView fatsText = (TextView) findViewById(R.id.fatsRemain);
        TextView carbohydratesText = (TextView) findViewById(R.id.carbohydratesRemain);

        Button breakfastButton = (Button) findViewById(R.id.breakfastButton);
        Button lunchButton = (Button) findViewById(R.id.lunchButton);
        Button dinerButton = (Button) findViewById(R.id.dinerButton);
        Button snackButton = (Button) findViewById(R.id.snackButton);

        Intent intent = getIntent();

        TextView date = (TextView) findViewById(R.id.date);

        String currentDateString = DateFormat.getDateInstance().format(new Date());
        date.setText(currentDateString);


        int kcalChange = 0;
        int proteinsChange = 0;
        int fatsChange = 0;
        int carbohydratesChange = 0;


        kcalChange = Integer.parseInt(intent.getStringExtra("Kcal"));
        proteinsChange = Integer.parseInt(intent.getStringExtra("Proteins"));
        fatsChange = Integer.parseInt(intent.getStringExtra("Fats"));
        carbohydratesChange = Integer.parseInt(intent.getStringExtra("Carbohydrates"));


        proteinsRemain = proteinsRemain - proteinsChange;
        fatsRemain = fatsRemain - fatsChange;
        carbohydratesRemain = carbohydratesRemain - carbohydratesChange;

        kcalText.setText(String.valueOf(kcalRemain));
        proteinsText.setText(String.valueOf(proteinsRemain));
        fatsText.setText(String.valueOf(fatsRemain));
        carbohydratesText.setText(String.valueOf(carbohydratesRemain));









        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, BreakfastActivity.class);
                startActivity(intent);
                hasStartedFlag = true;
            }
        });
        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, LunchActivity.class);
                startActivity(intent);
                hasStartedFlag = true;
            }
        });
        dinerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, DinerActivity.class);
                startActivity(intent);
                hasStartedFlag = true;
            }
        });
        snackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, SnackActivity.class);
                startActivity(intent);
                hasStartedFlag = true;
            }
        });
    }*/
}