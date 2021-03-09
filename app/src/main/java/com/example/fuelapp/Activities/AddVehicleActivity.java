package com.example.fuelapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fuelapp.Database.DatabaseHelper;
import com.example.fuelapp.R;

import java.util.Calendar;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay;
    EditText newVehicleBrandEt, newVehicleModelEt, newVehicleHorseEt, newVehicleEngineEt, newVehicleYearEt, newPlateNumberEt;
    TextView newInspectionTv, newInsuranceTv;
    Button addNewVehicleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        String currentDate = mDay + "." + mMonth + "." + mYear;

        newVehicleBrandEt = findViewById(R.id.newVehicleBrandEt);
        newVehicleModelEt = findViewById(R.id.newVehicleModelEt);
        newVehicleHorseEt = findViewById(R.id.newVehicleHorseEt);
        newVehicleEngineEt = findViewById(R.id.newVehicleEngineEt);
        newInspectionTv = findViewById(R.id.newInspectionTv);
        newInsuranceTv = findViewById(R.id.newInsuranceTv);
        newVehicleYearEt = findViewById(R.id.newVehicleYearEt);
        newPlateNumberEt = findViewById(R.id.newPlateNumberEt);
        addNewVehicleBtn = findViewById(R.id.addNewVehicleBtn);

        newInspectionTv.setText(currentDate);
        newInsuranceTv.setText(currentDate);
        newInspectionTv.setOnClickListener(this);
        newInsuranceTv.setOnClickListener(this);

        addNewVehicleBtn.setOnClickListener(v -> {
            DatabaseHelper myDB = new DatabaseHelper(AddVehicleActivity.this);
            myDB.addVehicle(newVehicleBrandEt.getText().toString().trim(),
                    newVehicleModelEt.getText().toString().trim(),
                    Float.parseFloat(newVehicleEngineEt.getText().toString().trim()),
                    Integer.parseInt(newVehicleHorseEt.getText().toString().trim()),
                    newInspectionTv.getText().toString().trim(),
                    newInsuranceTv.getText().toString().trim(),
                    Integer.parseInt(newVehicleYearEt.getText().toString().trim()),
                    newPlateNumberEt.getText().toString().trim()
            );

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        if (v == newInspectionTv) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        String choosenDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        newInspectionTv.setText(choosenDate);
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();
        } else if (v == newInsuranceTv) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        String choosenDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        newInsuranceTv.setText(choosenDate);
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();
        }
    }

}
