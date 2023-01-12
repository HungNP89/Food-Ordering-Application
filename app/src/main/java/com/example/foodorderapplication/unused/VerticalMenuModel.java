package com.example.foodorderapplication.unused;

public class VerticalMenuModel {
    int image;
    String productName;
    String price;

    float rating;

    public VerticalMenuModel(int image, String productName, String price, float rating) {
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.rating = rating;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
