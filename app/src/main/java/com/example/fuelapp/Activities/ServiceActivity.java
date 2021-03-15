package com.example.fuelapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
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
    private com.example.fuelapp.RecyclerViews.RecyclerServiceAdapter.RecyclerViewLongClickListener hListener;
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

    private void createAdapter() {
        setOnClickListener();
        setOnLongClickListener();
        RecyclerServiceAdapter = new RecyclerServiceAdapter(ServiceActivity.this, serviceIds, serviceTitles, serviceDescriptions, serviceCosts, serviceDates, servicedVehicleIds, listener, hListener);
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

    private void setOnLongClickListener() {
        hListener = (v, position) -> {
            DatabaseHelper myDB = new DatabaseHelper(ServiceActivity.this);
            new AlertDialog.Builder(ServiceActivity.this)
                    .setTitle("Delete service")
                    .setMessage("Are you sure you want to delete this service from the list?")
                    .setPositiveButton(android.R.string.yes, (dialog, wut) -> {
                        myDB.deleteOneService(serviceIds.get(position));
                        this.recreate();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        };
    }

    private void storeServicesInArrays() {
        Cursor cursor = myDB.readAllServices();

        if (cursor.getCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            emptyService.setVisibility(View.VISIBLE);
        } else {
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
                emptyView.setVisibility(View.GONE);
                emptyService.setVisibility(View.GONE);
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
        if (v == goToAddServiceBtn) {
            Intent passCarId = new Intent(getApplicationContext(), AddServiceActivity.class);
            passCarId.putExtra(COLUMN_VEHICLE_ID, getCarId());
            startActivity(passCarId);
        }
    }

    private String getCarId() {
        Intent intent = getIntent();
        return intent.getStringExtra(MainActivity.COLUMN_VEHICLE_ID);
    }
}