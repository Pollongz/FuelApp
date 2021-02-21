package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_CAR_ID = "_id";
    public static final String COLUMN_SERVICE_ID = "service_id";
    public static final String COLUMN_SERVICED_CAR_ID = "serviced_car_id";
    private DatabaseHelper myDB;
    private RecyclerServiceAdapter.RecyclerViewClickListener listener;
    private RecyclerView serviceRecycler;
    private RecyclerServiceAdapter RecyclerServiceAdapter;
    private ArrayList<String> serviceIds, serviceTitles, serviceDescriptions, serviceCosts, serviceDates, servicedCarIds;
    private Button goToAddServiceBtn;
    private String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        serviceRecycler = findViewById(R.id.serviceRecycler);
        goToAddServiceBtn = findViewById(R.id.goToAddServiceBtn);
        goToAddServiceBtn.setOnClickListener(this);

        Intent intent1 = getIntent();
        value = intent1.getStringExtra(ProfileActivity.COLUMN_CAR_ID);

        myDB = new DatabaseHelper(ServiceActivity.this);
        serviceIds = new ArrayList<>();
        serviceTitles = new ArrayList<>();
        serviceDescriptions = new ArrayList<>();
        serviceCosts = new ArrayList<>();
        serviceDates = new ArrayList<>();
        servicedCarIds = new ArrayList<>();

        storeServicesInArrays();
        createAdapter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ServiceActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == goToAddServiceBtn) {
            Intent intent1 = getIntent();
            value = intent1.getStringExtra(ProfileActivity.COLUMN_CAR_ID);

            Intent intent4 = new Intent(getApplicationContext(), AddServiceActivity.class);
            intent4.putExtra(COLUMN_CAR_ID, value);
            startActivity(intent4);
        }
    }

    private void createAdapter() {
        setOnClickListener();
        RecyclerServiceAdapter = new RecyclerServiceAdapter(ServiceActivity.this, serviceIds, serviceTitles, serviceDescriptions, serviceCosts, serviceDates, servicedCarIds, listener);
        serviceRecycler.setAdapter(RecyclerServiceAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ServiceActivity.this);
        serviceRecycler.setLayoutManager(layoutManager);
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Toast.makeText(getApplicationContext(), "clicked on " + serviceIds.get(position), Toast.LENGTH_SHORT).show();
            String value = serviceIds.get(position);
            Intent intent = new Intent(getApplicationContext(), ServiceDetailsActivity.class);
            intent.putExtra(COLUMN_SERVICE_ID, value);
            startActivity(intent);
        };
    }

    private void storeServicesInArrays() {
        Cursor cursor = myDB.readAllServices();

        Intent intent1 = getIntent();
        String checkedValue = intent1.getStringExtra(ProfileActivity.COLUMN_CAR_ID);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to be displayed.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("serviced_car_id"));
                if (value.equals(checkedValue)) {
                    serviceIds.add(cursor.getString(cursor.getColumnIndex("service_id")));
                    serviceTitles.add(cursor.getString(cursor.getColumnIndex("service_title")));
                    serviceDescriptions.add(cursor.getString(cursor.getColumnIndex("service_desc")));
                    serviceCosts.add(cursor.getString(cursor.getColumnIndex("service_cost")));
                    serviceDates.add(cursor.getString(cursor.getColumnIndex("service_date")));
                    servicedCarIds.add(cursor.getString(cursor.getColumnIndex("serviced_car_id")));
                }
            }
        }
    }
}