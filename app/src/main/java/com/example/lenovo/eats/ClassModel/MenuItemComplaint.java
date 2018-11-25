package com.example.lenovo.eats.ClassModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

/**
 * Created by lenovo on 11/24/2018.
 */

public class MenuItemComplaint implements Parcelable {

    @Exclude
    HashMap<String,Boolean> causes;

    @Exclude
    HashMap<String,Integer> ingredients_count;

    @Exclude
    Float sale_price;

    String cause;
    Integer quantity_reordered;
    String special_instruction;

    public HashMap<String, Boolean> getCauses() {
        return causes;
    }

    public MenuItemComplaint(HashMap<String, Boolean> causes, HashMap<String, Integer> ingredients_count, Float sale_price, String cause, Integer quantity_reordered, String special_instruction) {
        this.causes = causes;
        this.ingredients_count = ingredients_count;
        this.sale_price = sale_price;
        this.cause = cause;
        this.quantity_reordered = quantity_reordered;
        this.special_instruction = special_instruction;
    }

    public void setCauses(HashMap<String, Boolean> causes) {
        this.causes = causes;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Integer getQuantity_reordered() {
        return quantity_reordered;
    }

    public void setQuantity_reordered(Integer quantity_reordered) {
        this.quantity_reordered = quantity_reordered;
    }

    public String getSpecial_instruction() {
        return special_instruction;
    }

    public void setSpecial_instruction(String special_instruction) {
        this.special_instruction = special_instruction;
    }

    public MenuItemComplaint() {
    }

    public HashMap<String, Integer> getIngredients_count() {

        return ingredients_count;
    }

    public void setIngredients_count(HashMap<String, Integer> ingredients_count) {
        this.ingredients_count = ingredients_count;
    }

    public Float getSale_price() {
        return sale_price;
    }

    public void setSale_price(Float sale_price) {
        this.sale_price = sale_price;
    }

    protected MenuItemComplaint(Parcel in) {
        causes = (HashMap) in.readValue(HashMap.class.getClassLoader());
        ingredients_count = (HashMap) in.readValue(HashMap.class.getClassLoader());
        sale_price = in.readByte() == 0x00 ? null : in.readFloat();
        cause = in.readString();
        quantity_reordered = in.readByte() == 0x00 ? null : in.readInt();
        special_instruction = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(causes);
        dest.writeValue(ingredients_count);
        if (sale_price == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(sale_price);
        }
        dest.writeString(cause);
        if (quantity_reordered == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(quantity_reordered);
        }
        dest.writeString(special_instruction);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MenuItemComplaint> CREATOR = new Parcelable.Creator<MenuItemComplaint>() {
        @Override
        public MenuItemComplaint createFromParcel(Parcel in) {
            return new MenuItemComplaint(in);
        }

        @Override
        public MenuItemComplaint[] newArray(int size) {
            return new MenuItemComplaint[size];
        }
    };
}