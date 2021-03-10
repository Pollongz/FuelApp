package com.example.fuelapp.Activities;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fuelapp.Database.DatabaseHelper;
import com.example.fuelapp.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_VEHICLE_ID = "_id";
    private TextView myVehicleBrand, myVehicleModel, myVehicleYear, myEngineCapacity, myEnginePower, myInspectionDate, myInsuranceDate, myPlateNumber;
    private Button goToFuelList, goToServiceList, deleteItem;
    private String value;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        myVehicleBrand = findViewById(R.id.myVehicleBrand);
        myVehicleModel = findViewById(R.id.myVehicleModel);
        myVehicleYear = findViewById(R.id.myVehicleYear);
        myEngineCapacity = findViewById(R.id.myEngineCapacity);
        myEnginePower = findViewById(R.id.myEnginePower);
        myInspectionDate = findViewById(R.id.myInspectionDate);
        myInsuranceDate = findViewById(R.id.myInsuranceDate);
        myPlateNumber = findViewById(R.id.myPlateNumber);
        goToFuelList = findViewById(R.id.goToFuelList);
        goToServiceList = findViewById(R.id.goToServiceList);
        deleteItem = findViewById(R.id.deleteItem);

        myVehicleBrand.setText(getVehiclesData(1));
        myVehicleModel.setText(getVehiclesData(2));
        myEngineCapacity.setText(getVehiclesData(3));
        myEnginePower.setText(getVehiclesData(4));
        myInspectionDate.setText(getVehiclesData(5));
        myInsuranceDate.setText(getVehiclesData(6));
        myVehicleYear.setText(getVehiclesData(7));
        myPlateNumber.setText(getVehiclesData(8));

        goToFuelList.setOnClickListener(this);
        goToServiceList.setOnClickListener(this);
        deleteItem.setOnClickListener(this);

        Intent intent = getIntent();
        value = intent.getStringExtra(MainActivity.COLUMN_VEHICLE_ID);
    }

        @SuppressLint("Recycle")
        private String getVehiclesData(int option) {
            SQLiteDatabase db = this.openOrCreateDatabase("VehiclesList.db", Context.MODE_PRIVATE, null);
            Cursor cursor;
            if(db != null) {
                Intent intent = getIntent();
                String value = intent.getStringExtra(MainActivity.COLUMN_VEHICLE_ID);
                cursor = db.rawQuery("SELECT _id,vehicle_brand,vehicle_model,vehicle_engine_capacity,vehicle_horse_power,vehicle_inspection_date,vehicle_insurance_date,vehicle_year_made, vehicle_plate_number  FROM vehicles WHERE _id = " + value, null);
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

        if (v == goToFuelList) {
            Intent intent1 = new Intent(getApplicationContext(), FuelActivity.class);
            intent1.putExtra(COLUMN_VEHICLE_ID, value);
            startActivity(intent1);
        } else if (v == goToServiceList) {
            Intent intent1 = new Intent(getApplicationContext(), ServiceActivity.class);
            intent1.putExtra(COLUMN_VEHICLE_ID, value);
            startActivity(intent1);
        } else if (v == deleteItem) {
            DatabaseHelper myDB = new DatabaseHelper(ProfileActivity.this);
            new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("Delete vehicle")
                .setMessage("Are you sure you want to delete this vehicle from the list?")
                .setPositiveButton(android.R.string.yes, (dialog, position) -> {
                    myDB.deleteOneRow(value);
                    Intent intent12 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent12);
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        }
    }
}



