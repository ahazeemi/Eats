package com.example.lenovo.eats.ClassModel;

import com.google.firebase.database.Exclude;

/**
 * Created by lenovo on 11/23/2018.
 */


public class MenuItemView {

    @Exclude
    String menuItemId;

    @Exclude
    Integer quantityOrdered;

    String name;

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
