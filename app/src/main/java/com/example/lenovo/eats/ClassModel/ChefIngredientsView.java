package com.example.lenovo.eats.ClassModel;

/**
 * Created by hamza on 24-Nov-18.
 */

public class ChefIngredientsView {

    String name;
    String ingredientId;

    public ChefIngredientsView(String name, String ingredientId) {
        this.name = name;
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }
}
