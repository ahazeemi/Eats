package com.example.lenovo.eats.ClassModel;

public class TDBMiniOrder
{
    String mainOrderID;
    int price;

    public TDBMiniOrder()
    {
    }

    public TDBMiniOrder(String mainOrderID, int price)
    {
        this.mainOrderID = mainOrderID;
        this.price = price;
    }

    public String getMainOrderID()
    {
        return mainOrderID;
    }

    public void setMainOrderID(String mainOrderID)
    {
        this.mainOrderID = mainOrderID;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }
}
