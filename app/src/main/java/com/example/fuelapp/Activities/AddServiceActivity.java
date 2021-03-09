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

public class AddServiceActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COLUMN_VEHICLE_ID = "_id";
    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay;
    EditText newServiceTitleEt, newServiceDescEt, newServiceCostEt;
    TextView newServiceDateTv;
    Button addNewServicebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        String currentDate = mDay + "." + mMonth + "." + mYear;

        newServiceTitleEt = findViewById(R.id.newServiceTitleEt);
        newServiceCostEt = findViewById(R.id.newServiceCostEt);
        newServiceDescEt = findViewById(R.id.newServiceDescEt);
        newServiceDateTv = findViewById(R.id.newServiceDateTv);
        addNewServicebtn = findViewById(R.id.addNewServiceBtn);

        newServiceDateTv.setText(currentDate);
        newServiceDateTv.setOnClickListener(this);
        addNewServicebtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == newServiceDateTv) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        String choosenDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        newServiceDateTv.setText(choosenDate);
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();
        } else if (v == addNewServicebtn) {

            Intent intent1 = getIntent();
            String value = intent1.getStringExtra(ProfileActivity.COLUMN_VEHICLE_ID);

            DatabaseHelper myDB = new DatabaseHelper(AddServiceActivity.this);
            myDB.addServices(
                    newServiceTitleEt.getText().toString().trim(),
                    newServiceDescEt.getText().toString().trim(),
                    Float.parseFloat(newServiceCostEt.getText().toString().trim()),
                    newServiceDateTv.getText().toString().trim(),
                    value
            );

            Intent intent2 = new Intent(this, ServiceActivity.class);
            intent2.putExtra(COLUMN_VEHICLE_ID, value);
            startActivity(intent2);
        }
    }
}