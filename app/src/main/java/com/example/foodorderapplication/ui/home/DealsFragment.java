package com.example.foodorderapplication.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.adapters.DealAdapter;
import com.example.foodorderapplication.models.DealModel;

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
        dealMenuRec = view.findViewById(R.id.deal_meals);
        dealModelList = new ArrayList<>();
        dealModelList.add(new DealModel(R.drawable.breakfast,"Breakfast","20% OFF","breakfast"));
        dealModelList.add(new DealModel(R.drawable.lunch,"Lunch","10% OFF","lunch"));
        dealModelList.add(new DealModel(R.drawable.dinner,"Dinner","15% OFF","dinner"));

        dealAdapter = new DealAdapter(getContext(),dealModelList);
        dealMenuRec.setAdapter(dealAdapter);
        dealMenuRec.setLayoutManager(new LinearLayoutManager(getContext()));
        dealAdapter.notifyDataSetChanged();
        dealMenuRec.setHasFixedSize(true);
        dealMenuRec.setNestedScrollingEnabled(false);
        return view;
    }
}