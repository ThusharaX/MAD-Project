package com.binarymatter.mad_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.binarymatter.mad_project.R;
import com.google.firebase.auth.FirebaseAuth;

public class BuyerActivity extends AppCompatActivity {
    Button goToRequestVehicle, goToMyRequests, goToPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        goToRequestVehicle = findViewById(R.id.requestVehicle);
        goToMyRequests = findViewById(R.id.myRequests);
        goToPayment = findViewById(R.id.myPayment);

        goToRequestVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RequestVehicleActivity.class));
            }
        });

        goToMyRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VehicleRequestListActivity.class));
            }
        });

        goToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShowAllPaymentActivity.class));
            }
        });

    }


    public void logoutBuyer(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}