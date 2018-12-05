package com.example.lenovo.eats.ClassModel;

import java.io.Serializable;

public class BIll_OrderItem implements Serializable {
    private int quantity;
    private float calculated_price;

    public BIll_OrderItem(){}

    public BIll_OrderItem(int quantity, float price) {
        this.quantity = quantity;
        this.calculated_price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return calculated_price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.calculated_price = price;
    }
}
