package com.example.fuelapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "CarsList.db";
    private static final int  DATABASE_VERSION = 1;

    private static final String TABLE_CARS = "cars";
    private static final String COLUMN_CAR_ID = "_id";
    private static final String COLUMN_BRAND = "car_brand";
    private static final String COLUMN_MODEL = "car_model";
    private static final String COLUMN_HORSE_POWER = "car_horse_power";
    private static final String COLUMN_ENGINE_CAPACITY = "car_engine_capacity";
    private static final String COLUMN_INSPECTION_DATE = "car_inspection_date";
    private static final String COLUMN_INSURANCE_DATE = "car_insurance_date";
    private static final String COLUMN_YEAR_MADE = "car_year_made";

    private static final String TABLE_FUELS = "fuels";
    private static final String COLUMN_FUEL_ID = "fuel_id";
    private static final String COLUMN_STATION_NAME = "station_name";
    private static final String COLUMN_FUEL_TYPE = "fuel_type";
    private static final String COLUMN_FUEL_AMOUNT = "fuel_amount";
    private static final String COLUMN_FUEL_COST = "fuel_cost";
    private static final String COLUMN_MILEAGE = "mileage";
    private static final String COLUMN_FUEL_DATE = "fuel_date";
    private static final String COLUMN_FUELED_CAR_ID = "fueled_car_id";

    private static final String TABLE_REPAIRS = "repairs";
    private static final String COLUMN_REPAIR_ID = "repair_id";
    private static final String COLUMN_REPAIR_TITLE = "repair_title";
    private static final String COLUMN_REPAIR_DESCRIPTION = "repair_desc";
    private static final String COLUMN_REPAIR_COST = "repair_cost";
    private static final String COLUMN_REPAIR_DATE = "repair_date";
    private static final String COLUMN_REPAIRED_CAR_ID = "repaired_car_id";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 =
                "CREATE TABLE " + TABLE_CARS +
                        " (" + COLUMN_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_BRAND + " TEXT, " +
                        COLUMN_MODEL + " TEXT, " +
                        COLUMN_ENGINE_CAPACITY + " FLOAT, " +
                        COLUMN_HORSE_POWER + " INTEGER, " +
                        COLUMN_INSPECTION_DATE + " TEXT, " +
                        COLUMN_INSURANCE_DATE + " TEXT, " +
                        COLUMN_YEAR_MADE + " INTEGER);";
        String query2 =
                "CREATE TABLE " + TABLE_FUELS +
                        " (" + COLUMN_FUEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_STATION_NAME + " TEXT, " +
                        COLUMN_FUEL_TYPE + " TEXT, " +
                        COLUMN_FUEL_AMOUNT + " REAL, " +
                        COLUMN_FUEL_COST + " REAL, " +
                        COLUMN_MILEAGE + " INTEGER, " +
                        COLUMN_FUEL_DATE + " TEXT, " +
                        COLUMN_FUELED_CAR_ID + " INTEGER REFERENCES "+ COLUMN_CAR_ID + ");";
        String query3 =
                "CREATE TABLE " + TABLE_REPAIRS +
                        " (" + COLUMN_REPAIR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_REPAIR_TITLE + " TEXT, " +
                        COLUMN_REPAIR_DESCRIPTION + " TEXT, " +
                        COLUMN_REPAIR_COST + " INTEGER, " +
                        COLUMN_REPAIR_DATE + " TEXT, " +
                        COLUMN_REPAIRED_CAR_ID + " INTEGER REFERENCES "+ COLUMN_CAR_ID + ");";
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_CARS);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_FUELS);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_REPAIRS);
        onCreate(db);
    }

    void addCar(String brand,
                String model,
                float engine,
                int horsePower,
                String inspectionDate,
                String insuranceDate,
                int yearMade
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BRAND, brand);
        cv.put(COLUMN_MODEL, model);
        cv.put(COLUMN_ENGINE_CAPACITY, engine);
        cv.put(COLUMN_HORSE_POWER, horsePower);
        cv.put(COLUMN_INSPECTION_DATE, inspectionDate);
        cv.put(COLUMN_INSURANCE_DATE, insuranceDate);
        cv.put(COLUMN_YEAR_MADE, yearMade);
        long result = db.insert(TABLE_CARS, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed adding new car.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added a new car!", Toast.LENGTH_SHORT).show();
        }
    }

    void addFuel(String stationName,
                 String fuelType,
                 float fuelAmount,
                 float fuelCost,
                 int mileage,
                 String fuelDate,
                 String fueledCarId
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STATION_NAME, stationName);
        cv.put(COLUMN_FUEL_TYPE, fuelType);
        cv.put(COLUMN_FUEL_AMOUNT, fuelAmount);
        cv.put(COLUMN_FUEL_COST, fuelCost);
        cv.put(COLUMN_MILEAGE, mileage);
        cv.put(COLUMN_FUEL_DATE, fuelDate);
        cv.put(COLUMN_FUELED_CAR_ID, fueledCarId);
        long result = db.insert(TABLE_FUELS, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed adding new refueling.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added a new refueling!", Toast.LENGTH_SHORT).show();
        }
    }

    void addRepair(String repairTitle,
                   String repairDesc,
                   int repairCost,
                   String repairDate,
                   int repairedCarId
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_REPAIR_TITLE, repairTitle);
        cv.put(COLUMN_REPAIR_DESCRIPTION, repairDesc);
        cv.put(COLUMN_REPAIR_COST, repairCost);
        cv.put(COLUMN_REPAIR_DATE, repairDate);
        cv.put(COLUMN_REPAIRED_CAR_ID, repairedCarId);
        long result = db.insert(TABLE_REPAIRS, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed adding new repair.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added a new repair!", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("Recycle")
    Cursor readAllCars() {
        String query = "SELECT * FROM " + TABLE_CARS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    @SuppressLint("Recycle")
    Cursor readAllFuels() {
        String query = "SELECT * FROM " + TABLE_FUELS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    @SuppressLint("Recycle")
    Cursor readAllRepairs() {
        String query = "SELECT * FROM " + TABLE_REPAIRS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        long result = db.delete(TABLE_CARS, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed deleting car.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted a car!", Toast.LENGTH_SHORT).show();
        }
    }
}
