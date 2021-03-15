package com.example.fuelapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelapp.Database.DatabaseHelper;
import com.example.fuelapp.Fragments.ProfileFragment;
import com.example.fuelapp.R;
import com.example.fuelapp.RecyclerViews.RecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_VEHICLE_ID = "_id";
    private DatabaseHelper myDB;
    private com.example.fuelapp.RecyclerViews.RecyclerAdapter.RecyclerViewClickListener listener;
    private ArrayList<String> vehicleIds, vehicleBrands, vehicleModels, vehicleHorses, vehicleEngines, vehicleYears, plateNumbers;
    private ImageView emptyVehicle, emptyView;
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
        createAdapter();
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
        if (cursor.getCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            emptyVehicle.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
            emptyVehicle.setVisibility(View.GONE);
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