package com.example.lenovo.eats.ClassModel;

/**
 * Created by hamza on 24-Nov-18.
 */

public class Ingredient {
    int  availableQuantity;
    int reserveQuantity;
    String name;
    String unit;

    public Ingredient(int availableQuantity, int reserveQuantity, String name, String unit) {
        this.availableQuantity = availableQuantity;
        this.reserveQuantity = reserveQuantity;
        this.name = name;
        this.unit = unit;
    }

    public Ingredient() {
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getReserveQuantity() {
        return reserveQuantity;
    }

    public void setReserveQuantity(int reserveQuantity) {
        this.reserveQuantity = reserveQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
