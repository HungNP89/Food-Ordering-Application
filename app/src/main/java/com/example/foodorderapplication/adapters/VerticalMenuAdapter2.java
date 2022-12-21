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
import com.example.foodorderapplication.models.VerticalMenuModel2;

import java.util.ArrayList;

public class VerticalMenuAdapter2 extends RecyclerView.Adapter<VerticalMenuAdapter2.ViewHolder> {
    Context context;
    ArrayList<VerticalMenuModel2> list;
    LayoutInflater layoutInflater;

    public void filterList(ArrayList<VerticalMenuModel2> filterlist) {
        list = filterlist;
        notifyDataSetChanged();
    }

    public VerticalMenuAdapter2(Context context, ArrayList<VerticalMenuModel2> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VerticalMenuAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VerticalMenuAdapter2.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_menu2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalMenuAdapter2.ViewHolder holder, int position) {
        GlideApp.with(context).load(list.get(position).getImage()).into(holder.imageView2);
        holder.productName2.setText(list.get(position).getName());
        holder.price2.setText(String.valueOf(list.get(position).getPrice()));
        holder.rating2.setRating(list.get(position).getRating());

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
        ImageView imageView2;
        TextView productName2, price2;
        RatingBar rating2;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.vertical_item2);
            productName2 = itemView.findViewById(R.id.product_name2);
            price2 = itemView.findViewById(R.id.price2);
            rating2 = itemView.findViewById(R.id.rating_food2);
        }
    }
}
