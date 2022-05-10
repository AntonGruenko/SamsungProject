package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project.data.Database;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private int kcalRemain;
    private int proteinsRemain;
    private int fatsRemain;
    private int carbohydratesRemain;
    Database db;
    private static final String SETTINGS = "SharedPreferences";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,null);

        TextView dateText = (TextView) v.findViewById(R.id.date);
        TextView kcalText = (TextView) v.findViewById(R.id.KcalRemain);
        TextView proteinsText = (TextView) v.findViewById(R.id.proteinsRemain);
        TextView fatsText = (TextView) v.findViewById(R.id.fatsRemain);
        TextView carbohydratesText = (TextView) v.findViewById(R.id.carbohydratesRemain);
        TextView kcalInfoText = (TextView) v.findViewById(R.id.caloriesRemain);
        TextView proteinsInfoText = (TextView) v.findViewById(R.id.proteins);
        TextView fatsInfoText = (TextView) v.findViewById(R.id.fats);
        TextView carbohydratesInfoText = (TextView) v.findViewById(R.id.carbohydrates);

        Button breakfastButton = (Button) v.findViewById(R.id.breakfastButton);
        Button lunchButton = (Button) v.findViewById(R.id.lunchButton);
        Button dinnerButton = (Button) v.findViewById(R.id.dinnerButton);
        Button snackButton = (Button) v.findViewById(R.id.snackButton);
        Button waterButton = (Button) v.findViewById(R.id.waterButton);
        Button backButton = (Button) v.findViewById(R.id.backButton);
        Button forwardButton = (Button) v.findViewById(R.id.forwardButton);

        SharedPreferences sp = this.getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        long date = sp.getLong("date", 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        dateText.setText(String.format("%1$td.%1$tm.%1$tY", calendar));

        db = new Database(getActivity().getApplicationContext());

        kcalRemain = sp.getInt("kcal", 0) - db.select(date).getKcal();
        proteinsRemain = sp.getInt("proteins", 0) - db.select(date).getProteins();
        fatsRemain = sp.getInt("fats", 0) - db.select(date).getFats();
        carbohydratesRemain = sp.getInt("carbohydrates", 0) - db.select(date).getCarbohydrates();

        if(kcalRemain >= 0){
            kcalText.setText(String.valueOf(kcalRemain));
        }else{
            kcalText.setText(String.valueOf(Math.abs(kcalRemain)));
            kcalInfoText.setText(R.string.kcalExcess);
        }
        if(proteinsRemain >= 0) {
            proteinsText.setText(String.valueOf(proteinsRemain));
        }else{
            String text = getString(R.string.proteins) + "\n" + "(" + getString(R.string.excess) + ")";
            proteinsInfoText.setText(text);
            proteinsText.setText(String.valueOf(Math.abs(proteinsRemain)));
        }
        if(fatsRemain >= 0){
            fatsText.setText(String.valueOf(fatsRemain));
        }else{
            String text = getString(R.string.fats) + "\n" + "(" + getString(R.string.excess) + ")";
            fatsInfoText.setText(text);
            fatsText.setText(String.valueOf(Math.abs(fatsRemain)));
        }
        if(carbohydratesRemain >= 0){
            carbohydratesText.setText(String.valueOf(carbohydratesRemain));
        }else{
            String text = getString(R.string.carbohydrates) + "\n" + "(" + getString(R.string.excess) + ")";
            carbohydratesInfoText.setText(text);
            carbohydratesText.setText(String.valueOf(Math.abs(carbohydratesRemain)));
        }

        waterButton.setText(String.format("%s/%s", db.select(date).getGlasses(), sp.getInt("glasses", 0)));

        breakfastButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), BreakfastActivity.class);
            intent.putExtra("path", 1);
            startActivity(intent);
        });
        lunchButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), BreakfastActivity.class);
            intent.putExtra("path", 2);
            startActivity(intent);
        });
        dinnerButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), BreakfastActivity.class);
            intent.putExtra("path", 3);
            startActivity(intent);
        });
        snackButton.setOnClickListener(view ->{
            Intent intent = new Intent(getActivity(), BreakfastActivity.class);
            intent.putExtra("path", 4);
            startActivity(intent);
        });

        waterButton.setOnClickListener(view -> {
            e.putInt("glassesRemain",
                    sp.getInt("glassesRemain", 0) + 1);
            e.commit();
            int newGlasses = db.select(sp.getLong("date", 0)).getGlasses()+1;
            if(db.select(sp.getLong("date", 0)).equals(new Days(0,  0, 0, 0, 0, 0, false))) {
                db.insert(sp.getLong("date", 1),
                        db.select(sp.getLong("date", 0)).getKcal(),
                        db.select(sp.getLong("date", 0)).getProteins(),
                        db.select(sp.getLong("date", 0)).getFats(),
                        db.select(sp.getLong("date", 0)).getCarbohydrates(),
                        newGlasses,
                        db.select(sp.getLong("date", 0)).isSuccessful());
            }else{
                db.update(new Days(sp.getLong("date", 1),
                        db.select(sp.getLong("date", 0)).getKcal(),
                        db.select(sp.getLong("date", 0)).getProteins(),
                        db.select(sp.getLong("date", 0)).getFats(),
                        db.select(sp.getLong("date", 0)).getCarbohydrates(),
                        newGlasses,
                        db.select(sp.getLong("date", 0)).isSuccessful()));
            }
            waterButton.setText(String.format("%s/%s", newGlasses, sp.getInt("glasses", 0)));
        });

        backButton.setOnClickListener(view ->{
            calendar.setTimeInMillis(sp.getLong("date", 0));
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            e.putLong("date", calendar.getTimeInMillis());
            e.commit();

            long newDate = calendar.getTimeInMillis();
            dateText.setText(String.format("%1$td.%1$tm.%1$tY", calendar));
            kcalRemain = sp.getInt("kcal", 0) - db.select(newDate).getKcal();
            proteinsRemain = sp.getInt("proteins", 0) - db.select(newDate).getProteins();
            fatsRemain = sp.getInt("fats", 0) - db.select(newDate).getFats();
            carbohydratesRemain = sp.getInt("carbohydrates", 0) - db.select(newDate).getCarbohydrates();
            kcalText.setText(String.valueOf(kcalRemain));
            proteinsText.setText(String.valueOf(proteinsRemain));
            fatsText.setText(String.valueOf(fatsRemain));
            carbohydratesText.setText(String.valueOf(carbohydratesRemain));
            waterButton.setText(String.format("%s/%s", db.select(newDate).getGlasses(), sp.getInt("glasses", 0)));
        });

        forwardButton.setOnClickListener(view ->{
            calendar.setTimeInMillis(sp.getLong("date", 0));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            e.putLong("date", calendar.getTimeInMillis());
            e.commit();
            long newDate = calendar.getTimeInMillis();
            dateText.setText(String.format("%1$td.%1$tm.%1$tY", calendar));
            kcalRemain = sp.getInt("kcal", 0) - db.select(newDate).getKcal();
            proteinsRemain = sp.getInt("proteins", 0) - db.select(newDate).getProteins();
            fatsRemain = sp.getInt("fats", 0) - db.select(newDate).getFats();
            carbohydratesRemain = sp.getInt("carbohydrates", 0) - db.select(newDate).getCarbohydrates();
            kcalText.setText(String.valueOf(kcalRemain));
            proteinsText.setText(String.valueOf(proteinsRemain));
            fatsText.setText(String.valueOf(fatsRemain));
            carbohydratesText.setText(String.valueOf(carbohydratesRemain));
            waterButton.setText(String.format("%s/%s", db.select(newDate).getGlasses(), sp.getInt("glasses", 0)));
        });
        return v;

    }
}