package com.example.foodorderapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.adapters.DealMenuAdapter;
import com.example.foodorderapplication.models.DealMenuModel;
import com.example.foodorderapplication.models.ShowFromCatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AboutDealActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DealMenuModel> dealMenuModelsList;
    DealMenuAdapter dealMenuAdapter;
    ImageView imageView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_deal);

        String type = getIntent().getStringExtra("discount");

        recyclerView = findViewById(R.id.deal_items);
        imageView = findViewById(R.id.deal_items_image);
        toolbar = findViewById(R.id.deal_toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dealMenuModelsList = new ArrayList<>();
        dealMenuAdapter = new DealMenuAdapter(this, dealMenuModelsList);
        recyclerView.setAdapter(dealMenuAdapter);

        FirebaseFirestore fs = FirebaseFirestore.getInstance();

        if(type == null || type.isEmpty()) {
            fs.collection("FoodDiscount").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            DealMenuModel dealMenuModel = document.toObject(DealMenuModel.class);
                            dealMenuModelsList.add(dealMenuModel);
                            dealMenuAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        if (type != null && type.equalsIgnoreCase("breakfast")) {
            fs.collection("FoodDiscount").whereEqualTo("discount", "breakfast")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot ds : dsList) {
                                DealMenuModel dealMenuModel = ds.toObject(DealMenuModel.class);
                                dealMenuModelsList.add(dealMenuModel);
                                dealMenuAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("lunch")) {
            imageView.setImageResource(R.drawable.lunch);
            fs.collection("FoodDiscount").whereEqualTo("discount", "lunch")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot ds : dsList) {
                                DealMenuModel dealMenuModel = ds.toObject(DealMenuModel.class);
                                dealMenuModelsList.add(dealMenuModel);
                                dealMenuAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("dinner")) {
            imageView.setImageResource(R.drawable.dinner);
            fs.collection("FoodDiscount").whereEqualTo("discount", "dinner")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot ds : dsList) {
                                DealMenuModel dealMenuModel = ds.toObject(DealMenuModel.class);
                                dealMenuModelsList.add(dealMenuModel);
                                dealMenuAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
}