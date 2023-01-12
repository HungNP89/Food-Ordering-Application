package com.example.foodorderapplication.unused;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapplication.R;

import java.util.ArrayList;

public class VerticalMenuAdapter extends RecyclerView.Adapter<VerticalMenuAdapter.ViewHolder> {

    Context context;
    ArrayList<VerticalMenuModel> list;

    public void setFilteredList(ArrayList<VerticalMenuModel> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    public VerticalMenuAdapter(Context context, ArrayList<VerticalMenuModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VerticalMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VerticalMenuAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalMenuAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.productName.setText(list.get(position).getProductName());
        holder.price.setText(list.get(position).getPrice());
        holder.rating.setRating(list.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView productName, price;
        RatingBar rating;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.vertical_item);
            productName = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            rating = itemView.findViewById(R.id.rating_food);
        }
    }
}
