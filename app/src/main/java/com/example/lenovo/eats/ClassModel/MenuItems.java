package com.example.lenovo.eats.ClassModel;

import java.util.ArrayList;

public class MenuItems {


    private String mName;
    private String mPrice;
    private ArrayList<Integer> ingredients_id;
    private ArrayList<Integer> ingredients_quantity;
    private String preparationTime;
    private int mImageDrawable;
    private String item_id;


    public ArrayList<Integer> getIngredients_id() {
        return ingredients_id;
    }

    public void setIngredients_id(ArrayList<Integer> ingredients_id) {
        this.ingredients_id = ingredients_id;
    }

    public ArrayList<Integer> getIngredients_quantity() {
        return ingredients_quantity;
    }

    public void setIngredients_quantity(ArrayList<Integer> ingredients_quantity) {
        this.ingredients_quantity = ingredients_quantity;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public MenuItems(String id, int mImageDrawable, String mName, String mPrice, String mPreparationTime, ArrayList ing_id, ArrayList ing_quantity)
    {
        this.item_id = id;
        this.mImageDrawable = mImageDrawable;
        this.mName = mName;
        this.mPrice = mPrice;
        this.preparationTime = mPreparationTime;
        this.ingredients_id = ing_id;
        this.ingredients_quantity = ing_quantity;
    }

    public int getmImageDrawable() {
        return mImageDrawable;
    }

    public String getmName() {
        return mName;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getmPreparationTime() {
        return preparationTime;
    }

    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setItem_id(String mName) {
        this.item_id = mName;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public void setmPreparationTime(String mPreparationTime) {
        this.preparationTime = mPreparationTime;
    }
}
