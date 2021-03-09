package com.example.fuelapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelapp.Database.DatabaseHelper;
import com.example.fuelapp.R;
import com.example.fuelapp.RecyclerViews.RecyclerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_VEHICLE_ID = "_id";
    private DatabaseHelper myDB;
    private com.example.fuelapp.RecyclerViews.RecyclerAdapter.RecyclerViewClickListener listener;
    private ArrayList<String> vehicleIds, vehicleBrands, vehicleModels, vehicleHorses, vehicleEngines, vehicleYears;
    private TextView noDataTv;
    private ImageView noDataIv;
    private RecyclerView recyclerView;
    private RecyclerAdapter RecyclerAdapter;
    private Button goToAddVehicleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noDataTv = findViewById(R.id.noDataTv);
        noDataIv = findViewById(R.id.noData_imageView);

        recyclerView = findViewById(R.id.vehicleRecycler);
        goToAddVehicleBtn = findViewById(R.id.goToAddVehicleBtn);
        goToAddVehicleBtn.setOnClickListener(this);

        myDB = new DatabaseHelper(MainActivity.this);
        vehicleIds = new ArrayList<>();
        vehicleBrands = new ArrayList<>();
        vehicleModels = new ArrayList<>();
        vehicleEngines = new ArrayList<>();
        vehicleHorses = new ArrayList<>();
        vehicleYears = new ArrayList<>();

        storeVehiclesInArrays();
        createAdapter();

    }

    private void createAdapter() {
        setOnClickListener();
        RecyclerAdapter = new RecyclerAdapter(MainActivity.this, vehicleIds, vehicleBrands, vehicleModels,  vehicleEngines, vehicleHorses, vehicleYears, listener);
        recyclerView.setAdapter(RecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Toast.makeText(getApplicationContext(), "clicked on " + vehicleIds.get(position), Toast.LENGTH_SHORT).show();
            String value = vehicleIds.get(position);
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra(COLUMN_VEHICLE_ID, value);
            startActivity(intent);
        };
    }

    private void storeVehiclesInArrays() {
        Cursor cursor = myDB.readAllVehicles();
            if (cursor.getCount() == 0) {
                noDataIv.setVisibility(View.VISIBLE);
                noDataTv.setVisibility(View.VISIBLE);
            } else {
                while (cursor.moveToNext()) {
                    vehicleIds.add(cursor.getString(0));
                    vehicleBrands.add(cursor.getString(1));
                    vehicleModels.add(cursor.getString(2));
                    vehicleEngines.add(cursor.getString(3));
                    vehicleHorses.add(cursor.getString(4));
                    vehicleYears.add(cursor.getString(7));

                    noDataIv.setVisibility(View.GONE);
                    noDataTv.setVisibility(View.GONE);
                }
            }
    }

    @Override
    public void onClick(View v) {
        if (v == goToAddVehicleBtn) {
            Intent intent = new Intent(this, AddVehicleActivity.class);
            startActivity(intent);
        }
    }
}