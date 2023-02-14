package com.example.foodorderapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderapplication.R;
import com.example.foodorderapplication.models.CartModel;
import com.example.foodorderapplication.ui.home.OrderHistoryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        List<CartModel> list = (ArrayList<CartModel>) getIntent().getSerializableExtra("item");

        if (list != null && list.size() > 0) {
            for (CartModel cartModel : list) {
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("currentTime", cartModel.getCurrentTime());
                cartMap.put("productDate", cartModel.getProductDate());
                cartMap.put("productName", cartModel.getProductName());
                cartMap.put("productPrice", cartModel.getProductPrice());
                cartMap.put("totalQuantity", cartModel.getTotalQuantity());
                cartMap.put("totalPrice", cartModel.getTotalPrice());
                cartMap.put("Status","Confirm");

                firestore.collection("UserOrder").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("Order Confirmation").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(3000);
                                            Intent intent = new Intent(PlaceOrderActivity.this,HomeActivity.class);
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            finish();
                                        }
                                    }
                                });
                                thread.start();
                            }
                        });
                firestore.collection("UserOrder").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("Order Confirmation For User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(3000);
                                            Intent intent = new Intent(PlaceOrderActivity.this,HomeActivity.class);
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            finish();
                                        }
                                    }
                                });
                                thread.start();
                            }
                        });
            }
        }

    }
}