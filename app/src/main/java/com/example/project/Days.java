package com.example.project;

import java.io.Serializable;
import java.util.Objects;
//Класс для базы данных
public class Days implements Serializable {
    private long id;
    private int kcal;
    private int proteins;
    private int fats;
    private int carbohydrates;
    private int glasses;
    private boolean isSuccessful;

    public Days(long id, int kcal, int proteins, int fats, int carbohydrates, int glasses, boolean isSuccessful) {
        this.id = id;
        this.kcal = kcal;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.glasses = glasses;
        this.isSuccessful = isSuccessful;
    }

    public long getId() {
        return id;
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

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setSuccessful(boolean successful) {
        this.isSuccessful = successful;
    }

    public int getGlasses() {
        return glasses;
    }

    public void setGlasses(int glasses) {
        this.glasses = glasses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Days days = (Days) o;
        return id == days.id && kcal == days.kcal && proteins == days.proteins && fats == days.fats && carbohydrates == days.carbohydrates && glasses == days.glasses && isSuccessful == days.isSuccessful;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kcal, proteins, fats, carbohydrates, glasses, isSuccessful);
    }
}
