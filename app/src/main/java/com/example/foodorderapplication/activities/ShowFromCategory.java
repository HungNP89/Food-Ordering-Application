package com.example.foodorderapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.adapters.ShowFromCatAdapter;
import com.example.foodorderapplication.models.ShowFromCatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowFromCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowFromCatAdapter catAdapter;
    List<ShowFromCatModel> catModel;

    FirebaseFirestore firebaseFirestore;
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_from_category);

        recyclerView = findViewById(R.id.show_cat_item);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        catModel = new ArrayList<>();
        catAdapter = new ShowFromCatAdapter(this, catModel);
        recyclerView.setAdapter(catAdapter);
        mToolBar = findViewById(R.id.show_cat_toolbar);

        mToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24); // your drawable
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();

        String type = getIntent().getStringExtra("foodType");

        if (type == null || type.isEmpty()) {
            firebaseFirestore.collection("ShowFromCategory")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ShowFromCatModel showFromCatModel = document.toObject(ShowFromCatModel.class);
                                    catModel.add(showFromCatModel);
                                    catAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("burger")) {
            firebaseFirestore.collection("ShowFromCategory").whereEqualTo("foodType", "burger")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot ds : dsList) {
                                ShowFromCatModel showFromCatModel = ds.toObject(ShowFromCatModel.class);
                                catModel.add(showFromCatModel);
                                catAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("pizza")) {
            firebaseFirestore.collection("ShowFromCategory").whereEqualTo("foodType", "pizza")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ShowFromCatModel showFromCatModel = document.toObject(ShowFromCatModel.class);
                                    catModel.add(showFromCatModel);
                                    catAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("chinese")) {
            firebaseFirestore.collection("ShowFromCategory").whereEqualTo("foodType", "chinese")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ShowFromCatModel showFromCatModel = document.toObject(ShowFromCatModel.class);
                                    catModel.add(showFromCatModel);
                                    catAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("japanese")) {
            firebaseFirestore.collection("ShowFromCategory").whereEqualTo("foodType", "japanese")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ShowFromCatModel showFromCatModel = document.toObject(ShowFromCatModel.class);
                                    catModel.add(showFromCatModel);
                                    catAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}