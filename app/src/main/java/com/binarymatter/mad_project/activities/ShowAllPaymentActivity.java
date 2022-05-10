package com.binarymatter.mad_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.widget.Toast;

import com.binarymatter.mad_project.R;
import com.binarymatter.mad_project.adapters.PaymentAdapter;
import com.binarymatter.mad_project.models.PaymentModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllPaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private PaymentAdapter adapter;
    private List<PaymentModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_payment);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        list = new ArrayList<>();
        adapter = new PaymentAdapter(this, list);
        recyclerView.setAdapter(adapter);

        showData();
    }

    private void showData(){

        db.collection("Payments").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for(DocumentSnapshot snapshot: task.getResult()){

                            PaymentModel payment = new PaymentModel(snapshot.getString("id"),snapshot.getString("cardHName"), snapshot.getString("cardNumber"), snapshot.getString("expDate"), snapshot.getString("cvv"));
                            list.add(payment);
                        }

                            adapter.notifyDataSetChanged();
                    }
                }
                ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(ShowAllPaymentActivity.this, "Oops.. Something Wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }


}