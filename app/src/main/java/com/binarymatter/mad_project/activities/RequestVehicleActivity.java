package com.binarymatter.mad_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.binarymatter.mad_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class RequestVehicleActivity extends AppCompatActivity {

    private EditText rvTitle, rvCategory, rvRequiredDate, rvBudget;
    private Button requestVehicleSubmitBtn;
    private FirebaseFirestore db;
    FirebaseAuth fAuth;
    boolean valid = true;

    private String uId, uTitle, uCategory, uRequiredDate, uBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_vehicle);

        fAuth = FirebaseAuth.getInstance();

        rvTitle = findViewById(R.id.rvTitle);
        rvCategory = findViewById(R.id.rvCategory);
        rvRequiredDate = findViewById(R.id.rvRequiredDate);
        rvBudget = findViewById(R.id.rvBudget);
        requestVehicleSubmitBtn = findViewById(R.id.requestVehicleSubmitBtn);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            requestVehicleSubmitBtn.setText("Update");
            uId = bundle.getString("id");
            uTitle = bundle.getString("title");
            uCategory = bundle.getString("category");
            uRequiredDate = bundle.getString("requiredDate");
            uBudget = bundle.getString("budget");

            rvTitle.setText(uTitle);
            rvCategory.setText(uCategory);
            rvRequiredDate.setText(uRequiredDate);
            rvBudget.setText(uBudget);
        } else {
            requestVehicleSubmitBtn.setText("Submit");
        }

        requestVehicleSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(rvTitle);
                checkField(rvCategory);
                checkField(rvRequiredDate);
                checkField(rvBudget);

                if (valid) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    String uID = user.getUid();
                    String title = rvTitle.getText().toString();
                    String category = rvCategory.getText().toString();
                    String requiredDate = rvRequiredDate.getText().toString();
                    Double budget = Double.valueOf(rvBudget.getText().toString());

                    Bundle bundle1 = getIntent().getExtras();
                    if (bundle1 != null) {
                        String id = uId;
                        updateToFireStore(id, title, category, requiredDate, budget);
                    } else {
                        String id = UUID.randomUUID().toString();
                        saveToFireStore(id, title, category, requiredDate, budget, uID);
                    }
                }
            }
        });
    }

    private void updateToFireStore(String id, String title, String category, String requiredDate, Double budget) {
        db.collection("VehicleRequests").document(id).update("title", title, "category", category, "requiredDate", requiredDate, "budget", budget)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RequestVehicleActivity.this, "Data Updated !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), VehicleRequestListActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RequestVehicleActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RequestVehicleActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String title, String category, String requiredDate, Double budget, String uID) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("title", title);
        data.put("category", category);
        data.put("requiredDate", requiredDate);
        data.put("budget", budget);
        data.put("uID", uID);

        db.collection("VehicleRequests").document(id).set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RequestVehicleActivity.this, "Vehicle Request Added !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), BuyerActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RequestVehicleActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}