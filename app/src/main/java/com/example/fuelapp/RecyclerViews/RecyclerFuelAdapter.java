package com.example.fuelapp.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelapp.R;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class RecyclerFuelAdapter extends RecyclerView.Adapter<RecyclerFuelAdapter.MyViewHolder> {

    private ArrayList<String> fuelIds, fuelTypes, fuelAmounts, fuelCosts, mileages, fuelDates, fueledVehicleIds;
    private Context context;
    public RecyclerViewLongClickListener hListener;

    public RecyclerFuelAdapter(Context context,
                               ArrayList<String> fuelIds,
                               ArrayList<String> fuelTypes,
                               ArrayList<String> fuelAmounts,
                               ArrayList<String> fuelCosts,
                               ArrayList<String> mileages,
                               ArrayList<String> fuelDates,
                               ArrayList<String> fueledVehicleIds,
                               RecyclerViewLongClickListener hListener) {
        this.fuelIds = fuelIds;
        this.fuelTypes = fuelTypes;
        this.fuelAmounts = fuelAmounts;
        this.fuelCosts = fuelCosts;
        this.mileages = mileages;
        this.fuelDates = fuelDates;
        this.fueledVehicleIds = fueledVehicleIds;
        this.context = context;
        this.hListener = hListener;
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

        String fuelAmount = fuelAmounts.get(position) + " L";
        String fuelCost = fuelCosts.get(position) + " PLN";

        holder.fuelTypeTv.setText(String.valueOf(fuelTypes.get(position)));
        holder.fuelAmountTv.setText(fuelAmount);
        holder.fuelCostTv.setText(fuelCost);
        holder.mileageTv.setText(String.valueOf(mileages.get(position)));
        holder.fuelDateTv.setText(String.valueOf(fuelDates.get(position)));
    }

    @Override
    public int getItemCount() {
        return fuelIds.size();
    }

    public interface RecyclerViewLongClickListener {
        void onLongClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView fuelTypeTv, fuelAmountTv, fuelCostTv, mileageTv, fuelDateTv;

        public MyViewHolder(@NonNull View view) {
            super(view);

            fuelTypeTv = view.findViewById(R.id.fuelTypeTv);
            fuelAmountTv = view.findViewById(R.id.fuelAmountTv);
            fuelCostTv = view.findViewById(R.id.fuelCostTv);
            mileageTv = view.findViewById(R.id.mileageTv);
            fuelDateTv = view.findViewById(R.id.serviceDateTv);

            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            hListener.onLongClick(v, getAdapterPosition());
            return false;
        }
    }
}
