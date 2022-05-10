package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.data.Database;

import java.io.IOException;

public class BreakfastActivity extends Activity {
    private static final String SETTINGS = "SharedPreferences";
    private ProductsDatabaseHelper dBHelper;
    private SQLiteDatabase productsDb;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        TextView mealHeadline = (TextView) findViewById(R.id.mealHeadline);
        AutoCompleteTextView dbDropDownMenu = (AutoCompleteTextView) findViewById(R.id.autocompleteTextView);
        EditText editCoefficient = (EditText) findViewById(R.id.editCoefficient);
        EditText editBreakfastKcal = (EditText) findViewById(R.id.editKcal);
        EditText editBreakfastProteins = (EditText) findViewById(R.id.editProteins);
        EditText editBreakfastFats = (EditText) findViewById(R.id.editFats);
        EditText editBreakfastCarbohydrates = (EditText) findViewById(R.id.editCarbohydrates);
        TextView youHaveEatenText = (TextView) findViewById(R.id.youHaveEatenText);
        Button enterButton = (Button) findViewById(R.id.enterButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        switch (getIntent().getIntExtra("path", 4)){
            case(1):
                mealHeadline.setText(R.string.breakfast);
                break;
            case(2):
                mealHeadline.setText(R.string.lunch);
                break;
            case(3):
                mealHeadline.setText(R.string.dinner);
                break;
            case(4):
                mealHeadline.setText(R.string.snack);
                break;
        }

        final int[] kcal = {0};
        final int[] proteins = {0};
        final int[] fats = {0};
        final int[] carbohydrates = {0};

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        dBHelper = new ProductsDatabaseHelper(this);
        db = new Database(this);

        try {
            dBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            productsDb = dBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Cursor cursor = productsDb.rawQuery("SELECT * FROM products ORDER BY productTitle", null);
        cursor.moveToFirst();

        String labels[] = new String[95];
        for(int i = 0; i < 95; i++){
            labels[i] = cursor.getString(1);
            cursor.moveToNext();
            if(cursor.isAfterLast())
                break;
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, labels);
        dbDropDownMenu.setAdapter(adapter);

        dbDropDownMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                double coeff;
                Cursor cursor = productsDb.rawQuery("SELECT * FROM products ORDER BY productTitle", null);
                cursor.moveToPosition(position);
                try {
                    coeff = Double.valueOf(editCoefficient
                                    .getText()
                                    .toString()) * 0.01;
                }catch (Exception e){
                    coeff = 1;
                }
                kcal[0] +=(int) (cursor.getInt(2) * coeff);
                proteins[0] += (int) (cursor.getInt(3) * coeff);
                fats[0] += (int) (cursor.getInt(4) * coeff);
                carbohydrates[0] += cursor.getInt(5);
                editBreakfastKcal.setText(String.valueOf(kcal[0]));
                editBreakfastProteins.setText(String.valueOf(proteins[0]));
                editBreakfastFats.setText(String.valueOf(fats[0]));
                editBreakfastCarbohydrates.setText(String.valueOf(carbohydrates[0]));

                youHaveEatenText.append(String.format(" %s,", cursor.getString(1)));

                cursor.close();
            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(editBreakfastKcal.getText().toString().equals("") ||
                        editBreakfastProteins.getText().toString().equals("") ||
                        editBreakfastFats.getText().toString().equals("") ||
                        editBreakfastCarbohydrates.getText().toString().equals(""))) {
                    int breakfastKcal = Integer.parseInt(String.valueOf(editBreakfastKcal.getText()));
                    int breakfastProteins = Integer.parseInt(String.valueOf(editBreakfastProteins.getText()));
                    int breakfastFats= Integer.parseInt(String.valueOf(editBreakfastFats.getText()));
                    int breakfastCarbohydrates = Integer.parseInt(String.valueOf(editBreakfastCarbohydrates.getText()));
                    Intent intent = new Intent(BreakfastActivity.this, MainActivity.class);

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
                    boolean isSuccessful = false;

                    if((dailyKcal - insertKcal <= kcalSuccessRange && dailyKcal - insertKcal >= -kcalSuccessRange) &&
                            (dailyProteins - insertProteins <= proteinsSuccessRange && dailyProteins - insertProteins >= -proteinsSuccessRange) &&
                            (dailyFats - insertFats <= fatsSuccessRange && dailyFats - insertFats >= -fatsSuccessRange) &&
                            (dailyCarbohydrates - insertCarbohydrates <= carbohydratesSuccessRange && dailyCarbohydrates - insertCarbohydrates >= -carbohydratesSuccessRange) &&
                            (db.select(date).getGlasses() >= glassesSuccessRange)){
                        isSuccessful = true;
                    }
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
                    e.commit();
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.enterValue, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent backIntent = new Intent(BreakfastActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}