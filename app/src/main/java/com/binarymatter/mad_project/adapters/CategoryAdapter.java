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

import com.binarymatter.mad_project.R;
import com.binarymatter.mad_project.activities.CategoryActivity;
import com.binarymatter.mad_project.activities.CategoryListActivity;
import com.binarymatter.mad_project.models.CategoryModal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {

    private CategoryListActivity activity;
    private List<CategoryModal> catList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CategoryAdapter(CategoryListActivity activity, List<CategoryModal> catList) {
        this.activity = activity;
        this.catList = catList;
    }

    public void updateData(int position) {
        CategoryModal item = catList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id", item.getId());
        bundle.putString("name", item.getName());
        bundle.putString("description", item.getDescription());
        Intent intent = new Intent(activity, CategoryActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position) {
        CategoryModal item = catList.get(position);
        db.collection("Categories").document(item.getId()).delete()
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
        catList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.category_item, parent, false);
        return new CatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        holder.name.setText(catList.get(position).getName());
        holder.description.setText(catList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder{

        TextView name, description;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.catItemName);
            description = itemView.findViewById(R.id.catItemDescription);
        }
    }
}