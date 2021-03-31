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

public class EditServiceActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_SERVICE_ID = "service_id";
    final Calendar c = Calendar.getInstance();
    private int mYear;
    private int mMonth;
    private int mDay;
    private String chosenDate;
    private EditText editServiceTitleEt;
    private EditText editServiceDescEt;
    private EditText editServiceCostEt;
    private TextView editServiceDateTv;
    private Button editServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        editServiceTitleEt = findViewById(R.id.editServiceTitleEt);
        editServiceCostEt = findViewById(R.id.editServiceCostEt);
        editServiceDescEt = findViewById(R.id.editServiceDescEt);
        editServiceDateTv = findViewById(R.id.editServiceDateTv);
        editServiceBtn = findViewById(R.id.editServiceBtn);

        editServiceTitleEt.setText(getServicesData(1));
        editServiceDescEt.setText(getServicesData(2));
        editServiceCostEt.setText(getServicesData(3));
        editServiceDateTv.setText(getServicesData(4));

        editServiceDateTv.setOnClickListener(this);
        editServiceBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(EditServiceActivity.this)
                .setTitle("Discard changes")
                .setMessage("Are you sure you want to discard all changes?")
                .setPositiveButton(R.string.yes, (dialog, position) -> {
                    super.onBackPressed();
                    Intent intent = new Intent(getApplicationContext(), ServiceDetailsActivity.class);
                    intent.putExtra(COLUMN_SERVICE_ID, getServiceId());
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @SuppressLint("Recycle")
    private String getServicesData(int option) {
        Cursor cursor;
        try(SQLiteDatabase db = this.openOrCreateDatabase("VehiclesList.db", Context.MODE_PRIVATE, null)) {
            cursor = db.rawQuery("SELECT service_id,service_title,service_desc,service_cost,service_date,serviced_vehicle_id  FROM services WHERE service_id = " + getServiceId(), null);

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

    @Override
    public void onClick(View v) {
        if (v == editServiceDateTv) {
            chooseDate(editServiceDateTv);
        } else if (v == editServiceBtn) {
            if (editServiceTitleEt.getText().toString().trim().isEmpty()) {
                emptyError(editServiceTitleEt);
            } else if (editServiceDescEt.getText().toString().trim().isEmpty()) {
                emptyError(editServiceDescEt);
            } else if (editServiceCostEt.getText().toString().trim().isEmpty()) {
                emptyError(editServiceCostEt);
            } else {
                try(DatabaseHelper myDB = new DatabaseHelper(EditServiceActivity.this)) {
                    myDB.editService(
                            getServiceId(),
                            editServiceTitleEt.getText().toString().trim(),
                            editServiceDescEt.getText().toString().trim(),
                            Float.parseFloat(editServiceCostEt.getText().toString().trim()),
                            editServiceDateTv.getText().toString().trim()
                    );
                }

                Intent intent = new Intent(getApplicationContext(), ServiceDetailsActivity.class);
                intent.putExtra(COLUMN_SERVICE_ID, getServiceId());
                startActivity(intent);
            }
        }
    }

    private void emptyError(TextView option) {
        option.setError("This field is required");
        option.requestFocus();
    }

    private String getServiceId() {
        Intent intent = getIntent();
        return intent.getStringExtra(ServiceActivity.COLUMN_SERVICE_ID);
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
}