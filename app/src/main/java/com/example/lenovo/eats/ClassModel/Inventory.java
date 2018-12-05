package com.example.lenovo.eats.ClassModel;

public class Inventory
{
    private String id;
    private int    available_qty;
    private String name;
    private float  ppp;
    private String qty_unit;
    private int    reserved_qty;

    public Inventory() {
    }

    public Inventory(String id, int available_qty, String name, float ppp, String qty_unit, int reserved_qty)
    {
        this.id = id;
        this.available_qty = available_qty;
        this.name = name;
        this.ppp = ppp;
        this.qty_unit = qty_unit;
        this.reserved_qty = reserved_qty;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getAvailable_qty() {
        return available_qty;
    }

    public void setAvailable_qty(int available_qty) {
        this.available_qty = available_qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPpp() {
        return ppp;
    }

    public void setPpp(float ppp) {
        this.ppp = ppp;
    }

    public String getQty_unit() {
        return qty_unit;
    }

    public void setQty_unit(String qty_unit) {
        this.qty_unit = qty_unit;
    }

    public int getReserved_qty() {
        return reserved_qty;
    }

    public void setReserved_qty(int reserved_qty) {
        this.reserved_qty = reserved_qty;
    }
}
