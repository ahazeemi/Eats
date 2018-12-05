package com.example.lenovo.eats.ClassModel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;

public class Bill_Order implements Serializable {
    private HashMap<String, BIll_OrderItem> order_items;
    private String special_instruction, original_order_id;
    private int bill_status, number_of_items;
    private float grand_total, tax, sub_total;
    private long timestamp, time_of_completion;

    @Exclude
    private boolean merge;


    public Bill_Order() {
    }

    public Bill_Order(HashMap<String, BIll_OrderItem> order_items, String specialInstruction, String original_order_id, int bill_status, int number_of_items, float grand_total, float tax, float sub_total, long timestamp, long time_of_completion) {
        this.order_items = order_items;
        this.special_instruction = specialInstruction;
        this.original_order_id = original_order_id;
        this.bill_status = bill_status;
        this.number_of_items = number_of_items;
        this.grand_total = grand_total;
        this.tax = tax;
        this.sub_total = sub_total;
        this.timestamp = timestamp;
        this.time_of_completion = time_of_completion;
        this.merge = false;
    }

    @Exclude
    public void setChecked(boolean check)
    {
        merge = check;
    }
    @Exclude
    public boolean getChecked()
  {
       return merge;
    }

    public HashMap<String, BIll_OrderItem> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(HashMap<String, BIll_OrderItem> order_items) {
        this.order_items = order_items;
    }

    public String getSpecialInstruction() {
        return special_instruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.special_instruction = specialInstruction;
    }

    public String getOriginal_order_id() {
        return original_order_id;
    }

    public void setOriginal_order_id(String original_order_id) {
        this.original_order_id = original_order_id;
    }

    public int getBill_status() {
        return bill_status;
    }

    public void setBill_status(int bill_status) {
        this.bill_status = bill_status;
    }

   public int getNumber_of_items() {
        return number_of_items;
    }

    public void setNumber_of_items(int number_of_items) {
        this.number_of_items = number_of_items;
    }

    public float getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(float grand_total) {
        this.grand_total = grand_total;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getSub_total() {
        return sub_total;
    }

    public void setSub_total(float sub_total) {
        this.sub_total = sub_total;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTime_of_completion() {
        return time_of_completion;
    }

    public void setTime_of_completion(long time_of_completion) {
        this.time_of_completion = time_of_completion;
    }

    public boolean isMerge() {
        return merge;
    }

    public void setMerge(boolean merge) {
        this.merge = merge;
    }
}
