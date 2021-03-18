package com.example.fuelapp.Activities;

import androidx.appcompat.app.AlertDialog;
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
    private String choosenDate;
    private EditText newVehicleBrandEt, newVehicleModelEt, newVehicleHorseEt, newVehicleEngineEt, newVehicleYearEt, newPlateNumberEt;
    private TextView newInspectionTv, newInsuranceTv;
    private Button addNewVehicleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        newVehicleBrandEt = findViewById(R.id.newVehicleBrandEt);
        newVehicleModelEt = findViewById(R.id.newVehicleModelEt);
        newVehicleHorseEt = findViewById(R.id.newVehicleHorseEt);
        newVehicleEngineEt = findViewById(R.id.newVehicleEngineEt);
        newInspectionTv = findViewById(R.id.newInspectionTv);
        newInsuranceTv = findViewById(R.id.newInsuranceTv);
        newVehicleYearEt = findViewById(R.id.newVehicleYearEt);
        newPlateNumberEt = findViewById(R.id.newPlateNumberEt);
        addNewVehicleBtn = findViewById(R.id.addNewVehicleBtn);

        newInspectionTv.setText(getCurrentDate());
        newInsuranceTv.setText(getCurrentDate());
        newInspectionTv.setOnClickListener(this);
        newInsuranceTv.setOnClickListener(this);
        addNewVehicleBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(AddVehicleActivity.this)
                .setTitle("Discard changes")
                .setMessage("Are you sure you want to discard all changes?")
                .setPositiveButton(android.R.string.yes, (dialog, position) -> {
                    super.onBackPressed();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onClick(View v) {
        if (v == newInspectionTv) {
            chooseDate(newInspectionTv);
        } else if (v == newInsuranceTv) {
            chooseDate(newInsuranceTv);
        } else if (v == addNewVehicleBtn) {
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
        }
    }

    public String getCurrentDate() {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        if (mDay < 10 && mMonth < 10) {
            return "0" + mDay + ".0" + (mMonth + 1) + "." + mYear;
        } else if (mDay < 10) {
            return "0" + mDay + "." + (mMonth + 1) + "." + mYear;
        } else if (mMonth < 10) {
            return mDay + ".0" + (mMonth + 1) + "." + mYear;
        } else {
            return mDay + "." + (mMonth + 1) + "." + mYear;
        }
    }

    private void chooseDate(TextView option) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    if (dayOfMonth < 10 && monthOfYear < 9) {
                        choosenDate = "0" + dayOfMonth + ".0" + (monthOfYear + 1) + "." + year;
                    } else if (dayOfMonth < 10) {
                        choosenDate = "0" + dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                    } else if (monthOfYear < 9) {
                        choosenDate = dayOfMonth + ".0" + (monthOfYear + 1) + "." + year;
                    } else {
                        choosenDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                    }
                    option.setText(choosenDate);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
