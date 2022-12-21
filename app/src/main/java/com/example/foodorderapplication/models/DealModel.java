package com.example.foodorderapplication.models;

public class DealModel {

    int image;
    String name;
    String discount;
    String type;

    public DealModel(int image, String name, String discount, String type) {
        this.image = image;
        this.name = name;
        this.discount = discount;
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
