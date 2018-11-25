package com.example.lenovo.eats.ClassModel;

import java.util.HashMap;

/**
 * Created by hamza on 25-Nov-18.
 */

public class MenuItem {
    String name;
    HashMap<String,Integer> ingredients;
    String type;
    Float sale_price;
    String preparation_time;

    public Float getSale_price() {
        return sale_price;
    }

    public void setSale_price(Float sale_price) {
        this.sale_price = sale_price;
    }

    public String getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(String preparation_time) {
        this.preparation_time = preparation_time;
    }

    public MenuItem(String name, HashMap<String, Integer> ingredients, String type, Float sale_price, String preparation_time) {
        this.name = name;
        this.ingredients = ingredients;
        this.type = type;
        this.sale_price = sale_price;
        this.preparation_time = preparation_time;
    }

    public MenuItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
