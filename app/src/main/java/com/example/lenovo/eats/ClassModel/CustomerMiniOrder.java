package com.example.lenovo.eats.ClassModel;

import java.util.HashMap;

/**
 * Created by lenovo on 11/25/2018.
 */

public class CustomerMiniOrder {
    String main_order_id;
    Float price;
    Long timestamp;
    HashMap<String,MenuItemComplaint> order_items;

    public HashMap<String, MenuItemComplaint> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(HashMap<String, MenuItemComplaint> order_items) {
        this.order_items = order_items;
    }

    public CustomerMiniOrder() {

    }

    public CustomerMiniOrder(String main_order_id, Float price, Long timestamp, HashMap<String, MenuItemComplaint> order_items) {
        this.main_order_id = main_order_id;
        this.price = price;
        this.timestamp = timestamp;
        this.order_items = order_items;
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
