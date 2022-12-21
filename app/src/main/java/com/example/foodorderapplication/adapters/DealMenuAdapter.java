package com.example.foodorderapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.activities.AboutDealActivity;
import com.example.foodorderapplication.models.DealMenuModel;

import java.util.List;

public class DealMenuAdapter extends RecyclerView.Adapter<DealMenuAdapter.ViewHolder> {
    Context context;
    List<DealMenuModel> list;

    public DealMenuAdapter(List<DealMenuModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DealMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DealMenuAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DealMenuAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
        holder.description.setText(list.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, description, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.deal_item1);
            name = itemView.findViewById(R.id.deal_item_name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.item_price);
        }
    }
}
