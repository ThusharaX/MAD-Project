package com.binarymatter.mad_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.binarymatter.mad_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar feedbackRating;
    int ratingNumber;
    private Button feedbackSubmitBtn;
    private EditText editTextFeedback;

    private FirebaseFirestore db;

    FirebaseAuth fAuth;

    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        fAuth = FirebaseAuth.getInstance();

//        feedbackRating = findViewById(R.id.feedbackRatingBar);
//        ratingNumber = feedbackRating.getNumStars();
        feedbackRating=(RatingBar)findViewById(R.id.feedbackRatingBar);

        editTextFeedback = findViewById(R.id.editTextFeedback);
        feedbackSubmitBtn = findViewById(R.id.feedbackSubmitBtn);

        db = FirebaseFirestore.getInstance();

        feedbackSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(editTextFeedback);
                String rating=String.valueOf(feedbackRating.getRating());

                if (valid) {
                    String id = UUID.randomUUID().toString();
                    String feedback = editTextFeedback.getText().toString();
                    saveToFireStore(id, rating, feedback);
                }
            }
        });
    }

    private void saveToFireStore(String id, String rating, String feedback) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("rating", rating);
        data.put("feedback", feedback);

        db.collection("Feedbacks").document(id).set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FeedbackActivity.this, "Feedback Added !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), BuyerActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FeedbackActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Validation
    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }
}