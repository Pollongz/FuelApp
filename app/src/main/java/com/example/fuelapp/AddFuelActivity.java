package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddFuelActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_CAR_ID = "_id";
    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay;
    EditText newStationNameEt, newFuelTypeEt, newFuelAmountEt, newFuelCostEt, newMileageEt;
    TextView newFuelDateTv;
    Button addNewFuelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fuel);

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        String currentDate = mDay + "." + mMonth + "." + mYear;

        newStationNameEt = findViewById(R.id.newStationNameEt);
        newFuelTypeEt = findViewById(R.id.newFuelTypeEt);
        newFuelAmountEt = findViewById(R.id.newFuelAmountEt);
        newFuelCostEt = findViewById(R.id.newFuelCostEt);
        newMileageEt = findViewById(R.id.newMileageEt);
        newFuelDateTv = findViewById(R.id.newFuelDateTv);
        addNewFuelBtn = findViewById(R.id.addNewFuelBtn);

        newFuelDateTv.setText(currentDate);
        newFuelDateTv.setOnClickListener(this);
        addNewFuelBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v == newFuelDateTv) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        String choosenDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        newFuelDateTv.setText(choosenDate);
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();
        } else if (v == addNewFuelBtn) {

            Intent intent1 = getIntent();
            String value = intent1.getStringExtra(ProfileActivity.COLUMN_CAR_ID);

            DatabaseHelper myDB = new DatabaseHelper(AddFuelActivity.this);
            myDB.addFuel(
                    newStationNameEt.getText().toString().trim(),
                    newFuelTypeEt.getText().toString().trim(),
                    Float.parseFloat(newFuelAmountEt.getText().toString().trim()),
                    Float.parseFloat(newFuelCostEt.getText().toString().trim()),
                    Integer.parseInt(newMileageEt.getText().toString().trim()),
                    newFuelDateTv.getText().toString().trim(),
                    value
            );

            Intent intent2 = new Intent(this, FuelActivity.class);
            intent2.putExtra(COLUMN_CAR_ID, value);
            startActivity(intent2);
        }
    }
}