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

public class AddFuelActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_VEHICLE_ID = "_id";
    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay;
    EditText newFuelTypeEt, newFuelAmountEt, newFuelCostEt, newMileageEt;
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
            DatabaseHelper myDB = new DatabaseHelper(AddFuelActivity.this);
            myDB.addFuel(
                newFuelTypeEt.getText().toString().trim(),
                Float.parseFloat(newFuelAmountEt.getText().toString().trim()),
                Float.parseFloat(newFuelCostEt.getText().toString().trim()),
                Integer.parseInt(newMileageEt.getText().toString().trim()),
                newFuelDateTv.getText().toString().trim(),
                getCarId()
            );

            Intent intent2 = new Intent(this, FuelActivity.class);
            intent2.putExtra(COLUMN_VEHICLE_ID, getCarId());
            startActivity(intent2);
        }
    }

    private String getCarId() {
        Intent intent = getIntent();
        return intent.getStringExtra(ProfileActivity.COLUMN_VEHICLE_ID);
    }
}