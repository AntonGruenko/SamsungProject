package com.example.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.data.Database;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UserFragment extends Fragment {
    private static final String SETTINGS = "SharedPreferences";
    private static final long DAY_IN_MILLIS = 86400000;
    private final ArrayList<Long> dates = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        BarChart chart = v.findViewById(R.id.chart);
        TextView averageKcal = v.findViewById(R.id.average);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        Database db = new Database(getActivity().getApplicationContext());
        int[] colorArray = new int[7];

        SharedPreferences sp = this.getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        long date = sp.getLong("date", 0);

        //Заполнение столбцов цветом в соответствие с успешностью дня
        int j = 0;
        for(long i = date; i > date - 7 * DAY_IN_MILLIS; i -= DAY_IN_MILLIS) {
            if (db.select(i).isSuccessful()) {
                colorArray[j] = Color.parseColor("#FCAB10");
            } else {
                colorArray[j] = Color.parseColor("#A3320B");
            }
            ++j;
        }

        LimitLine ll = new LimitLine(sp.getInt("kcal", 0), getString(R.string.your_limit));
        fillDays();

        for (int i = 0; i < dates.size(); i++){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dates.get(i));
            String dateText = String.format(Locale.ENGLISH, "%1$td.%1$tm", calendar);
            labels.add(dateText);
            int kcal = db.select(dates.get(i)).getKcal();
            barEntries.add(new BarEntry(i,kcal));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(colorArray);
        Description description = new Description();
        description.setText(getString(R.string.days));
        chart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        chart.setData(barData);


        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(dates.size());
        YAxis yAxis = chart.getAxisLeft();
        yAxis.addLimitLine(ll);
        chart.animateY(500);

        averageKcal.setText(String.valueOf(getArithmeticMean(db, date)));
        return v;
    }

    private void  fillDays(){
        SharedPreferences sp = this.getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        long date = sp.getLong("date", 0);
        dates.clear();
        for (int i = 0; i < 7; i++){
            dates.add(date - DAY_IN_MILLIS * i);
        }

    }
    //Вычисление среднего потребления калорий за последние 7 дней
    private int getArithmeticMean(Database db, long date) throws ArithmeticException{
        int res = 0;
        int div =  0;
        for(int i = 0; i < 7; i++){
            long id = date - DAY_IN_MILLIS * i;
            res += db.select(id).getKcal();
            if(db.select(id).getKcal() != 0){
                div++;
            }
        }
        try {
            res /= div;
        }catch (ArithmeticException e){
            res = 0;
        }
        return res;
    }

    public static long getDayInMillis() {
        return DAY_IN_MILLIS;
    }
}