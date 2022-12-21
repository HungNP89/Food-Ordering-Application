package com.example.foodorderapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.adapters.CartAdapter;
import com.example.foodorderapplication.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    int TotalAmount;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<CartModel> cartModel;
    CartAdapter cartAdapter;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fs;

    TextView cart_total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cart_total_price = findViewById(R.id.cart_total_price);
        fAuth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.my_cart);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver,new IntentFilter("TotalAmount"));

        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModel = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartModel);
        recyclerView.setAdapter(cartAdapter);

        fs.collection("AddToCart").document(fAuth.getCurrentUser().getUid())
                .collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                                CartModel cartModel1 = ds.toObject(CartModel.class);
                                cartModel.add(cartModel1);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount",0);
            cart_total_price.setText("Total Amount :" + totalBill);
        }
    };
}