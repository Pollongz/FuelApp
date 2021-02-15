package com.example.fuelapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<String> carIds, carBrands, carModels, carHorses, carEngines, carYears;
    private Context context;
    private RecyclerViewClickListener listener;

    public RecyclerAdapter(Context context,
                           ArrayList<String> carIds,
                           ArrayList<String> carBrands,
                           ArrayList<String> carModels,
                           ArrayList<String> carEngines,
                           ArrayList<String> carHorses,
                           ArrayList<String> carYears,
                           RecyclerViewClickListener listener) {
        this.carIds = carIds;
        this.carBrands = carBrands;
        this.carModels = carModels;
        this.carEngines = carEngines;
        this.carHorses = carHorses;
        this.carYears = carYears;
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

        String $carHorse = carHorses.get(position) + " hp";

        holder.carBrandTv.setText(String.valueOf(carBrands.get(position)));
        holder.carModelTv.setText(String.valueOf(carModels.get(position)));
        holder.carEngineTv.setText(String.valueOf(carEngines.get(position)));
        holder.carHorseTv.setText($carHorse);
        holder.carYearTv.setText(String.valueOf(carYears.get(position)));
        Date date = new Date();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        holder.dateTv.setText(formatter.format(date));
    }

    @Override
    public int getItemCount() {
        return carIds.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView carBrandTv, carModelTv, carHorseTv, carEngineTv, carYearTv, dateTv;

        //Defining elements of a itemView
        public MyViewHolder(@NonNull View view) {
            super(view);
            carBrandTv = view.findViewById(R.id.carBrandTv);
            carModelTv = view.findViewById(R.id.carModelTv);
            carEngineTv = view.findViewById(R.id.carEngineTv);
            carHorseTv = view.findViewById(R.id.carHorseTv);
            carYearTv = view.findViewById(R.id.carYearTv);
            dateTv = view.findViewById(R.id.dateTv);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
