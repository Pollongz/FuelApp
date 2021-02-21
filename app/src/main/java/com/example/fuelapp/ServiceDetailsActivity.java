package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_CAR_ID = "_id";
    public static final String COLUMN_SERVICED_CAR_ID = "serviced_car_id";
    private TextView thisServiceTitleTv, thisServiceDescTv, thisServiceDateTv, thisServiceCostTv;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        thisServiceTitleTv = findViewById(R.id.thisServiceTitleTv);
        thisServiceDescTv = findViewById(R.id.thisServiceDescTv);
        thisServiceDateTv = findViewById(R.id.thisServiceDateTv);
        thisServiceCostTv = findViewById(R.id.thisServiceCostTv);

        String serviceCost = getServicesData(3) + " PLN";

        thisServiceTitleTv.setText(getServicesData(1));
        thisServiceDescTv.setText(getServicesData(2));
        thisServiceCostTv.setText(serviceCost);
        thisServiceDateTv.setText(getServicesData(4));

        Intent intent = getIntent();
        value = intent.getStringExtra(ProfileActivity.COLUMN_CAR_ID);
    }

    @SuppressLint("Recycle")
    private String getServicesData(int option) {
        SQLiteDatabase db = this.openOrCreateDatabase("CarsList.db", Context.MODE_PRIVATE, null);
        Cursor cursor;
        if(db != null) {
            Intent intent = getIntent();
            value = intent.getStringExtra(ServiceActivity.COLUMN_SERVICE_ID);
            cursor = db.rawQuery("SELECT service_id,service_title,service_desc,service_cost,service_date,serviced_car_id  FROM services WHERE service_id = " + value, null);
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

    }
}