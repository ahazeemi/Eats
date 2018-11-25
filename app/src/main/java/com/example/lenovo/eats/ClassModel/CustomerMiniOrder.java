package com.example.lenovo.eats.ClassModel;

/**
 * Created by lenovo on 11/25/2018.
 */

public class CustomerMiniOrder {
    String main_order_id;
    Float price;
    Long timestamp;

    public CustomerMiniOrder(String main_order_id, Float price, Long timestamp) {
        this.main_order_id = main_order_id;
        this.price = price;
        this.timestamp = timestamp;
    }

    public CustomerMiniOrder() {

    }

    public String getMain_order_id() {

        return main_order_id;
    }

    public void setMain_order_id(String main_order_id) {
        this.main_order_id = main_order_id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
