package com.example.foodorderapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapplication.Add.GlideApp;
import com.example.foodorderapplication.R;
import com.example.foodorderapplication.activities.DetailedActivity;
import com.example.foodorderapplication.models.DealMenuModel;

import java.util.List;

public class DealMenuAdapter extends RecyclerView.Adapter<DealMenuAdapter.ViewHolder> {
    Context context;
    List<DealMenuModel> list;

    public DealMenuAdapter(Context context, List<DealMenuModel> list) {
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
        GlideApp.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));
        holder.ratingBar.setRating(list.get(position).getRating());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed", list.get(holder.getAbsoluteAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.deal_item1);
            name = itemView.findViewById(R.id.deal_item_name);
            price = itemView.findViewById(R.id.item_price);
            ratingBar = itemView.findViewById(R.id.rating_food4);
        }
    }
}
