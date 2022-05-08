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

public class CategoryActivity extends AppCompatActivity {

    private EditText catName, catDescription;
    private Button categorySubmitBtn;
    private FirebaseFirestore db;
    FirebaseAuth fAuth;
    boolean valid = true;

    private String uId, uCatName, uCatDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        fAuth = FirebaseAuth.getInstance();

        catName = findViewById(R.id.catName);
        catDescription = findViewById(R.id.catDescription);
        categorySubmitBtn = findViewById(R.id.categorySubmitBtn);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categorySubmitBtn.setText("Update");
            uId = bundle.getString("id");
            uCatName = bundle.getString("name");
            uCatDescription = bundle.getString("description");

            catName.setText(uCatName);
            catDescription.setText(uCatDescription);
        } else {
            categorySubmitBtn.setText("Submit");
        }

        categorySubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(catName);
                checkField(catDescription);

                if (valid) {
                    FirebaseUser user = fAuth.getCurrentUser();
//                    String uID = user.getUid();
                    String name = catName.getText().toString();
                    String description = catDescription.getText().toString();

                    Bundle bundle1 = getIntent().getExtras();
                    if (bundle1 != null) {
                        String id = uId;
                        updateToFireStore(id, name, description);
                    } else {
                        String id = UUID.randomUUID().toString();
                        saveToFireStore(id, name, description);
                    }
                }
            }
        });
    }

    private void updateToFireStore(String id, String name, String description) {
        db.collection("Categories").document(id).update("name", name, "description", description)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CategoryActivity.this, "Data Updated !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CategoryListActivity.class));
                            finish();
                        } else {
                            Toast.makeText(CategoryActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String catName, String catDescription) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("name", catName);
        data.put("description", catDescription);

        db.collection("Categories").document(id).set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CategoryActivity.this, "Category Added !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CategoryActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
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