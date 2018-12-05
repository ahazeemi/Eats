package com.example.lenovo.eats.Utility;


import java.util.Arrays;

/**
 * Created by Sheri on 03-Dec-18.
 */

public class itemFactory extends Object {

    public String list[];
    public String org;
    public   itemFactory(String src)
    {
        list=src.split(",");
org=src;
    }
    public boolean isValid()
    {
        return (list.length==4);
    }
    public String getID()
    {
        return list[1];
    }
    public int getETime()
    {
        return Integer.parseInt(list[2]);
    }
    public String getOrderID()
    {
        return list[3];

    }

    @Override
    public String toString() {
        return org;

    }
}
