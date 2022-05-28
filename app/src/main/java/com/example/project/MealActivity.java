package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project.data.Database;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MealActivity extends Activity {
    private static final String SETTINGS = "SharedPreferences";
    private Database db;
    private String[] adviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        TextView mealHeadline = findViewById(R.id.mealHeadline);
        AutoCompleteTextView dbDropDownMenu = findViewById(R.id.autocompleteTextView);
        EditText editCoefficient = findViewById(R.id.editCoefficient);
        EditText editBreakfastKcal = findViewById(R.id.editKcal);
        EditText editBreakfastProteins = findViewById(R.id.editProteins);
        EditText editBreakfastFats = findViewById(R.id.editFats);
        EditText editBreakfastCarbohydrates = findViewById(R.id.editCarbohydrates);
        TextView youHaveEatenText = findViewById(R.id.youHaveEatenText);
        Button enterButton = findViewById(R.id.enterButton);
        Button backButton = findViewById(R.id.backButton);
        Button adviceButton = findViewById(R.id.advice_button);

        adviceList = getResources().getStringArray(R.array.advices);


        switch (getIntent().getIntExtra("path", 4)){
            case(1):
                mealHeadline.setText(R.string.breakfast);
                adviceButton.setText(String.format("%s \n %s", getString(R.string.advice), adviceList[0]));
                break;
            case(2):
                mealHeadline.setText(R.string.lunch);
                adviceButton.setText(String.format("%s \n %s", getString(R.string.advice), adviceList[1]));
                break;
            case(3):
                mealHeadline.setText(R.string.dinner);
                adviceButton.setText(String.format("%s \n %s", getString(R.string.advice), adviceList[2]));
                break;
            case(4):
                mealHeadline.setText(R.string.snack);
                adviceButton.setText(String.format("%s \n %s", getString(R.string.advice), adviceList[3]));
                break;
        }

        final int[] kcal = {0};
        final int[] proteins = {0};
        final int[] fats = {0};
        final int[] carbohydrates = {0};

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        //Получение данных из базы данных продуктов
        ProductsDatabaseHelper dBHelper = new ProductsDatabaseHelper(this);
        db = new Database(this);

        try {
            dBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        SQLiteDatabase productsDb = dBHelper.getWritableDatabase();

        Cursor cursor = productsDb.rawQuery("SELECT * FROM products ORDER BY productTitle", null);
        cursor.moveToFirst();

        ArrayList<String> labels = new ArrayList<>();
        for(int i = 0; i < 95; i++){
            labels.add(cursor.getString(1));
            cursor.moveToNext();
            if(cursor.isAfterLast())
                break;
        }
        cursor.close();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, labels);
        dbDropDownMenu.setAdapter(adapter);

        dbDropDownMenu.setOnItemClickListener((parent, view, position, id) -> {
            String item = (String) parent.getItemAtPosition(position);
            int num = labels.indexOf(item);
            double coeff;
            Cursor menuCursor = productsDb.rawQuery("SELECT * FROM products ORDER BY productTitle", null);
            menuCursor.moveToPosition(num);
            try {
                coeff = Double.parseDouble(editCoefficient
                                .getText()
                                .toString()) * 0.01;
            }catch (Exception e1){
                coeff = 1;
            }
            kcal[0] +=(int) (menuCursor.getInt(2) * coeff);
            proteins[0] += (int) (menuCursor.getInt(3) * coeff);
            fats[0] += (int) (menuCursor.getInt(4) * coeff);
            carbohydrates[0] += (int) (menuCursor.getInt(5) * coeff);
            editBreakfastKcal.setText(String.valueOf(kcal[0]));
            editBreakfastProteins.setText(String.valueOf(proteins[0]));
            editBreakfastFats.setText(String.valueOf(fats[0]));
            editBreakfastCarbohydrates.setText(String.valueOf(carbohydrates[0]));

            youHaveEatenText.append(String.format(" %s,", menuCursor.getString(1)));

            menuCursor.close();
        });
        //Ввод данных в БД
        enterButton.setOnClickListener(view -> {
            if(!(editBreakfastKcal.getText().toString().equals("") ||
                    editBreakfastProteins.getText().toString().equals("") ||
                    editBreakfastFats.getText().toString().equals("") ||
                    editBreakfastCarbohydrates.getText().toString().equals(""))) {
                int breakfastKcal = Integer.parseInt(String.valueOf(editBreakfastKcal.getText()));
                int breakfastProteins = Integer.parseInt(String.valueOf(editBreakfastProteins.getText()));
                int breakfastFats= Integer.parseInt(String.valueOf(editBreakfastFats.getText()));
                int breakfastCarbohydrates = Integer.parseInt(String.valueOf(editBreakfastCarbohydrates.getText()));
                Intent intent = new Intent(MealActivity.this, MainActivity.class);

                long date = sp.getLong("date", 0);

                int kcalBefore = db.select(date).getKcal();
                int proteinsBefore = db.select(date).getProteins();
                int fatsBefore = db.select(date).getFats();
                int carbohydratesBefore = db.select(date).getCarbohydrates();

                int insertKcal = kcalBefore + breakfastKcal;
                int insertProteins = proteinsBefore + breakfastProteins;
                int insertFats = fatsBefore + breakfastFats;
                int insertCarbohydrates = carbohydratesBefore + breakfastCarbohydrates;

                int dailyKcal = sp.getInt("kcal", 0);
                int dailyProteins = sp.getInt("proteins", 0);
                int dailyFats = sp.getInt("fats", 0);
                int dailyCarbohydrates = sp.getInt("carbohydrates", 0);

                int kcalSuccessRange = (int) (sp.getInt("kcal", 0) * 0.2);
                int proteinsSuccessRange = (int) (sp.getInt("proteins", 0) * 0.1);
                int fatsSuccessRange = (int) (sp.getInt("fats", 0) * 0.1);
                int carbohydratesSuccessRange = (int) (sp.getInt("carbohydrates", 0) * 0.1);
                int glassesSuccessRange = sp.getInt("glasses", 0) - 1;
                boolean isSuccessful = (dailyKcal - insertKcal <= kcalSuccessRange && dailyKcal - insertKcal >= -kcalSuccessRange) &&
                        (dailyProteins - insertProteins <= proteinsSuccessRange && dailyProteins - insertProteins >= -proteinsSuccessRange) &&
                        (dailyFats - insertFats <= fatsSuccessRange && dailyFats - insertFats >= -fatsSuccessRange) &&
                        (dailyCarbohydrates - insertCarbohydrates <= carbohydratesSuccessRange && dailyCarbohydrates - insertCarbohydrates >= -carbohydratesSuccessRange) &&
                        (db.select(date).getGlasses() >= glassesSuccessRange);
                //Проверка успешности дня
                if(db.select(date).equals(new Days(0,  0, 0, 0, 0, 0, false))) {
                    db.insert(date,
                            insertKcal,
                            insertProteins,
                            insertFats,
                            insertCarbohydrates,
                            db.select(date).getGlasses(),
                            isSuccessful);
                }else{
                    db.update(new Days(date,
                            insertKcal,
                            insertProteins,
                            insertFats,
                            insertCarbohydrates,
                            db.select(date).getGlasses(),
                            isSuccessful));
                }
                e.apply();
                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(), R.string.enterValue, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        backButton.setOnClickListener(view -> {
            Intent backIntent = new Intent(MealActivity.this, MainActivity.class);
            startActivity(backIntent);
        });

        adviceButton.setOnClickListener(v -> {
            Random generator = new Random();
            int index = generator.nextInt(adviceList.length);
            adviceButton.setText(String.format("%s \n %s", getString(R.string.advice), adviceList[index]));
        });
    }
}