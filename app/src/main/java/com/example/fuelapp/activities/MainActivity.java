package com.example.fuelapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fuelapp.database.DatabaseHelper;
import com.example.fuelapp.fragments.ProfileFragment;
import com.example.fuelapp.R;
import com.example.fuelapp.recyclerViews.RecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_VEHICLE_ID = "_id";
    private DatabaseHelper myDB;
    private com.example.fuelapp.recyclerViews.RecyclerAdapter.RecyclerViewClickListener listener;
    private ArrayList<String> vehicleIds, vehicleBrands, vehicleModels, vehicleHorses, vehicleEngines, vehicleYears, plateNumbers;
    private ImageView emptyVehicle, emptyView;
    private TextView vehiclesTitle;
    private RecyclerView recyclerView;
    private RecyclerAdapter RecyclerAdapter;
    private FloatingActionButton goToAddVehicleBtn;
    private FrameLayout fragmentProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyView = findViewById(R.id.emptyView);
        emptyVehicle = findViewById(R.id.emptyVehicle);
        fragmentProfile = findViewById(R.id.fragment_profile);
        recyclerView = findViewById(R.id.vehicleRecycler);
        goToAddVehicleBtn = findViewById(R.id.goToAddVehicleBtn);
        vehiclesTitle = findViewById(R.id.vehiclesTitle);

        goToAddVehicleBtn.setOnClickListener(this);

        myDB = new DatabaseHelper(MainActivity.this);
        vehicleIds = new ArrayList<>();
        vehicleBrands = new ArrayList<>();
        vehicleModels = new ArrayList<>();
        vehicleEngines = new ArrayList<>();
        vehicleHorses = new ArrayList<>();
        vehicleYears = new ArrayList<>();
        plateNumbers = new ArrayList<>();

        storeVehiclesInArrays();
        setVisibilityOfMainActivityElements();
        createAdapter();
    }

    private void setVisibilityOfMainActivityElements() {
        int carListVisible = isNoVehicles() ? View.VISIBLE : View.INVISIBLE;
        int carListInvisible = !isNoVehicles() ? View.VISIBLE : View.INVISIBLE;

        emptyView.setVisibility(carListVisible);
        emptyVehicle.setVisibility(carListVisible);
        vehiclesTitle.setVisibility(carListInvisible);
    }

    private void createAdapter() {
        setOnClickListener();
        RecyclerAdapter = new RecyclerAdapter(MainActivity.this, vehicleIds, vehicleBrands, vehicleModels,  vehicleEngines, vehicleHorses, vehicleYears, plateNumbers, listener);
        recyclerView.setAdapter(RecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            String value = vehicleIds.get(position);
            openFragment(value);
            goToAddVehicleBtn.setVisibility(View.GONE);
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToAddVehicleBtn.setVisibility(View.VISIBLE);
    }

    private void storeVehiclesInArrays() {
        Cursor cursor = myDB.readAllVehicles();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                vehicleIds.add(cursor.getString(0));
                vehicleBrands.add(cursor.getString(1));
                vehicleModels.add(cursor.getString(2));
                vehicleEngines.add(cursor.getString(3));
                vehicleHorses.add(cursor.getString(4));
                vehicleYears.add(cursor.getString(7));
                plateNumbers.add(cursor.getString(8));
            }
        }
    }

    private boolean isNoVehicles() {
        return vehicleIds.isEmpty();
    }

    @Override
    public void onClick(View v) {
        if (v == goToAddVehicleBtn) {
            Intent intent = new Intent(this, AddVehicleActivity.class);
            startActivity(intent);
        }
    }

    private void openFragment(String value) {
        ProfileFragment fragment = ProfileFragment.newInstance(value);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_down, R.anim.exit_to_down, R.anim.enter_from_down, R.anim.exit_to_down);
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_profile, fragment, "PROFILE_FRAGMENT").commit();
    }
}