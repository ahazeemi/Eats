package com.example.lenovo.eats.ClassModel;

import java.util.HashMap;

/**
 * Created by hamza on 24-Nov-18.
 */

public class Order {
    HashMap<String,Ingredient> menuItems;
    String specialInstruction;

    public Order(String specialInstruction) {
        this.specialInstruction = specialInstruction;
        menuItems=new HashMap<>();
    }

    public Order() {
    }

    public HashMap<String, Ingredient> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(HashMap<String, Ingredient> menuItems) {
        this.menuItems = menuItems;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }
}
