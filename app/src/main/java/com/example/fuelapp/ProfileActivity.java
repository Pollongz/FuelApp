package com.example.fuelapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    public static final String COLUMN_CAR_ID = "_id";
    private TextView myCarID, myCarBrand, myCarModel, myCarYear, myEngineCapacity, myEnginePower, myInspectionDate, myInsuranceDate;
    private Button goToFuelList, deleteItem;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        myCarID = findViewById(R.id.myCarID);
        myCarBrand = findViewById(R.id.myCarBrand);
        myCarModel = findViewById(R.id.myCarModel);
        myCarYear = findViewById(R.id.myCarYear);
        myEngineCapacity = findViewById(R.id.myEngineCapacity);
        myEnginePower = findViewById(R.id.myEnginePower);
        myInspectionDate = findViewById(R.id.myInspectionDate);
        myInsuranceDate = findViewById(R.id.myInsuranceDate);
        goToFuelList = findViewById(R.id.goToFuelList);
        deleteItem = findViewById(R.id.deleteItem);

        myCarID.setText(getCarsData(0));
        myCarBrand.setText(getCarsData(1));
        myCarModel.setText(getCarsData(2));
        myEngineCapacity.setText(getCarsData(3));
        myEnginePower.setText(getCarsData(4));
        myInspectionDate.setText(getCarsData(5));
        myInsuranceDate.setText(getCarsData(6));
        myCarYear.setText(getCarsData(7));

        Intent intent = getIntent();
        String value = intent.getStringExtra(MainActivity.COLUMN_CAR_ID);

        goToFuelList.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), FuelActivity.class);
            intent1.putExtra(COLUMN_CAR_ID, value);
            startActivity(intent1);
        });

        deleteItem.setOnClickListener(v -> {
            DatabaseHelper myDB = new DatabaseHelper(ProfileActivity.this);
            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Delete item")
                    .setMessage("Are you sure you want to delete this item from the list?")
                    .setPositiveButton(android.R.string.yes, (dialog, position) -> {
                        myDB.deleteOneRow(value);
                        Intent intent12 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent12);
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }

        @SuppressLint("Recycle")
        private String getCarsData(int option) {
            SQLiteDatabase db = this.openOrCreateDatabase("CarsList.db", Context.MODE_PRIVATE, null);
            Cursor cursor;
            if(db != null) {
                Intent intent = getIntent();
                String value = intent.getStringExtra(MainActivity.COLUMN_CAR_ID);
                cursor = db.rawQuery("SELECT _id,car_brand,car_model,car_engine_capacity,car_horse_power,car_inspection_date,car_insurance_date,car_year_made  FROM cars WHERE _id = " + value, null);
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
}



