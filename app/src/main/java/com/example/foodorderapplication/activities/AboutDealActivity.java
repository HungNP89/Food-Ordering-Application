package com.example.foodorderapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.adapters.DealMenuAdapter;
import com.example.foodorderapplication.models.DealMenuModel;

import java.util.ArrayList;
import java.util.List;

public class AboutDealActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DealMenuModel> dealMenuModelsList;
    DealMenuAdapter dealMenuAdapter;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_deal);

        String type = getIntent().getStringExtra("type");

        recyclerView = findViewById(R.id.deal_items);
        imageView = findViewById(R.id.deal_items_image);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dealMenuModelsList = new ArrayList<>();
        dealMenuAdapter = new DealMenuAdapter(dealMenuModelsList);
        recyclerView.setAdapter(dealMenuAdapter);

        if (type != null && type.equalsIgnoreCase("breakfast")) {
            dealMenuModelsList.add(new DealMenuModel(R.drawable.pho,"Pho","description","5"));
            dealMenuModelsList.add(new DealMenuModel(R.drawable.pho,"Pho","description","5"));
            dealMenuModelsList.add(new DealMenuModel(R.drawable.pho,"Pho","description","5"));
            dealMenuAdapter.notifyDataSetChanged();
        }

        if (type != null && type.equalsIgnoreCase("lunch")) {
            imageView.setImageResource(R.drawable.lunch);
            dealMenuModelsList.add(new DealMenuModel(R.drawable.pho,"Pho","description","5"));
            dealMenuModelsList.add(new DealMenuModel(R.drawable.pho,"Pho","description","5"));
            dealMenuModelsList.add(new DealMenuModel(R.drawable.pho,"Pho","description","5"));
            dealMenuAdapter.notifyDataSetChanged();
        }

        if (type != null && type.equalsIgnoreCase("dinner")) {
            imageView.setImageResource(R.drawable.dinner);
            dealMenuModelsList.add(new DealMenuModel(R.drawable.pho,"Pho","description","5"));
            dealMenuModelsList.add(new DealMenuModel(R.drawable.pho,"Pho","description","5"));
            dealMenuModelsList.add(new DealMenuModel(R.drawable.pho,"Pho","description","5"));
            dealMenuAdapter.notifyDataSetChanged();
        }
    }
}