package com.example.lenovo.eats.ClassModel;

import java.io.Serializable;

public class Bill_OrderDish implements Serializable {
    private String id, name;
    private int quantity;
    private float price;

    public Bill_OrderDish(){}

    public Bill_OrderDish(String id, String name, int quantity, float price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
