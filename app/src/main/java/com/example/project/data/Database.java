package com.example.project.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.project.Days;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Database{
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "calendar";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_KCAL = "KCAL";
    private static final String COLUMN_PROTEINS = "PROTEINS";
    private static final String COLUMN_FATS = "FATS";
    private static final String COLUMN_CARBOHYDRATES = "CARBOHYDRATES";
    private static final String COLUMN_GLASSES = "GLASSES";
    private static final String COLUMN_SUCCESSFUL = "IS_SUCCESSFUL";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_KCAL = 1;
    private static final int NUM_COLUMN_PROTEINS = 2;
    private static final int NUM_COLUMN_FATS = 3;
    private static final int NUM_COLUMN_CARBOHYDRATES = 4;
    private static final int NUM_COLUMN_GLASSES = 5;
    private static final int NUM_COLUMN_SUCCESSFUL = 6;


    private final SQLiteDatabase dataBase;
    OpenHelper helper;

    public Database(Context context) {
        OpenHelper helper = new OpenHelper(context);
        dataBase = helper.getWritableDatabase();
    }

    public long insert(long id, int kcal, int proteins, int fats, int carbohydrates, int glasses, boolean isSuccessful) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_KCAL, kcal);
        cv.put(COLUMN_PROTEINS, proteins);
        cv.put(COLUMN_FATS, fats);
        cv.put(COLUMN_CARBOHYDRATES, carbohydrates);
        cv.put(COLUMN_GLASSES,glasses);
        cv.put(COLUMN_SUCCESSFUL, isSuccessful);
        return dataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Days day){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KCAL, day.getKcal());
        cv.put(COLUMN_PROTEINS, day.getProteins());
        cv.put(COLUMN_FATS, day.getFats());
        cv.put(COLUMN_CARBOHYDRATES, day.getCarbohydrates());
        cv.put(COLUMN_GLASSES, day.getGlasses());
        cv.put(COLUMN_SUCCESSFUL, day.isSuccessful());
        return dataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(day.getId())});
    }

    public Days select(long id){
        Cursor c = dataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        Days days = new Days(0,  0, 0, 0, 0, 0, false);
        c.moveToFirst();
        if(c != null && c.moveToFirst()) {
            days.setId(c.getInt(NUM_COLUMN_ID));
            days.setKcal(c.getInt(NUM_COLUMN_KCAL));
            days.setProteins(c.getInt(NUM_COLUMN_PROTEINS));
            days.setFats(c.getInt(NUM_COLUMN_FATS));
            days.setCarbohydrates(c.getInt(NUM_COLUMN_CARBOHYDRATES));
            days.setGlasses(c.getInt(NUM_COLUMN_GLASSES));
            days.setSuccessful(c.getInt(NUM_COLUMN_SUCCESSFUL) != 0);
            c.close();
        }
        return days;
    }

    public long length(){
        long taskCount = DatabaseUtils.queryNumEntries(dataBase, TABLE_NAME);
        return taskCount;
    }

    public ArrayList<Days> selectAll(){
        Cursor c = dataBase.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Days> arr = new ArrayList<>();
        c.moveToFirst();
        if(!c.isAfterLast()){
            do {
                long id = c.getLong(NUM_COLUMN_ID);
                int kcal = c.getInt(NUM_COLUMN_KCAL);
                int proteins = c.getInt(NUM_COLUMN_PROTEINS);
                int fats = c.getInt(NUM_COLUMN_FATS);
                int carbohydrates = c.getInt(NUM_COLUMN_CARBOHYDRATES);
                int glasses = c.getInt(NUM_COLUMN_GLASSES);
                boolean isSuccessful = c.getInt(NUM_COLUMN_SUCCESSFUL) != 0;
                arr.add(new Days(id, kcal, proteins, fats, carbohydrates, glasses, isSuccessful));
            }while (c.moveToNext());
        }
        c.close();
        return arr;
    }

    public void delete(long id){
        dataBase.delete(TABLE_NAME, COLUMN_ID + " =?", new String[]{String.valueOf(id)});
    }

    public void deleteAll(){
        dataBase.delete(TABLE_NAME, null, null);
    }

    public int getArithmeticMean(Database db, long date){
        int res = 0;
        long dayInMillis = 86400000;
        int count = 0;
        for(long i = date; i < date + 7 * dayInMillis; i += dayInMillis){
            int kcal = db.select(i).getKcal();
            res += kcal;
            if(kcal != 0){
                count++;
            }
        }
        try {
            res /= count;
        }catch (Exception e){
            res = 0;
        }
        return res;
    }

    public static class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INT," +
                    COLUMN_KCAL + " INT," +
                    COLUMN_PROTEINS + " INT," +
                    COLUMN_FATS + " INT," +
                    COLUMN_CARBOHYDRATES + " INT," +
                    COLUMN_GLASSES + " INT," +
                    COLUMN_SUCCESSFUL + " INT);";
            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}