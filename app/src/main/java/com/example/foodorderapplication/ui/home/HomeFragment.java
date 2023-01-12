package com.example.foodorderapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapplication.R;
import com.example.foodorderapplication.adapters.HorizontalMenuAdapter;
import com.example.foodorderapplication.adapters.VerticalMenuAdapter2;
import com.example.foodorderapplication.models.HorizontalMenuModel;
import com.example.foodorderapplication.models.VerticalMenuModel2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment  {

    SearchView searchView;

    /////////////HorizontalView
    RecyclerView horizontalMenuRec;
    ArrayList<HorizontalMenuModel> horizontalMenuModelList;
    HorizontalMenuAdapter horizontalMenuAdapter;

    ///////////VerticalView2;
    RecyclerView verticalMenuRec2;
    ArrayList<VerticalMenuModel2> verticalMenuModel2s;
    VerticalMenuAdapter2 verticalMenuAdapter2;

    public HomeFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        horizontalMenuRec = view.findViewById(R.id.horizontal_menu);
        verticalMenuRec2 = view.findViewById(R.id.vertical_menu2);
        searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        //database connect
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ///////////Horizontal Recycle View
        horizontalMenuModelList = new ArrayList<>();
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HorizontalMenuModel horizontalMenuModel = document.toObject(HorizontalMenuModel.class);
                                horizontalMenuModelList.add(horizontalMenuModel);
                                horizontalMenuAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("", "Error getting documents.", task.getException());
                        }
                    }
                });
        horizontalMenuAdapter = new HorizontalMenuAdapter( horizontalMenuModelList, getContext());
        horizontalMenuRec.setAdapter(horizontalMenuAdapter);
        horizontalMenuRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        horizontalMenuRec.setHasFixedSize(true);
        horizontalMenuRec.setNestedScrollingEnabled(false);

        //////////Vertical Recycle View 2
        verticalMenuModel2s = new ArrayList<>();
        db.collection("All Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                VerticalMenuModel2 verticalMenuModel2 = document.toObject(VerticalMenuModel2.class);
                                verticalMenuModel2s.add(verticalMenuModel2);
                                verticalMenuAdapter2.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("", "Error getting documents.", task.getException());
                        }
                    }
                });
        Log.w("", "Ok !");
        verticalMenuAdapter2 = new VerticalMenuAdapter2(getActivity(), verticalMenuModel2s);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        verticalMenuRec2.setAdapter(verticalMenuAdapter2);
        verticalMenuRec2.setLayoutManager(gridLayoutManager);

        return view;
    }

    private void filter(String text) {
        ArrayList<VerticalMenuModel2> filteredList = new ArrayList<VerticalMenuModel2>();
        for (VerticalMenuModel2 item : verticalMenuModel2s) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Log.w("","Error");
        } else {
            verticalMenuAdapter2.filterList(filteredList);
        }
    }

}