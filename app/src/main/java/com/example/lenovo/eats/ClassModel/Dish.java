package com.example.lenovo.eats.ClassModel;

import com.google.firebase.database.Exclude;

/**
 * Created by lenovo on 11/23/2018.
 */

public class Dish {

    @Exclude
    String dishId;

    @Exclude
    String quantityOrdered;

    String name;


    public String getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(String quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
