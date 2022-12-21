package com.example.foodorderapplication.models;

import java.io.Serializable;

public class ShowFromCatModel implements Serializable {

    String description;
    String name;
    Float rating;
    int price;
    String image;
    String foodType;

    public ShowFromCatModel() {

    }

    public ShowFromCatModel(String description, String name, Float rating, int price, String image, String foodType) {
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.image = image;
        this.foodType = foodType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

}

