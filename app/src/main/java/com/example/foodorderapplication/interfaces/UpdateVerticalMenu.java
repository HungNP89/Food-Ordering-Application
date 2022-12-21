package com.example.foodorderapplication.interfaces;

import com.example.foodorderapplication.adapters.VerticalMenuAdapter;
import com.example.foodorderapplication.models.VerticalMenuModel;

import java.util.ArrayList;

public interface UpdateVerticalMenu {
    public void callBack(int position , ArrayList<VerticalMenuModel> list);
}
