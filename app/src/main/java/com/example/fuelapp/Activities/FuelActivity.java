package com.example.fuelapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fuelapp.Database.DatabaseHelper;
import com.example.fuelapp.Fragments.ProfileFragment;
import com.example.fuelapp.R;
import com.example.fuelapp.RecyclerViews.RecyclerFuelAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FuelActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String COLUMN_VEHICLE_ID = "_id";
    private DatabaseHelper myDB;
    private com.example.fuelapp.RecyclerViews.RecyclerFuelAdapter.RecyclerViewClickListener listener;
    private ArrayList<String> fuelIds, fuelTypes, fuelAmounts, fuelCosts, mileages, fuelDates, fueledVehicleIds;
    private ImageView emptyFuel, emptyView;
    private RecyclerView fuelRecycler;
    private RecyclerFuelAdapter RecyclerFuelAdapter;
    private FloatingActionButton goToAddFuelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);

        emptyView = findViewById(R.id.emptyView);
        emptyFuel = findViewById(R.id.emptyFuel);
        fuelRecycler = findViewById(R.id.fuelRecycler);
        goToAddFuelBtn = findViewById(R.id.goToAddFuelBtn);
        goToAddFuelBtn.setOnClickListener(this);

        myDB = new DatabaseHelper(FuelActivity.this);
        fuelIds = new ArrayList<>();
        fuelTypes = new ArrayList<>();
        fuelAmounts = new ArrayList<>();
        fuelCosts = new ArrayList<>();
        mileages = new ArrayList<>();
        fuelDates = new ArrayList<>();
        fueledVehicleIds = new ArrayList<>();

        storeFuelsInArrays();
        createAdapter();
    }

    private void createAdapter() {
        setOnClickListener();
        RecyclerFuelAdapter = new RecyclerFuelAdapter(FuelActivity.this, fuelIds, fuelTypes, fuelAmounts, fuelCosts, mileages, fuelDates, fueledVehicleIds,  listener);
        fuelRecycler.setAdapter(RecyclerFuelAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FuelActivity.this);
        fuelRecycler.setLayoutManager(layoutManager);
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            DatabaseHelper myDB = new DatabaseHelper(FuelActivity.this);
            new AlertDialog.Builder(FuelActivity.this)
                .setTitle("Delete fuelling")
                .setMessage("Are you sure you want to delete this fuelling from the list?")
                .setPositiveButton(android.R.string.yes, (dialog, wut) -> {
                    myDB.deleteOneFuel(fuelIds.get(position));
                    this.recreate();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        };
    }

    private void storeFuelsInArrays() {
        Cursor cursor = myDB.readAllFuels();

        if (cursor.getCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            emptyFuel.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                String fueledValue = cursor.getString(cursor.getColumnIndex("fueled_vehicle_id"));
                if (fueledValue.equals(getCarId())) {
                    fuelIds.add(cursor.getString(cursor.getColumnIndex("fuel_id")));
                    fuelTypes.add(cursor.getString(cursor.getColumnIndex("fuel_type")));
                    fuelAmounts.add(cursor.getString(cursor.getColumnIndex("fuel_amount")));
                    fuelCosts.add(cursor.getString(cursor.getColumnIndex("fuel_cost")));
                    mileages.add(cursor.getString(cursor.getColumnIndex("mileage")));
                    fuelDates.add(cursor.getString(cursor.getColumnIndex("fuel_date")));
                    fueledVehicleIds.add(cursor.getString(cursor.getColumnIndex("fueled_vehicle_id")));
                }
                emptyView.setVisibility(View.GONE);
                emptyFuel.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == goToAddFuelBtn) {
            Intent passCarId = new Intent(getApplicationContext(), AddFuelActivity.class);
            passCarId.putExtra(COLUMN_VEHICLE_ID, getCarId());
            startActivity(passCarId);
        }
    }

    //stara metoda przekazywania z profilu do listy id auta
    private String getCarId() {
        Intent intent = getIntent();
        return intent.getStringExtra(MainActivity.COLUMN_VEHICLE_ID);
    }
}