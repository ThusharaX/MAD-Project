package com.binarymatter.mad_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.binarymatter.mad_project.R;
import com.binarymatter.mad_project.adapters.CategoryAdapter;
import com.binarymatter.mad_project.models.CategoryModal;
import com.binarymatter.mad_project.utils.CategoryTouchHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategories;
    private FirebaseFirestore db;
    private CategoryAdapter adapter;
    private List<CategoryModal> list;
    FirebaseAuth fAuth;
    TextView textViewCatTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        textViewCatTopic = findViewById(R.id.textViewCatTopic);

        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this));

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new CategoryAdapter(this, list);
        recyclerViewCategories.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new CategoryTouchHelper(adapter));
        touchHelper.attachToRecyclerView(recyclerViewCategories);

        showData();
    }

    public void showData() {
//        FirebaseUser user = fAuth.getCurrentUser();
//        Bundle bundle = getIntent().getExtras();

        textViewCatTopic.setText("All Categories");
        db.collection("Categories").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            CategoryModal categoryModal = new CategoryModal(snapshot.getString("id"), snapshot.getString("name"), snapshot.getString("description"));
                            list.add(categoryModal);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CategoryListActivity.this, "Oops... Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}