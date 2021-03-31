package com.example.fuelapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fuelapp.Activities.EditVehicleActivity;
import com.example.fuelapp.Activities.FuelActivity;
import com.example.fuelapp.Activities.MainActivity;
import com.example.fuelapp.Activities.ServiceActivity;
import com.example.fuelapp.Database.DatabaseHelper;
import com.example.fuelapp.R;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String COLUMN_VEHICLE_ID = "_id";
    private TextView myVehicleBrand;
    private TextView myVehicleModel;
    private TextView myVehicleYear;
    private TextView myEngineCapacity;
    private TextView myEnginePower;
    private TextView myInspectionDate;
    private TextView myInsuranceDate;
    private TextView myPlateNumber;
    private Button goToFuelList;
    private Button goToServiceList;
    private Button deleteItem;
    private Button editVehicle;
    private String value;

    public static ProfileFragment newInstance(String value) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(COLUMN_VEHICLE_ID, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            value = getArguments().getString(COLUMN_VEHICLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        myVehicleBrand = view.findViewById(R.id.myVehicleBrand);
        myVehicleModel = view.findViewById(R.id.myVehicleModel);
        myVehicleYear = view.findViewById(R.id.myVehicleYear);
        myEngineCapacity  = view.findViewById(R.id.myEngineCapacity);
        myEnginePower  = view.findViewById(R.id.myEnginePower);
        myInspectionDate  = view.findViewById(R.id.myInspectionDate);
        myInsuranceDate  = view.findViewById(R.id.myInsuranceDate);
        myPlateNumber  = view.findViewById(R.id.myPlateNumber);
        goToFuelList = view.findViewById(R.id.goToFuelList);
        goToServiceList = view.findViewById(R.id.goToServiceList);
        deleteItem = view.findViewById(R.id.deleteItem);
        editVehicle = view.findViewById(R.id.editVehicle);

        myVehicleBrand.setText(getVehiclesData(1));
        myVehicleModel.setText(getVehiclesData(2));
        myEngineCapacity.setText(getVehiclesData(3));
        myEnginePower.setText(getVehiclesData(4));
        myInspectionDate.setText(getVehiclesData(5));
        myInsuranceDate.setText(getVehiclesData(6));
        myVehicleYear.setText(getVehiclesData(7));
        myPlateNumber.setText(getVehiclesData(8));

        goToFuelList.setOnClickListener(this);
        goToServiceList.setOnClickListener(this);
        deleteItem.setOnClickListener(this);
        editVehicle.setOnClickListener(this);

        return view;
    }

    @SuppressLint("Recycle")
    private String getVehiclesData(int option) {
        Cursor cursor;
        try(SQLiteDatabase db = getActivity().openOrCreateDatabase("VehiclesList.db", Context.MODE_PRIVATE, null)) {
            cursor = db.rawQuery("SELECT _id,vehicle_brand,vehicle_model,vehicle_engine_capacity,vehicle_horse_power,vehicle_inspection_date,vehicle_insurance_date,vehicle_year_made, vehicle_plate_number  FROM vehicles WHERE _id = " + value, null);

            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()) {
                buffer.append(cursor.getString(option));
            }
            return buffer.toString();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == goToFuelList) {
            Intent intent1 = new Intent(getContext(), FuelActivity.class);
            intent1.putExtra(COLUMN_VEHICLE_ID, value);
            startActivity(intent1);
        } else if (v == goToServiceList) {
            Intent intent1 = new Intent(getContext(), ServiceActivity.class);
            intent1.putExtra(COLUMN_VEHICLE_ID, value);
            startActivity(intent1);
        } else if (v == deleteItem) {
            try(DatabaseHelper myDB = new DatabaseHelper(getActivity())) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete vehicle")
                        .setMessage("Are you sure you want to delete this vehicle from the list?")
                        .setPositiveButton(android.R.string.ok, (dialog, position) -> {
                            myDB.deleteOneVehicle(value);
                            Intent intent12 = new Intent(getContext(), MainActivity.class);
                            startActivity(intent12);
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } else if (v == editVehicle) {
            Intent intent1 = new Intent(getContext(), EditVehicleActivity.class);
            intent1.putExtra(COLUMN_VEHICLE_ID, value);
            startActivity(intent1);
        }
    }
}