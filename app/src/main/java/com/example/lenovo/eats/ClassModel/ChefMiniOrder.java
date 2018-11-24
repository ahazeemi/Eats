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

    public ChefMiniOrder(String mainOrderId, String menuItemId, long timestamp, HashMap<String, Integer> ingredientOrdered) {
        this.mainOrderId = mainOrderId;
        this.menuItemId = menuItemId;
        this.timestamp = timestamp;
        this.ingredientOrdered = ingredientOrdered;
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
}
