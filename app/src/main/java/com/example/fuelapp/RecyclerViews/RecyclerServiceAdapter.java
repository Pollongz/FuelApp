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

public class RecyclerServiceAdapter extends RecyclerView.Adapter<RecyclerServiceAdapter.MyViewHolder> {

    private final ArrayList<String> serviceIds, serviceTitles, serviceDescriptions, serviceCosts, serviceDates, servicedVehicleIds;
    private Context context;
    public RecyclerViewClickListener listener;
    public RecyclerViewLongClickListener hListener;

    public RecyclerServiceAdapter (Context context,
                                   ArrayList<String> serviceIds,
                                   ArrayList<String> serviceTitles,
                                   ArrayList<String> serviceDescriptions,
                                   ArrayList<String> serviceCosts,
                                   ArrayList<String> serviceDates,
                                   ArrayList<String> servicedVehicleIds,
                                   RecyclerViewClickListener listener,
                                   RecyclerViewLongClickListener hListener
    ) {
        this.context = context;
        this.serviceIds = serviceIds;
        this.serviceTitles = serviceTitles;
        this.serviceDescriptions = serviceDescriptions;
        this.serviceCosts = serviceCosts;
        this.serviceDates = serviceDates;
        this.servicedVehicleIds = servicedVehicleIds;
        this.listener = listener;
        this.hListener = hListener;
    }

    @NonNull
    @Override
    public RecyclerServiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View serviceView = inflater.inflate(R.layout.list_service, parent, false);
        return new MyViewHolder(serviceView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerServiceAdapter.MyViewHolder holder, int position) {

        String $serviceCost = serviceCosts.get(position) + " PLN";

        holder.serviceTitleTv.setText(String.valueOf(serviceTitles.get((position))));
        holder.serviceDescTv.setText(String.valueOf(serviceDescriptions.get((position))));
        holder.serviceCostTv.setText($serviceCost);
        holder.serviceDateTv.setText(String.valueOf(serviceDates.get((position))));
    }

    @Override
    public int getItemCount() {
        return serviceIds.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public interface RecyclerViewLongClickListener {
        void onLongClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final TextView serviceTitleTv, serviceDescTv, serviceCostTv, serviceDateTv;

        public MyViewHolder(@NonNull View view) {
            super(view);

            serviceTitleTv = view.findViewById(R.id.serviceTitleTv);
            serviceDescTv = view.findViewById(R.id.serviceDesctv);
            serviceCostTv = view.findViewById(R.id.serviceCostTv);
            serviceDateTv = view.findViewById(R.id.serviceDateTv);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            hListener.onLongClick(v, getAdapterPosition());
            return false;
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
