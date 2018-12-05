package com.example.lenovo.eats.ClassModel;

/**
 * Created by Sheri on 05-Dec-18.
 */


public class OrderMenuItem {
    Float calculated_price;
    Integer quantity;

    public OrderMenuItem() {
    }

    public OrderMenuItem(Float calculated_price, Integer quantity) {
        this.calculated_price = calculated_price;
        this.quantity = quantity;
    }

    public Float getCalculated_price() {
        return calculated_price;
    }

    public void setCalculated_price(Float calculated_price) {
        this.calculated_price = calculated_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
