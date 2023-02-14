package com.example.foodorderapplication.models;

public class OrderHistoryModel {

    String currentTime;
    String productDate;
    String productName;
    int productPrice;
    String totalQuantity;
    int totalPrice;
    String Status;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    String documentId;

    public OrderHistoryModel() {

    }

    public OrderHistoryModel(String currentTime, String productDate, String productName, int productPrice, String totalQuantity, int totalPrice, String Status ) {
        this.Status = Status;
        this.currentTime = currentTime;
        this.productDate = productDate;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;

    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = Status;
    }


    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}
