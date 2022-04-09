package com.example.project.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.Days;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Database{
    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "calendar";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_KCAL = "KCAL";
    public static final String COLUMN_PROTEINS = "PROTEINS";
    public static final String COLUMN_FATS = "FATS";
    public static final String COLUMN_CARBOHYDRATES = "CARBOHYDRATES";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_DATE = 1;
    private static final int NUM_COLUMN_KCAL = 2;
    private static final int NUM_COLUMN_PROTEINS = 3;
    private static final int NUM_COLUMN_FATS = 4;
    private static final int NUM_COLUMN_CARBOHYDRATES = 5;

    private final SQLiteDatabase dataBase;
    OpenHelper helper;

    public Database(Context context) {
        OpenHelper helper = new OpenHelper(context);
        dataBase = helper.getWritableDatabase();
    }

    public long insert(int kcalRemain, int proteinsRemain, int fatsRemain, int carbohydratesRemain) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE,DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
        cv.put(COLUMN_KCAL, kcalRemain);
        cv.put(COLUMN_PROTEINS, proteinsRemain);
        cv.put(COLUMN_FATS, fatsRemain);
        cv.put(COLUMN_CARBOHYDRATES, carbohydratesRemain);
        long newRowId = dataBase.insert(TABLE_NAME, null, cv);
        return newRowId;
    }

    public int update(Days day){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KCAL, day.getKcal());
        cv.put(COLUMN_PROTEINS, day.getProteins());
        cv.put(COLUMN_FATS, day.getFats());
        cv.put(COLUMN_CARBOHYDRATES, day.getCarbohydrates());
        return dataBase.update(TABLE_NAME, cv, COLUMN_ID + " =?", new String[]{String.valueOf(day.getId())});
    }

    public Days select(long id){
        Cursor cursor = dataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        Days days = null;
        if(cursor != null && cursor.moveToFirst()) {
            days.setId(id);
            days.setDate(cursor.getString(NUM_COLUMN_DATE));
            days.setKcal(cursor.getInt(NUM_COLUMN_KCAL));
            days.setProteins(cursor.getInt(NUM_COLUMN_PROTEINS));
            days.setFats(cursor.getInt(NUM_COLUMN_FATS));
            days.setCarbohydrates(cursor.getInt(NUM_COLUMN_CARBOHYDRATES));
            cursor.close();
        }
        return days;
    }

    public long length(Database database){
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
                String date = c.getString(NUM_COLUMN_DATE);
                int kcal = c.getInt(NUM_COLUMN_KCAL);
                int proteins = c.getInt(NUM_COLUMN_PROTEINS);
                int fats = c.getInt(NUM_COLUMN_FATS);
                int carbohydrates = c.getInt(NUM_COLUMN_CARBOHYDRATES);
                arr.add(new Days(id, date, kcal, proteins, fats, carbohydrates));
            }while (c.moveToNext());
        }
        return arr;
    }

    public void delete(long id){
        dataBase.delete(TABLE_NAME, COLUMN_ID + " =?", new String[]{String.valueOf(id)});
    }

    public void deleteAll(){
        dataBase.delete(TABLE_NAME, null, null);
    }

    public static class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_KCAL + " INT," +
                    COLUMN_PROTEINS + " INT," +
                    COLUMN_FATS + " INT," +
                    COLUMN_CARBOHYDRATES + " INT);";
            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}