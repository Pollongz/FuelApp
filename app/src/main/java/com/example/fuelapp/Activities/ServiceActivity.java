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
import android.widget.Toast;

import com.example.fuelapp.Database.DatabaseHelper;
import com.example.fuelapp.R;
import com.example.fuelapp.RecyclerViews.RecyclerServiceAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_VEHICLE_ID = "_id";
    public static final String COLUMN_SERVICE_ID = "service_id";
    private DatabaseHelper myDB;
    private com.example.fuelapp.RecyclerViews.RecyclerServiceAdapter.RecyclerViewClickListener listener;
    private ImageView emptyService, emptyView;
    private RecyclerView serviceRecycler;
    private RecyclerServiceAdapter RecyclerServiceAdapter;
    private ArrayList<String> serviceIds, serviceTitles, serviceDescriptions, serviceCosts, serviceDates, servicedVehicleIds;
    private FloatingActionButton goToAddServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        emptyView = findViewById(R.id.emptyView);
        emptyService = findViewById(R.id.emptyService);
        serviceRecycler = findViewById(R.id.serviceRecycler);
        goToAddServiceBtn = findViewById(R.id.goToAddServiceBtn);
        goToAddServiceBtn.setOnClickListener(this);

        myDB = new DatabaseHelper(ServiceActivity.this);
        serviceIds = new ArrayList<>();
        serviceTitles = new ArrayList<>();
        serviceDescriptions = new ArrayList<>();
        serviceCosts = new ArrayList<>();
        serviceDates = new ArrayList<>();
        servicedVehicleIds = new ArrayList<>();

        storeServicesInArrays();
        createAdapter();
    }

    @Override
    public void onBackPressed() {
        getCarId();
        super.onBackPressed();
        Intent passCarId = new Intent(getApplicationContext(), ProfileActivity.class);
        passCarId.putExtra(COLUMN_VEHICLE_ID, getCarId());
        startActivity(passCarId);
    }

    private void createAdapter() {
        setOnClickListener();
        RecyclerServiceAdapter = new RecyclerServiceAdapter(ServiceActivity.this, serviceIds, serviceTitles, serviceDescriptions, serviceCosts, serviceDates, servicedVehicleIds, listener);
        serviceRecycler.setAdapter(RecyclerServiceAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ServiceActivity.this);
        serviceRecycler.setLayoutManager(layoutManager);
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Intent passServiceId = new Intent(getApplicationContext(), ServiceDetailsActivity.class);
            passServiceId.putExtra(COLUMN_SERVICE_ID, serviceIds.get(position));
            startActivity(passServiceId);
        };
    }

    private void storeServicesInArrays() {
        Cursor cursor = myDB.readAllServices();

        if (cursor.getCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            emptyService.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
            emptyService.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                String servicedValue = cursor.getString(cursor.getColumnIndex("serviced_vehicle_id"));
                if (servicedValue.equals(getCarId())) {
                    serviceIds.add(cursor.getString(cursor.getColumnIndex("service_id")));
                    serviceTitles.add(cursor.getString(cursor.getColumnIndex("service_title")));
                    serviceDescriptions.add(cursor.getString(cursor.getColumnIndex("service_desc")));
                    serviceCosts.add(cursor.getString(cursor.getColumnIndex("service_cost")));
                    serviceDates.add(cursor.getString(cursor.getColumnIndex("service_date")));
                    servicedVehicleIds.add(cursor.getString(cursor.getColumnIndex("serviced_vehicle_id")));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == goToAddServiceBtn) {
            Intent passCarId = new Intent(getApplicationContext(), AddServiceActivity.class);
            passCarId.putExtra(COLUMN_VEHICLE_ID, getCarId());
            startActivity(passCarId);
        }
    }

    private String getCarId() {
        Intent intent = getIntent();
        return intent.getStringExtra(ProfileActivity.COLUMN_VEHICLE_ID);
    }
}