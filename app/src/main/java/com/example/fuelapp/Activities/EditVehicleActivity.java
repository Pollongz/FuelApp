package com.example.fuelapp.Activities;

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
import com.example.fuelapp.Database.DatabaseHelper;
import com.example.fuelapp.R;
import java.util.Calendar;

public class EditVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_VEHICLE_ID = "_id";
    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay;
    private String choosenDate;
    private EditText editVehicleBrandEt, editVehicleModelEt, editVehicleHorseEt, editVehicleEngineEt, editVehicleYearEt, editPlateNumberEt;
    private TextView editInspectionTv, editInsuranceTv;
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

    @SuppressLint("Recycle")
    private String getVehiclesData(int option) {
        SQLiteDatabase db = this.openOrCreateDatabase("VehiclesList.db", Context.MODE_PRIVATE, null);
        Cursor cursor;
        if(db != null) {
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
        return null;
    }

    private void chooseDate(TextView option) {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

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

    @Override
    public void onClick(View v) {
        if (v == editInspectionTv) {
            chooseDate(editInspectionTv);
        } else if (v == editInsuranceTv) {
            chooseDate(editInsuranceTv);
        } else if (v == editVehicleBtn) {
            DatabaseHelper myDB = new DatabaseHelper(EditVehicleActivity.this);
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
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    private String getCarId() {
        Intent intent = getIntent();
        return intent.getStringExtra(MainActivity.COLUMN_VEHICLE_ID);
    }
}