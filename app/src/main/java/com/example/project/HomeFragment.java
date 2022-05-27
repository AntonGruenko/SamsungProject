package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.project.data.Database;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private int kcalRemain;
    private int proteinsRemain;
    private int fatsRemain;
    private int carbohydratesRemain;
    private Database db;
    private static final String SETTINGS = "SharedPreferences";

    private TextView kcalText;
    private TextView proteinsText;
    private TextView fatsText;
    private TextView carbohydratesText;
    private TextView kcalInfoText;
    private TextView proteinsInfoText;
    private TextView fatsInfoText;
    private TextView carbohydratesInfoText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, null);
        TextView dateText = v.findViewById(R.id.date);

        kcalText = v.findViewById(R.id.KcalRemain);
        proteinsText = v.findViewById(R.id.proteinsRemain);
        fatsText = v.findViewById(R.id.fatsRemain);
        carbohydratesText = v.findViewById(R.id.carbohydratesRemain);
        kcalInfoText = v.findViewById(R.id.caloriesRemain);
        proteinsInfoText = v.findViewById(R.id.proteins);
        fatsInfoText = v.findViewById(R.id.fats);
        carbohydratesInfoText = v.findViewById(R.id.carbohydrates);

        Button breakfastButton = v.findViewById(R.id.breakfastButton);
        Button lunchButton = v.findViewById(R.id.lunchButton);
        Button dinnerButton = v.findViewById(R.id.dinnerButton);
        Button snackButton = v.findViewById(R.id.snackButton);
        Button waterButton = v.findViewById(R.id.waterButton);
        Button backButton = v.findViewById(R.id.backButton);
        Button forwardButton = v.findViewById(R.id.forwardButton);

        SharedPreferences sp = this.getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        long date = sp.getLong("date", 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        dateText.setText(String.format(Locale.ENGLISH,"%1$td.%1$tm.%1$tY", calendar));

        db = new Database(getActivity().getApplicationContext());

        kcalRemain = sp.getInt("kcal", 0) - db.select(date).getKcal();
        proteinsRemain = sp.getInt("proteins", 0) - db.select(date).getProteins();
        fatsRemain = sp.getInt("fats", 0) - db.select(date).getFats();
        carbohydratesRemain = sp.getInt("carbohydrates", 0) - db.select(date).getCarbohydrates();

        changeDescription(v);

        waterButton.setText(String.format("%s/%s", db.select(date).getGlasses(), sp.getInt("glasses", 0)));

        breakfastButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MealActivity.class);
            intent.putExtra("path", 1);
            startActivity(intent);
        });
        lunchButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MealActivity.class);
            intent.putExtra("path", 2);
            startActivity(intent);
        });
        dinnerButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MealActivity.class);
            intent.putExtra("path", 3);
            startActivity(intent);
        });
        snackButton.setOnClickListener(view ->{
            Intent intent = new Intent(getActivity(), MealActivity.class);
            intent.putExtra("path", 4);
            startActivity(intent);
        });
        //Добавление стакана воды
        waterButton.setOnClickListener(view -> {
            e.putInt("glassesRemain",
                    sp.getInt("glassesRemain", 0) + 1);
            e.apply();
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
        //Изменение даты
        backButton.setOnClickListener(view ->{
            calendar.setTimeInMillis(sp.getLong("date", 0));
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            e.putLong("date", calendar.getTimeInMillis());
            e.apply();
            long newDate = calendar.getTimeInMillis();
            dateText.setText(String.format(Locale.ENGLISH, "%1$td.%1$tm.%1$tY", calendar));
            kcalRemain = sp.getInt("kcal", 0) - db.select(newDate).getKcal();
            proteinsRemain = sp.getInt("proteins", 0) - db.select(newDate).getProteins();
            fatsRemain = sp.getInt("fats", 0) - db.select(newDate).getFats();
            carbohydratesRemain = sp.getInt("carbohydrates", 0) - db.select(newDate).getCarbohydrates();
            waterButton.setText(String.format("%s/%s", db.select(newDate).getGlasses(), sp.getInt("glasses", 0)));
            changeDescription(v);
        });

        forwardButton.setOnClickListener(view ->{
            calendar.setTimeInMillis(sp.getLong("date", 0));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            e.putLong("date", calendar.getTimeInMillis());
            e.apply();
            long newDate = calendar.getTimeInMillis();
            dateText.setText(String.format(Locale.ENGLISH, "%1$td.%1$tm.%1$tY", calendar));
            kcalRemain = sp.getInt("kcal", 0) - db.select(newDate).getKcal();
            proteinsRemain = sp.getInt("proteins", 0) - db.select(newDate).getProteins();
            fatsRemain = sp.getInt("fats", 0) - db.select(newDate).getFats();
            carbohydratesRemain = sp.getInt("carbohydrates", 0) - db.select(newDate).getCarbohydrates();
            waterButton.setText(String.format("%s/%s", db.select(newDate).getGlasses(), sp.getInt("glasses", 0)));
            changeDescription(v);
        });
        return v;

    }
    //Изменение количества калорий и питательных веществ, а также описания
    private void changeDescription(View v){
        kcalText = v.findViewById(R.id.KcalRemain);
        proteinsText = v.findViewById(R.id.proteinsRemain);
        fatsText = v.findViewById(R.id.fatsRemain);
        carbohydratesText = v.findViewById(R.id.carbohydratesRemain);
        kcalInfoText = v.findViewById(R.id.caloriesRemain);
        proteinsInfoText = v.findViewById(R.id.proteins);
        fatsInfoText = v.findViewById(R.id.fats);
        carbohydratesInfoText = v.findViewById(R.id.carbohydrates);

        if(kcalRemain >= 0){
            kcalText.setText(String.valueOf(kcalRemain));
            kcalInfoText.setText(getString(R.string.caloriesRemainText));
        }else{
            kcalText.setText(String.valueOf(Math.abs(kcalRemain)));
            kcalInfoText.setText(getString(R.string.kcalExcess));
        }
        if(proteinsRemain >= 0) {
            proteinsText.setText(String.valueOf(proteinsRemain));
            proteinsInfoText.setText(getString(R.string.proteins));
        }else{
            String text = getString(R.string.proteins) + "\n" + "(" + getString(R.string.excess) + ")";
            proteinsInfoText.setText(text);
            proteinsText.setText(String.valueOf(Math.abs(proteinsRemain)));
        }
        if(fatsRemain >= 0){
            fatsText.setText(String.valueOf(fatsRemain));
            fatsInfoText.setText(getString(R.string.fats));
        }else{
            String text = getString(R.string.fats) + "\n" + "(" + getString(R.string.excess) + ")";
            fatsInfoText.setText(text);
            fatsText.setText(String.valueOf(Math.abs(fatsRemain)));
        }
        if(carbohydratesRemain >= 0){
            carbohydratesText.setText(String.valueOf(carbohydratesRemain));
            carbohydratesInfoText.setText(getString(R.string.carbohydrates));
        }else{
            String text = getString(R.string.carbohydrates) + "\n" + "(" + getString(R.string.excess) + ")";
            carbohydratesInfoText.setText(text);
            carbohydratesText.setText(String.valueOf(Math.abs(carbohydratesRemain)));
        }
    }
}