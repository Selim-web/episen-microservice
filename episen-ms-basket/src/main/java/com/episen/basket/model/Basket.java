package com.episen.basket.model;

import java.util.List;

public class Basket {

    private String username;

    private Double totalAmount;

    private String status;

    private List<Item> itemList;

    public Basket(String username, Double totalAmount, String status, List<Item> itemList) {
        this.username = username;
        this.totalAmount = totalAmount;
        this.status = status;
        this.itemList = itemList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
