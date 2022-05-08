package com.binarymatter.mad_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.binarymatter.mad_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class PaymentActivity extends AppCompatActivity {

    private EditText etCardHolderName,  etCardNumber, etExpDate, etCvv;
    private Button mPay;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        etCardHolderName = findViewById(R.id.rvCardHolderName);
        etCardNumber = findViewById(R.id.rvCardNumber);
        etExpDate = findViewById(R.id.rvExpireDate);
        etCvv = findViewById(R.id.rvCvv);
        mPay = findViewById(R.id.PaySubmitBtn);

        db = FirebaseFirestore.getInstance();

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardHName = etCardHolderName.getText().toString();
                String cardNumber = etCardNumber.getText().toString();
                String expDate = etExpDate.getText().toString();
                String cvv = etCvv.getText().toString();
                String id = UUID.randomUUID().toString();

                saveToFireStore(id,cardHName,cardNumber,expDate,cvv);
            }
        });
    }

    private void saveToFireStore(String id, String cardHName, String cardNumber, String expDate, String cvv) {

        if(!cardHName.isEmpty() && !cardNumber.isEmpty() && !expDate.isEmpty() && !cvv.isEmpty()) {

            HashMap<String, Object>map = new HashMap<>();
            map.put("id", id);
            map.put("cardHName", cardHName);
            map.put("cardNumber", cardNumber);
            map.put("expDate",expDate);
            map.put("cvv",cvv);

            db.collection("Payments").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()) {
                                Toast.makeText(PaymentActivity.this, "Payment Successfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(PaymentActivity.this, "Payment UnSucessful", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {

            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
        }

    }
}