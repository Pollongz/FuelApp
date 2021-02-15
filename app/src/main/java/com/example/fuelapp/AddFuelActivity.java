package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddFuelActivity extends AppCompatActivity {

    public static final String COLUMN_CAR_ID = "_id";
    EditText newStationNameEt, newFuelTypeEt, newFuelAmountEt, newFuelCostEt, newMileageEt, newFuelDateEt;
    Button addNewFuelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fuel);


        newStationNameEt = findViewById(R.id.newStationNameEt);
        newFuelTypeEt = findViewById(R.id.newFuelTypeEt);
        newFuelAmountEt = findViewById(R.id.newFuelAmountEt);
        newFuelCostEt = findViewById(R.id.newFuelCostEt);
        newMileageEt = findViewById(R.id.newMileageEt);
        newFuelDateEt = findViewById(R.id.newFuelDateEt);
        addNewFuelBtn = findViewById(R.id.addNewFuelBtn);

        addNewFuelBtn.setOnClickListener(v -> {
            Intent intent1 = getIntent();
            String value = intent1.getStringExtra(ProfileActivity.COLUMN_CAR_ID);
            DatabaseHelper myDB = new DatabaseHelper(AddFuelActivity.this);
            myDB.addFuel(
                    newStationNameEt.getText().toString().trim(),
                    newFuelTypeEt.getText().toString().trim(),
                    Float.parseFloat(newFuelAmountEt.getText().toString().trim()),
                    Float.parseFloat(newFuelCostEt.getText().toString().trim()),
                    Integer.parseInt(newMileageEt.getText().toString().trim()),
                    newFuelDateEt.getText().toString().trim(),
                    value
            );

            Intent intent2 = new Intent(this, FuelActivity.class);
            intent2.putExtra(COLUMN_CAR_ID, value);
            startActivity(intent2);
        });
    }
}