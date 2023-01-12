package com.example.foodorderapplication.models;

import java.io.Serializable;

public class DealMenuModel implements Serializable {

    String image;
    String name;
    String description;
    int price;
    String discount;
    Float rating;

    public DealMenuModel(String image, String name, String description, int price, Float rating, String discount) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.discount = discount;
    }

    public DealMenuModel() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

}
