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

    String cause;
    Integer quantityReordered;
    String specialInstruction;

    public HashMap<String, Boolean> getCauses() {
        return causes;
    }

    public MenuItemComplaint(HashMap<String, Boolean> causes, String cause, Integer quantityReordered, String specialInstruction) {
        this.causes = causes;
        this.cause = cause;
        this.quantityReordered = quantityReordered;
        this.specialInstruction = specialInstruction;
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

    public Integer getQuantityReordered() {
        return quantityReordered;
    }

    public void setQuantityReordered(Integer quantityReordered) {
        this.quantityReordered = quantityReordered;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    protected MenuItemComplaint(Parcel in) {
        causes = (HashMap) in.readValue(HashMap.class.getClassLoader());
        cause = in.readString();
        quantityReordered = in.readByte() == 0x00 ? null : in.readInt();
        specialInstruction = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(causes);
        dest.writeString(cause);
        if (quantityReordered == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(quantityReordered);
        }
        dest.writeString(specialInstruction);
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
