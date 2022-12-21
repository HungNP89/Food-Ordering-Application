package com.example.foodorderapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.models.UserProfileModel;

import java.util.List;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.ViewHolder> {
    Context context;
    List<UserProfileModel> list;

    public UserProfileAdapter(Context context, List<UserProfileModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_user_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileAdapter.ViewHolder holder, int position) {
        holder.showEmail.setText(list.get(position).getEmail());
        holder.showName.setText(list.get(position).getUsername());
        holder.showAddress.setText(list.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView showEmail,showName,showAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showEmail = itemView.findViewById(R.id.show_email);
            showName = itemView.findViewById(R.id.show_name);
            showAddress = itemView.findViewById(R.id.show_address);
        }
    }
}
