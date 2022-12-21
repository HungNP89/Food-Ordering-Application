package com.example.foodorderapplication.models;

public class UserProfileModel {

    String Email;
    String Username;
    String Address;

    public UserProfileModel() {

    }
    public UserProfileModel(String Email, String Username, String Address) {
        this.Email = Email;
        this.Username = Username;
        this.Address = Address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

}
