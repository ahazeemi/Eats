package com.example.lenovo.eats.ClassModel;

public class TrioOrderItems {
    private int mImageDrawable;
    private String oName;
    private String quantity;
    private String special;

    public TrioOrderItems(int mImageDrawable, String oName, String quantity, String special) {
        this.mImageDrawable = mImageDrawable;
        this.oName = oName;
        this.quantity = quantity;
        this.special = special;
    }

    public int getmImageDrawable() {
        return mImageDrawable;
    }

    public String getoName() {
        return oName;
    }

    public String getquantity() {
        return quantity;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public void setquantity(String quantity) {
        this.quantity = quantity;
    }

}
