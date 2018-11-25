package com.example.lenovo.eats.ClassModel;

/**
 * Created by hamza on 24-Nov-18.
 */

public class Ingredient {
    int  available_qty;
    int reserved_qty;
    String name;
    Float ppp;

    public Ingredient() {
    }

    String qty_unit;

    public int getAvailable_qty() {
        return available_qty;
    }

    public Ingredient(int available_qty, int reserved_qty, String name, Float ppp, String qty_unit) {
        this.available_qty = available_qty;
        this.reserved_qty = reserved_qty;
        this.name = name;
        this.ppp = ppp;
        this.qty_unit = qty_unit;
    }

    public void setAvailable_qty(int available_qty) {
        this.available_qty = available_qty;
    }

    public int getReserved_qty() {
        return reserved_qty;
    }

    public void setReserved_qty(int reserved_qty) {
        this.reserved_qty = reserved_qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPpp() {
        return ppp;
    }

    public void setPpp(Float ppp) {
        this.ppp = ppp;
    }

    public String getQty_unit() {
        return qty_unit;
    }

    public void setQty_unit(String qty_unit) {
        this.qty_unit = qty_unit;
    }
}
