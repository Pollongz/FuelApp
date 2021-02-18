package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class AddCarActivity extends AppCompatActivity implements View.OnClickListener {

    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay;
    EditText newCarBrandEt, newCarModelEt, newCarHorseEt, newCarEngineEt, newCarYearEt;
    TextView newInspectionTv, newInsuranceTv;
    Button addNewCarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        String currentDate = mDay + "." + mMonth + "." + mYear;

        newCarBrandEt = findViewById(R.id.newCarBrandEt);
        newCarModelEt = findViewById(R.id.newCarModelEt);
        newCarHorseEt = findViewById(R.id.newCarHorseEt);
        newCarEngineEt = findViewById(R.id.newCarEngineEt);
        newInspectionTv = findViewById(R.id.newInspectionTv);
        newInsuranceTv = findViewById(R.id.newInsuranceTv);
        newCarYearEt = findViewById(R.id.newCarYearEt);
        addNewCarBtn = findViewById(R.id.addNewCarBtn);

        newInspectionTv.setText(currentDate);
        newInsuranceTv.setText(currentDate);
        newInspectionTv.setOnClickListener(this);
        newInsuranceTv.setOnClickListener(this);

        addNewCarBtn.setOnClickListener(v -> {
            DatabaseHelper myDB = new DatabaseHelper(AddCarActivity.this);
            myDB.addCar(newCarBrandEt.getText().toString().trim(),
                    newCarModelEt.getText().toString().trim(),
                    Float.parseFloat(newCarEngineEt.getText().toString().trim()),
                    Integer.parseInt(newCarHorseEt.getText().toString().trim()),
                    newInspectionTv.getText().toString().trim(),
                    newInsuranceTv.getText().toString().trim(),
                    Integer.parseInt(newCarYearEt.getText().toString().trim())
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
