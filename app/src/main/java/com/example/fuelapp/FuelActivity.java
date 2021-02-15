package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class FuelActivity extends AppCompatActivity {


    public static final String COLUMN_CAR_ID = "_id";
    private DatabaseHelper myDB;
    private RecyclerFuelAdapter.RecyclerViewClickListener listener;
    private ArrayList<String> fuelIds, stationNames, fuelTypes, fuelAmounts, fuelCosts, mileages, fuelDates, fueledCarIds;
    private RecyclerView recyclerView;
    private RecyclerFuelAdapter RecyclerFuelAdapter;
    private Button goToAddFuelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);

        recyclerView = findViewById(R.id.fuelRecycler);
        goToAddFuelBtn = findViewById(R.id.goToAddFuelBtn);

        goToAddFuelBtn.setOnClickListener(v -> {
            Intent intent1 = getIntent();
            String value = intent1.getStringExtra(ProfileActivity.COLUMN_CAR_ID);
            Intent intent4 = new Intent(getApplicationContext(), AddFuelActivity.class);
            intent4.putExtra(COLUMN_CAR_ID, value);
            startActivity(intent4);
        });

        myDB = new DatabaseHelper(FuelActivity.this);
        fuelIds = new ArrayList<>();
        stationNames = new ArrayList<>();
        fuelTypes = new ArrayList<>();
        fuelAmounts = new ArrayList<>();
        fuelCosts = new ArrayList<>();
        mileages = new ArrayList<>();
        fuelDates = new ArrayList<>();
        fueledCarIds = new ArrayList<>();

        storeFuelsInArrays();
        createAdapter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FuelActivity.this, MainActivity.class));
        finish();
    }

    private void createAdapter() {
        setOnClickListener();
        RecyclerFuelAdapter = new RecyclerFuelAdapter(FuelActivity.this, fuelIds, stationNames, fuelTypes, fuelAmounts, fuelCosts, mileages, fuelDates, fueledCarIds,  listener);
        recyclerView.setAdapter(RecyclerFuelAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FuelActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setOnClickListener() {
        listener = (v, position) -> Toast.makeText(getApplicationContext(), "clicked on " + fuelIds.get(position), Toast.LENGTH_SHORT).show();
    }

    private void storeFuelsInArrays() {
        Cursor cursor = myDB.readAllFuels();
        Intent intent1 = getIntent();
        String checkedValue = intent1.getStringExtra(ProfileActivity.COLUMN_CAR_ID);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to be displayed.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("fueled_car_id"));
                if (value.equals(checkedValue)) {
                    fuelIds.add(cursor.getString(cursor.getColumnIndex("fuel_id")));
                    stationNames.add(cursor.getString(cursor.getColumnIndex("station_name")));
                    fuelTypes.add(cursor.getString(cursor.getColumnIndex("fuel_type")));
                    fuelAmounts.add(cursor.getString(cursor.getColumnIndex("fuel_amount")));
                    fuelCosts.add(cursor.getString(cursor.getColumnIndex("fuel_cost")));
                    mileages.add(cursor.getString(cursor.getColumnIndex("mileage")));
                    fuelDates.add(cursor.getString(cursor.getColumnIndex("fuel_date")));
                    fueledCarIds.add(cursor.getString(cursor.getColumnIndex("fueled_car_id")));
                }
            }
        }
    }
}