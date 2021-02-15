package com.example.fuelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String COLUMN_CAR_ID = "_id";
    private DatabaseHelper myDB;
    private RecyclerAdapter.RecyclerViewClickListener listener;
    private ArrayList<String> carIds, carBrands, carModels, carHorses, carEngines, carYears;
    private TextView noDataTv;
    private ImageView noDataIv;
    private RecyclerView recyclerView;
    private RecyclerAdapter RecyclerAdapter;
    private Button goToAddCarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noDataTv = findViewById(R.id.noDataTv);
        noDataIv = findViewById(R.id.noData_imageView);

        recyclerView = findViewById(R.id.carRecycler);
        goToAddCarBtn = findViewById(R.id.goToAddCarBtn);

        goToAddCarBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCarActivity.class);
            startActivity(intent);
        });

        myDB = new DatabaseHelper(MainActivity.this);
        carIds = new ArrayList<>();
        carBrands = new ArrayList<>();
        carModels = new ArrayList<>();
        carEngines = new ArrayList<>();
        carHorses = new ArrayList<>();
        carYears = new ArrayList<>();

        storeCarsInArrays();
        createAdapter();

    }

    private void createAdapter() {
        setOnClickListener();
        RecyclerAdapter = new RecyclerAdapter(MainActivity.this, carIds, carBrands, carModels,  carEngines, carHorses, carYears, listener);
        recyclerView.setAdapter(RecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Toast.makeText(getApplicationContext(), "clicked on " + carIds.get(position), Toast.LENGTH_SHORT).show();
            String value = carIds.get(position);
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra(COLUMN_CAR_ID, value);
            startActivity(intent);
        };
    }

    private void storeCarsInArrays() {
        Cursor cursor = myDB.readAllCars();
            if (cursor.getCount() == 0) {
                noDataIv.setVisibility(View.VISIBLE);
                noDataTv.setVisibility(View.VISIBLE);
            } else {
                while (cursor.moveToNext()) {
                    carIds.add(cursor.getString(0));
                    carBrands.add(cursor.getString(1));
                    carModels.add(cursor.getString(2));
                    carEngines.add(cursor.getString(3));
                    carHorses.add(cursor.getString(4));
                    carYears.add(cursor.getString(7));

                    noDataIv.setVisibility(View.GONE);
                    noDataTv.setVisibility(View.GONE);
                }
            }
    }
}