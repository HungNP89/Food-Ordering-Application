package com.example.foodorderapplication.models;

import java.io.Serializable;

public class VerticalMenuModel2 implements Serializable {

    String image;
    String name;
    int price;
    Float rating;
    String description;
    String type;

    public VerticalMenuModel2() {

    }

    public VerticalMenuModel2(String image, String name, int price, Float rating, String description, String type) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.type = type;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
