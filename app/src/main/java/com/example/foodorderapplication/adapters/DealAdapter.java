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

import com.bumptech.glide.Glide;
import com.example.foodorderapplication.Add.GlideApp;
import com.example.foodorderapplication.R;
import com.example.foodorderapplication.activities.AboutDealActivity;
import com.example.foodorderapplication.models.DealModel;

import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    Context context;
    List<DealModel> list;

    public DealAdapter(Context context, List<DealModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DealAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.deals_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DealAdapter.ViewHolder holder, int position) {
        GlideApp.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.discount.setText(list.get(position).getDiscount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AboutDealActivity.class);
                intent.putExtra("discount", list.get(holder.getAbsoluteAdapterPosition()).getType());
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
        TextView name, discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView2);
            name = itemView.findViewById(R.id.text3);
            discount = itemView.findViewById(R.id.discount);
        }
    }
}
