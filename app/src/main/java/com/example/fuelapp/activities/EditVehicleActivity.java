package com.example.fuelapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fuelapp.database.DatabaseHelper;
import com.example.fuelapp.R;
import java.util.Calendar;

public class EditVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    final Calendar c = Calendar.getInstance();
    private int mYear;
    private int mMonth;
    private int mDay;
    private String chosenDate;
    private EditText editVehicleBrandEt;
    private EditText editVehicleModelEt;
    private EditText editVehicleHorseEt;
    private EditText editVehicleEngineEt;
    private EditText editVehicleYearEt;
    private EditText editPlateNumberEt;
    private TextView editInspectionTv;
    private TextView editInsuranceTv;
    private Button editVehicleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        editVehicleBrandEt = findViewById(R.id.editVehicleBrandEt);
        editVehicleModelEt = findViewById(R.id.editVehicleModelEt);
        editVehicleHorseEt = findViewById(R.id.editVehicleHorseEt);
        editVehicleEngineEt = findViewById(R.id.editVehicleEngineEt);
        editInspectionTv = findViewById(R.id.editInspectionTv);
        editInsuranceTv = findViewById(R.id.editInsuranceTv);
        editVehicleYearEt = findViewById(R.id.editVehicleYearEt);
        editPlateNumberEt = findViewById(R.id.editPlateNumberEt);
        editVehicleBtn = findViewById(R.id.editVehicleBtn);

        editVehicleBrandEt.setText(getVehiclesData(1));
        editVehicleModelEt.setText(getVehiclesData(2));
        editVehicleEngineEt.setText(getVehiclesData(3));
        editVehicleHorseEt.setText(getVehiclesData(4));
        editInspectionTv.setText(getVehiclesData(5));
        editInsuranceTv.setText(getVehiclesData(6));
        editVehicleYearEt.setText(getVehiclesData(7));
        editPlateNumberEt.setText(getVehiclesData(8));

        editInspectionTv.setOnClickListener(this);
        editInsuranceTv.setOnClickListener(this);
        editVehicleBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
             new AlertDialog.Builder(EditVehicleActivity.this)
            .setTitle("Discard changes")
            .setMessage("Are you sure you want to discard all changes?")
            .setPositiveButton(R.string.yes, (dialog, position) -> {
                super.onBackPressed();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            })
            .setNegativeButton(R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    @SuppressLint("Recycle")
    private String getVehiclesData(int option) {
        Cursor cursor;

        try(SQLiteDatabase db = this.openOrCreateDatabase("VehiclesList.db", Context.MODE_PRIVATE, null)) {
            cursor = db.rawQuery("SELECT _id,vehicle_brand,vehicle_model,vehicle_engine_capacity,vehicle_horse_power,vehicle_inspection_date,vehicle_insurance_date,vehicle_year_made, vehicle_plate_number  FROM vehicles WHERE _id = " + getCarId(), null);

            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
            }
            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()) {
                buffer.append(cursor.getString(option));
            }
            return buffer.toString();
        }
    }

    private void chooseDate(TextView option) {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    if (dayOfMonth < 10 && monthOfYear < 9) {
                        chosenDate = "0" + dayOfMonth + ".0" + (monthOfYear + 1) + "." + year;
                    } else if (dayOfMonth < 10) {
                        chosenDate = "0" + dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                    } else if (monthOfYear < 9) {
                        chosenDate = dayOfMonth + ".0" + (monthOfYear + 1) + "." + year;
                    } else {
                        chosenDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                    }
                    option.setText(chosenDate);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == editInspectionTv) {
            chooseDate(editInspectionTv);
        } else if (v == editInsuranceTv) {
            chooseDate(editInsuranceTv);
        } else if (v == editVehicleBtn) {
            if (editVehicleBrandEt.getText().toString().trim().isEmpty()) {
                emptyError(editVehicleBrandEt);
            } else if (editVehicleModelEt.getText().toString().trim().isEmpty()) {
                emptyError(editVehicleModelEt);
            } else if (editVehicleEngineEt.getText().toString().trim().isEmpty()) {
                emptyError(editVehicleEngineEt);
            } else if (editVehicleHorseEt.getText().toString().trim().isEmpty()) {
                emptyError(editVehicleHorseEt);
            } else if (editVehicleYearEt.getText().toString().trim().isEmpty()) {
                emptyError(editVehicleYearEt);
            } else if (editPlateNumberEt.getText().toString().trim().isEmpty()) {
                emptyError(editPlateNumberEt);
            } else {
                try(DatabaseHelper myDB = new DatabaseHelper(EditVehicleActivity.this)) {
                    myDB.editVehicle(getCarId(),
                            editVehicleBrandEt.getText().toString().trim(),
                            editVehicleModelEt.getText().toString().trim(),
                            Float.parseFloat(editVehicleEngineEt.getText().toString().trim()),
                            Integer.parseInt(editVehicleHorseEt.getText().toString().trim()),
                            editInspectionTv.getText().toString().trim(),
                            editInsuranceTv.getText().toString().trim(),
                            Integer.parseInt(editVehicleYearEt.getText().toString().trim()),
                            editPlateNumberEt.getText().toString().trim()
                    );
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private void emptyError(TextView option) {
        option.setError("This field is required");
        option.requestFocus();
    }

    private String getCarId() {
        Intent intent = getIntent();
        return intent.getStringExtra(MainActivity.COLUMN_VEHICLE_ID);
    }
}