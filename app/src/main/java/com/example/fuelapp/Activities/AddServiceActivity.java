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

public class AddServiceActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_VEHICLE_ID = "_id";
    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay;
    private String choosenDate;
    private EditText newServiceTitleEt, newServiceDescEt, newServiceCostEt;
    private TextView newServiceDateTv;
    private Button addNewServicebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        newServiceTitleEt = findViewById(R.id.newServiceTitleEt);
        newServiceCostEt = findViewById(R.id.newServiceCostEt);
        newServiceDescEt = findViewById(R.id.newServiceDescEt);
        newServiceDateTv = findViewById(R.id.newServiceDateTv);
        addNewServicebtn = findViewById(R.id.addNewServiceBtn);

        newServiceDateTv.setText(getCurrentDate());
        newServiceDateTv.setOnClickListener(this);
        addNewServicebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == newServiceDateTv) {
            chooseDate(newServiceDateTv);
        } else if (v == addNewServicebtn) {
            DatabaseHelper myDB = new DatabaseHelper(AddServiceActivity.this);
            myDB.addServices(
                    newServiceTitleEt.getText().toString().trim(),
                    newServiceDescEt.getText().toString().trim(),
                    Float.parseFloat(newServiceCostEt.getText().toString().trim()),
                    newServiceDateTv.getText().toString().trim(),
                    getCarId()
            );

            Intent intent2 = new Intent(this, ServiceActivity.class);
            intent2.putExtra(COLUMN_VEHICLE_ID, getCarId());
            startActivity(intent2);
        }
    }

    private String getCarId() {
        Intent intent = getIntent();
        return intent.getStringExtra(MainActivity.COLUMN_VEHICLE_ID);
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