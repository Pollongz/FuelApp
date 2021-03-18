package com.example.fuelapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelapp.Database.DatabaseHelper;
import com.example.fuelapp.R;

public class ServiceDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_SERVICE_ID = "service_id";
    public static final String COLUMN_VEHICLE_ID = "_id";
    private TextView thisServiceTitleTv, thisServiceDescTv, thisServiceDateTv, thisServiceCostTv;
    private Button editService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        thisServiceTitleTv = findViewById(R.id.thisServiceTitleTv);
        thisServiceDescTv = findViewById(R.id.thisServiceDescTv);
        thisServiceDateTv = findViewById(R.id.thisServiceDateTv);
        thisServiceCostTv = findViewById(R.id.thisServiceCostTv);
        editService = findViewById(R.id.editService);

        String serviceCost = getServicesData(3) + " PLN";
        thisServiceTitleTv.setText(getServicesData(1));
        thisServiceDescTv.setText(getServicesData(2));
        thisServiceCostTv.setText(serviceCost);
        thisServiceDateTv.setText(getServicesData(4));

        editService.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("Recycle")
    private String getServicesData(int option) {
        SQLiteDatabase db = this.openOrCreateDatabase("VehiclesList.db", Context.MODE_PRIVATE, null);
        Cursor cursor;
        if(db != null) {
            cursor = db.rawQuery("SELECT service_id,service_title,service_desc,service_cost,service_date,serviced_vehicle_id  FROM services WHERE service_id = " + getServiceId(), null);
            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
            }
            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()) {
                buffer.append(cursor.getString(option));
            }
            return buffer.toString();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        if (v == editService) {
            Intent intent1 = new Intent(getApplicationContext(), EditServiceActivity.class);
            intent1.putExtra(COLUMN_SERVICE_ID, getServiceId());
            startActivity(intent1);
        }
    }

    private String getServiceId() {
        Intent intent = getIntent();
        return intent.getStringExtra(ServiceActivity.COLUMN_SERVICE_ID);
    }

    private String getCarId() {
        Intent intent = getIntent();
        return intent.getStringExtra(MainActivity.COLUMN_VEHICLE_ID);
    }
}