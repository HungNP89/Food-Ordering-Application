package com.example.foodorderapplication.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.adapters.OrderHistoryAdapter;
import com.example.foodorderapplication.models.OrderHistoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class OrderHistoryFragment extends Fragment {

    TextView orderHistory;
    RecyclerView recyclerView;
    Button clear;
    List<OrderHistoryModel> historyModelList;
    OrderHistoryAdapter historyAdapter;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fs;


    public OrderHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        clear = view.findViewById(R.id.btn_clear);
        recyclerView = view.findViewById(R.id.order_history);
        firebaseAuth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyModelList = new ArrayList<>();
        historyAdapter = new OrderHistoryAdapter(getActivity(),historyModelList);
        recyclerView.setAdapter(historyAdapter);

        fs.collection("UserOrder").document(firebaseAuth.getCurrentUser().getUid())
                .collection("Order Confirmation For User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                                String documentId = ds.getId();
                                OrderHistoryModel orderHistoryModel = ds.toObject(OrderHistoryModel.class);
                                orderHistoryModel.setDocumentId(documentId);
                                historyModelList.add(orderHistoryModel);
                                historyAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearHistory();
            }
        });
        return view;
    }

    private void clearHistory() {
        fs.collection("UserOrder").document(firebaseAuth.getCurrentUser().getUid()).collection("Order Confirmation For User")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot qds : task.getResult()) {
                                qds.getReference().delete();
                            }
                            Toast.makeText(getContext(),"Clear History",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}