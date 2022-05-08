package com.binarymatter.mad_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.binarymatter.mad_project.R;
import com.google.firebase.auth.FirebaseAuth;

public class RenterActivity extends AppCompatActivity {

    Button goToVehicleRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter);

        goToVehicleRequests = findViewById(R.id.vehicleRequests);

        goToVehicleRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RenterActivity.this, VehicleRequestListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USER_TYPE", "Renter");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void logoutRenter(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}