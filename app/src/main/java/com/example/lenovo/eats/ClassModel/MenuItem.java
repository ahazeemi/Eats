package com.example.lenovo.eats.ClassModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hamza on 24-Nov-18.
 */

public class MenuItem {

    String name;
    HashMap<String,Integer> ingredients;
    String type;

    public MenuItem(String name, String type) {
        this.name = name;
        this.type = type;
        ingredients=new HashMap<>();
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
