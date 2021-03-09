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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<String> vehicleIds, vehicleBrands, vehicleModels, vehicleHorses, vehicleEngines, vehicleYears;
    private Context context;
    private RecyclerViewClickListener listener;

    public RecyclerAdapter(Context context,
                           ArrayList<String> vehicleIds,
                           ArrayList<String> vehicleBrands,
                           ArrayList<String> vehicleModels,
                           ArrayList<String> vehicleEngines,
                           ArrayList<String> vehicleHorses,
                           ArrayList<String> vehicleYears,
                           RecyclerViewClickListener listener) {
        this.vehicleIds = vehicleIds;
        this.vehicleBrands = vehicleBrands;
        this.vehicleModels = vehicleModels;
        this.vehicleEngines = vehicleEngines;
        this.vehicleHorses = vehicleHorses;
        this.vehicleYears = vehicleYears;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    //ustalenie co znajduje się na etykiecie elementu
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {

        String $vehicleHorse = vehicleHorses.get(position) + " hp";

        holder.vehicleBrandTv.setText(String.valueOf(vehicleBrands.get(position)));
        holder.vehicleModelTv.setText(String.valueOf(vehicleModels.get(position)));
        holder.vehicleEngineTv.setText(String.valueOf(vehicleEngines.get(position)));
        holder.vehicleHorseTv.setText($vehicleHorse);
        holder.vehicleYearTv.setText(String.valueOf(vehicleYears.get(position)));
    }

    @Override
    public int getItemCount() {
        return vehicleIds.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView vehicleBrandTv, vehicleModelTv, vehicleHorseTv, vehicleEngineTv, vehicleYearTv, dateTv;

        //Defining elements of a itemView
        public MyViewHolder(@NonNull View view) {
            super(view);
            vehicleBrandTv = view.findViewById(R.id.fuelAmountTv);
            vehicleModelTv = view.findViewById(R.id.fuelCostTv);
            vehicleEngineTv = view.findViewById(R.id.vehicleEngineTv);
            vehicleHorseTv = view.findViewById(R.id.vehicleHorseTv);
            vehicleYearTv = view.findViewById(R.id.vehicleYearTv);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
