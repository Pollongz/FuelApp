package com.example.fuelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class RecyclerFuelAdapter extends RecyclerView.Adapter<RecyclerFuelAdapter.MyViewHolder> {

    public static final String COLUMN_CAR_ID = "_id";
    public static final String COLUMN_FUELED_CAR_ID = "fueled_car_id";
    public static final String VALUE = "value";
    private ArrayList<String> fuelIds, stationNames, fuelTypes, fuelAmounts, fuelCosts, mileages, fuelDates, fueledCarIds;
    private Context context;
    public RecyclerViewClickListener listener;

    public RecyclerFuelAdapter(Context context,
                               ArrayList<String> fuelIds,
                               ArrayList<String> stationNames,
                               ArrayList<String> fuelTypes,
                               ArrayList<String> fuelAmounts,
                               ArrayList<String> fuelCosts,
                               ArrayList<String> mileages,
                               ArrayList<String> fuelDates,
                               ArrayList<String> fueledCarIds,
                               RecyclerViewClickListener listener) {
        this.fuelIds = fuelIds;
        this.stationNames = stationNames;
        this.fuelTypes = fuelTypes;
        this.fuelAmounts = fuelAmounts;
        this.fuelCosts = fuelCosts;
        this.mileages = mileages;
        this.fuelDates = fuelDates;
        this.fueledCarIds = fueledCarIds;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerFuelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View fuelView = inflater.inflate(R.layout.list_fuel, parent, false);
        return new MyViewHolder(fuelView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerFuelAdapter.MyViewHolder holder, int position) {

        String $fuelAmount = fuelAmounts.get(position) + "L";
        String $fuelCost = fuelCosts.get(position) + "$";

        holder.fuelIdTv.setText(String.valueOf(fuelIds.get(position)));
        holder.fuelAmountTv.setText($fuelAmount);
        holder.fuelCostTv.setText($fuelCost);
        holder.fuelDateTv.setText(String.valueOf(fuelDates.get(position)));
        holder.fueledCarTv.setText(String.valueOf(fueledCarIds.get(position)));
    }

    @Override
    public int getItemCount() {
        return fuelIds.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView fuelIdTv, fuelAmountTv, fuelCostTv, fuelDateTv, fueledCarTv;

        public MyViewHolder(@NonNull View view) {
            super(view);
            fuelIdTv = view.findViewById(R.id.fuelIdTv);
            fuelAmountTv = view.findViewById(R.id.carBrandTv);
            fuelCostTv = view.findViewById(R.id.carModelTv);
            fuelDateTv = view.findViewById(R.id.carYearTv);
            fueledCarTv = view.findViewById(R.id.fueledCarTv);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
