package com.binarymatter.mad_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.binarymatter.mad_project.activities.VehicleRequestListActivity;
import com.binarymatter.mad_project.models.RequestVehicleModal;
import com.binarymatter.mad_project.R;
import com.binarymatter.mad_project.activities.BuyerActivity;

import java.util.List;

public class RequestVehicleAdapter extends RecyclerView.Adapter<RequestVehicleAdapter.MyViewHolder> {
    private VehicleRequestListActivity activity;
    private List<RequestVehicleModal> reqList;

    public RequestVehicleAdapter(VehicleRequestListActivity activity, List<RequestVehicleModal> reqList) {
        this.activity = activity;
        this.reqList = reqList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.request_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(reqList.get(position).getTitle());
        holder.category.setText(reqList.get(position).getCategory());
        holder.requiredDate.setText(reqList.get(position).getRequiredDate());
        holder.budget.setText(String.valueOf(reqList.get(position).getBudget()));
    }

    @Override
    public int getItemCount() {
        return reqList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, category, requiredDate, budget;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.reqItemTitle);
            category = itemView.findViewById(R.id.reqItemCategory);
            requiredDate = itemView.findViewById(R.id.reqItemRequiredDate);
            budget = itemView.findViewById(R.id.reqItemBudget);
        }
    }
}
