package com.binarymatter.mad_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.binarymatter.mad_project.R;
import com.binarymatter.mad_project.adapters.RequestVehicleAdapter;
import com.binarymatter.mad_project.models.RequestVehicleModal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VehicleRequestListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMyRequests;
    private FirebaseFirestore db;
    private RequestVehicleAdapter adapter;
    private List<RequestVehicleModal> list;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_request_list);

        recyclerViewMyRequests = findViewById(R.id.recyclerViewMyRequests);
        recyclerViewMyRequests.setLayoutManager(new LinearLayoutManager(this));

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new RequestVehicleAdapter(this, list);
        recyclerViewMyRequests.setAdapter(adapter);

        showData();
    }

    private void showData() {
        FirebaseUser user = fAuth.getCurrentUser();
        db.collection("VehicleRequests").whereEqualTo("uID", user.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for(DocumentSnapshot snapshot : task.getResult()) {
                            RequestVehicleModal requestVehicleModal = new RequestVehicleModal(snapshot.getString("id"), snapshot.getString("title"), snapshot.getString("category"), snapshot.getString("requiredDate"), snapshot.getDouble("budget"));
                            list.add(requestVehicleModal);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VehicleRequestListActivity.this, "Oops... Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}