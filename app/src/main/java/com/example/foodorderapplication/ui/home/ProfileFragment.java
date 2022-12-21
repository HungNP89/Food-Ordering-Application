package com.example.foodorderapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.models.UserProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth fAuth;

    TextView showEmail,showName,showAddress;

    public ProfileFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        showEmail = view.findViewById(R.id.show_email);
        showName = view.findViewById(R.id.show_name);
        showAddress = view.findViewById(R.id.show_address);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users").child(fAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfileModel users = snapshot.getValue(UserProfileModel.class);
                showEmail.setText(users.getEmail());
                showName.setText(users.getUsername());
                showAddress.setText(users.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}