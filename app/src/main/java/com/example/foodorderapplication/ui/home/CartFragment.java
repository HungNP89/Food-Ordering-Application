package com.example.foodorderapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.activities.PaymentActivity;
import com.example.foodorderapplication.activities.PlaceOrderActivity;
import com.example.foodorderapplication.adapters.CartAdapter;
import com.example.foodorderapplication.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    int TotalAmount;
    RecyclerView recyclerView;
    List<CartModel> cartModel;
    CartAdapter cartAdapter;
    Button btnPayment;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fs;

    TextView cart_total_price;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cart_total_price = view.findViewById(R.id.cart_total_price);
        btnPayment = view.findViewById(R.id.btn_payment);
        fAuth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModel = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), cartModel);
        recyclerView.setAdapter(cartAdapter);

        fs.collection("UserOrder").document(fAuth.getCurrentUser().getUid())
                .collection("Add To Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                                String documentId = ds.getId();
                                CartModel cartModel1 = ds.toObject(CartModel.class);
                                cartModel1.setDocumentId(documentId);
                                cartModel.add(cartModel1);
                                cartAdapter.notifyDataSetChanged();
                            }

                            calculateTotalAmount(cartModel);
                        }
                    }
                });
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                Intent moveToOrder = new Intent(getContext(),
                        PlaceOrderActivity.class);
                moveToOrder.putExtra("item",(Serializable) cartModel);
                startActivity(moveToOrder);
            }
        });
        return view;
    }

    private void clearAll() {
        fs.collection("UserOrder").document(fAuth.getCurrentUser().getUid()).collection("Add To Cart")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot qds : task.getResult()) {
                                qds.getReference().delete();
                            }
                            Toast.makeText(getContext(),"Clear All",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void calculateTotalAmount(List<CartModel> cartModel) {
        int totalPrice = 0;
        for (CartModel cartModel1 : cartModel) {
            totalPrice += cartModel1.getTotalPrice();
        }

        cart_total_price.setText("Total Amount :" + totalPrice);
    }

}