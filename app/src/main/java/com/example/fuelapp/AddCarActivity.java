package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddCarActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener insurance_dateListener, inspection_dateListener;
    EditText newCarBrandEt, newCarModelEt, newCarHorseEt, newCarEngineEt, newCarYearEt;
    TextView newInspectionTv, newInsuranceTv;
    Button addNewCarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        newCarBrandEt = findViewById(R.id.newCarBrandEt);
        newCarModelEt = findViewById(R.id.newCarModelEt);
        newCarHorseEt = findViewById(R.id.newCarHorseEt);
        newCarEngineEt = findViewById(R.id.newCarEngineEt);
        newInspectionTv = findViewById(R.id.newInspectionTv);
        newInsuranceTv = findViewById(R.id.newInsuranceTv);
        newCarYearEt = findViewById(R.id.newCarYearEt);
        addNewCarBtn = findViewById(R.id.addNewCarBtn);

        newInspectionTv.setOnClickListener(v -> {
           showDatePickerDialog(1);
            inspection_dateListener = (view, year, month, dayOfMonth) -> {
                String date = dayOfMonth + "-" + month + "-" + year;
                newInspectionTv.setText(date);
            };
        });

        newInsuranceTv.setOnClickListener(v -> {
            showDatePickerDialog(2);
            insurance_dateListener = (view, year, month, dayOfMonth) -> {
                String date = dayOfMonth + "-" + month + "-" + year;
                newInsuranceTv.setText(date);
            };
        });

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

    private void showDatePickerDialog(int id) {

        switch(id) {
            case 1:
                DatePickerDialog inspectionDate = new DatePickerDialog(
                        this,
                        inspection_dateListener,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                inspectionDate.show();
                break;
            case 2:
                DatePickerDialog insuranceDate = new DatePickerDialog(
                        this,
                        insurance_dateListener,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                insuranceDate.show();
                break;
        }
    }
}
