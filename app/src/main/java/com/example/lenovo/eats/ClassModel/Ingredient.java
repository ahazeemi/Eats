package com.example.lenovo.eats.ClassModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hamza on 24-Nov-18.
 */

public class Ingredient implements Parcelable {
    int  available_qty;
    int reserved_qty;
    String name;
    Float ppp;

    public Ingredient() {
    }

    String qty_unit;

    public int getAvailable_qty() {
        return available_qty;
    }

    public Ingredient(int available_qty, int reserved_qty, String name, Float ppp, String qty_unit) {
        this.available_qty = available_qty;
        this.reserved_qty = reserved_qty;
        this.name = name;
        this.ppp = ppp;
        this.qty_unit = qty_unit;
    }

    public void setAvailable_qty(int available_qty) {
        this.available_qty = available_qty;
    }

    public int getReserved_qty() {
        return reserved_qty;
    }

    public void setReserved_qty(int reserved_qty) {
        this.reserved_qty = reserved_qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPpp() {
        return ppp;
    }

    public void setPpp(Float ppp) {
        this.ppp = ppp;
    }

    public String getQty_unit() {
        return qty_unit;
    }

    public void setQty_unit(String qty_unit) {
        this.qty_unit = qty_unit;
    }

    protected Ingredient(Parcel in) {
        available_qty = in.readInt();
        reserved_qty = in.readInt();
        name = in.readString();
        ppp = in.readByte() == 0x00 ? null : in.readFloat();
        qty_unit = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(available_qty);
        dest.writeInt(reserved_qty);
        dest.writeString(name);
        if (ppp == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(ppp);
        }
        dest.writeString(qty_unit);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}