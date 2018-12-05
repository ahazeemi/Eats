package com.example.lenovo.eats.ClassModel;

import java.io.Serializable;
import java.util.ArrayList;

public class Bill_MyOrder implements Serializable {
    private ArrayList<Bill_OrderDish> dishes;
    private float subTotal, tax, grandTotal;
    private int billStatus, number_of_items;
    private String orderId;
    private boolean checked;
    private long timestamp, time_of_completion;
    private String specialInstruction, original_order_id;
    private boolean inserted;

    public Bill_MyOrder() {}

    public Bill_MyOrder(ArrayList<Bill_OrderDish> dishes, float subTotal, float tax, float grandTotal, int billStatus, int number_of_items, String orderId, long timestamp, long time_of_completion, String specialInstruction, String original_order_id) {
        this.dishes = dishes;
        this.subTotal = subTotal;
        this.tax = tax;
        this.grandTotal = grandTotal;
        this.billStatus = billStatus;
        this.number_of_items = number_of_items;
        this.orderId = orderId;
        this.checked = false;
        this.timestamp = timestamp;
        this.time_of_completion = time_of_completion;
        this.specialInstruction = specialInstruction;
        this.original_order_id = original_order_id;
        this.inserted = false;
    }

//    public MyOrder(ArrayList<OrderDish> dishes, float subTotal, float tax, float grandTotal, int billStatus, String id) {
//        this.dishes = dishes;
//        this.subTotal = subTotal;
//        this.tax = tax;
//        this.grandTotal = grandTotal;
//        this.billStatus = billStatus;
//        this.orderId=id;
//    }

    public void calcuateBill(){
        subTotal = 0;
        tax = 0;
        grandTotal = 0;
        for (Bill_OrderDish dish:
             dishes) {
            subTotal += dish.getPrice();
        }

        tax = 0.17f * subTotal;
        grandTotal = tax + subTotal;
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }


    public int getNumber_of_items() {
        return number_of_items;
    }

    public void setNumber_of_items(int number_of_items) {
        this.number_of_items = number_of_items;
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

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public String getOriginal_order_id() {
        return original_order_id;
    }

    public void setOriginal_order_id(String original_order_id) {
        this.original_order_id = original_order_id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<Bill_OrderDish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Bill_OrderDish> dishes) {
        this.dishes = dishes;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
