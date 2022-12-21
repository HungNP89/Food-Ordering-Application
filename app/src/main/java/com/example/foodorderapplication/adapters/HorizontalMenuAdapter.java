package com.example.foodorderapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapplication.Add.GlideApp;
import com.example.foodorderapplication.R;
import com.example.foodorderapplication.activities.ShowFromCategory;
import com.example.foodorderapplication.interfaces.UpdateVerticalMenu;
import com.example.foodorderapplication.models.HorizontalMenuModel;
import com.example.foodorderapplication.models.VerticalMenuModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HorizontalMenuAdapter extends RecyclerView.Adapter<HorizontalMenuAdapter.ViewHolder> {

    UpdateVerticalMenu updateVerticalMenu;
    Activity activity;
    ArrayList<HorizontalMenuModel> list;
    Context context;


    boolean check = true;
    int row_index = -1;

    public HorizontalMenuAdapter(ArrayList<HorizontalMenuModel> list, Context context) {
        this.updateVerticalMenu = updateVerticalMenu;
        this.activity = activity;
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowFromCategory.class);
                intent.putExtra("foodType", list.get(holder.getAbsoluteAdapterPosition()).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item1);
            name = itemView.findViewById(R.id.item1_text);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
