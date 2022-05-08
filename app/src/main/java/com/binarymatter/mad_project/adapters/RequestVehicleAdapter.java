package com.binarymatter.mad_project.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.binarymatter.mad_project.activities.RequestVehicleActivity;
import com.binarymatter.mad_project.activities.VehicleRequestListActivity;
import com.binarymatter.mad_project.models.RequestVehicleModal;
import com.binarymatter.mad_project.R;
import com.binarymatter.mad_project.activities.BuyerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RequestVehicleAdapter extends RecyclerView.Adapter<RequestVehicleAdapter.MyViewHolder> {
    private VehicleRequestListActivity activity;
    private List<RequestVehicleModal> reqList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public RequestVehicleAdapter(VehicleRequestListActivity activity, List<RequestVehicleModal> reqList) {
        this.activity = activity;
        this.reqList = reqList;
    }

    public void updateData(int position) {
        RequestVehicleModal item = reqList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id", item.getId());
        bundle.putString("title", item.getTitle());
        bundle.putString("category", item.getCategory());
        bundle.putString("requiredDate", item.getRequiredDate());
        bundle.putString("budget", String.valueOf(item.getBudget()));
        Intent intent = new Intent(activity, RequestVehicleActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position) {
        RequestVehicleModal item = reqList.get(position);
        db.collection("VehicleRequests").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position) {
        reqList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
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
