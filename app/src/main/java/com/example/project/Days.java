package com.example.project;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Days implements Serializable {
    private long id;
    private String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
    private int kcal;
    private int proteins;
    private int fats;
    private int carbohydrates;

    public Days(long id, String date, int kcal, int proteins, int fats, int carbohydrates){
        this.id = id;
        this.date = date;
        this.kcal = kcal;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getKcal() {
        return kcal;
    }

    public int getProteins() {
        return proteins;
    }

    public int getFats() {
        return fats;
    }

    public int getCarbohydrates(){
        return carbohydrates;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}
