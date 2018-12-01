package com.example.lenovo.eats.ClassModel;

import java.util.HashMap;

/**
 * Created by hamza on 24-Nov-18.
 */

public class ChefMiniOrder {
    String mainOrderId;
    String menuItemId;
    long timestamp;
    HashMap<String,Integer> ingredientOrdered;
    float price;
    int time;

    public ChefMiniOrder(String mainOrderId, String menuItemId, long timestamp, HashMap<String, Integer> ingredientOrdered, float price, int time) {
        this.mainOrderId = mainOrderId;
        this.menuItemId = menuItemId;
        this.timestamp = timestamp;
        this.ingredientOrdered = ingredientOrdered;
        this.price = price;
        this.time = time;
    }

    public ChefMiniOrder() {
    }

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public HashMap<String, Integer> getIngredientOrdered() {
        return ingredientOrdered;
    }

    public void setIngredientOrdered(HashMap<String, Integer> ingredientOrdered) {
        this.ingredientOrdered = ingredientOrdered;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
