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
import com.example.foodorderapplication.activities.ShowFromCategory;
import com.example.foodorderapplication.models.ShowFromCatModel;

import java.util.ArrayList;
import java.util.List;

public class ShowFromCatAdapter extends RecyclerView.Adapter<ShowFromCatAdapter.ViewHolder> {

    Context context;
    List<ShowFromCatModel> list;

    public ShowFromCatAdapter(Context context, List<ShowFromCatModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_from_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowFromCatAdapter.ViewHolder holder, int position) {
        GlideApp.with(context).load(list.get(position).getImage()).into(holder.imageView3);
        holder.productName3.setText(list.get(position).getName());
        holder.price3.setText(String.valueOf(list.get(position).getPrice()));
        holder.rating3.setRating(list.get(position).getRating());

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
        ImageView imageView3;
        TextView productName3, price3;
        RatingBar rating3;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView3 = itemView.findViewById(R.id.vertical_item3);
            productName3 = itemView.findViewById(R.id.product_name3);
            price3 = itemView.findViewById(R.id.price3);
            rating3 = itemView.findViewById(R.id.rating_food3);
        }
    }
}
