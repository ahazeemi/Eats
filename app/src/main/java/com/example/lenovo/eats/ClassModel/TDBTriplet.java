package com.example.lenovo.eats.ClassModel;

public class TDBTriplet
{
    String first, second, third;

    public TDBTriplet()
    {
    }

    public TDBTriplet(String first, String second, String third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public String getFirst()
    {
        return first;
    }

    public void setFirst(String first)
    {
        this.first = first;
    }

    public String getSecond()
    {
        return second;
    }

    public void setSecond(String second)
    {
        this.second = second;
    }

    public String getThird()
    {
        return third;
    }

    public void setThird(String third)
    {
        this.third = third;
    }
}
