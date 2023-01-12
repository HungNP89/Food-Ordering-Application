package com.example.foodorderapplication.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.adapters.DealAdapter;
import com.example.foodorderapplication.models.DealModel;
import com.example.foodorderapplication.models.HorizontalMenuModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DealsFragment extends Fragment {
    RecyclerView dealMenuRec;
    List<DealModel> dealModelList;
    DealAdapter dealAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deals, container, false);

        FirebaseFirestore fs = FirebaseFirestore.getInstance();

        dealMenuRec = view.findViewById(R.id.deal_meals);
        dealModelList = new ArrayList<>();
        fs.collection("DiscountCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DealModel dealMenuModel = document.toObject(DealModel.class);
                                dealModelList.add(dealMenuModel);
                                dealAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("", "Error getting documents.", task.getException());
                        }
                    }
                });

        dealAdapter = new DealAdapter(getContext(),dealModelList);
        dealMenuRec.setAdapter(dealAdapter);
        dealMenuRec.setLayoutManager(new LinearLayoutManager(getContext()));
        dealMenuRec.setHasFixedSize(true);
        dealMenuRec.setNestedScrollingEnabled(false);
        return view;
    }
}