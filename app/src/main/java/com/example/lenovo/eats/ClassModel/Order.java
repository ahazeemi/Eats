package com.example.lenovo.eats.ClassModel;

import java.util.HashMap;

/**
 * Created by hamza on 24-Nov-18.
 */

public class Order {
    HashMap<String,Integer> order_items;
    String special_instruction;
    Long time_of_completion;
    Long timestamp;

    public Order(HashMap<String, Integer> order_items, String special_instruction, Long time_of_completion, Long timestamp) {
        this.order_items = order_items;
        this.special_instruction = special_instruction;
        this.time_of_completion = time_of_completion;
        this.timestamp = timestamp;
    }

    public HashMap<String, Integer> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(HashMap<String, Integer> order_items) {
        this.order_items = order_items;
    }

    public String getSpecial_instruction() {
        return special_instruction;
    }

    public void setSpecial_instruction(String special_instruction) {
        this.special_instruction = special_instruction;
    }

    public Long getTime_of_completion() {
        return time_of_completion;
    }

    public void setTime_of_completion(Long time_of_completion) {
        this.time_of_completion = time_of_completion;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Order() {
    }
}
