package com.example.lenovo.eats.ClassModel;

/**
 * Created by Suleman on 12/3/2017.
 */

public class Movie {
    private String title, genre, year;
    private  String key;
    private int capacity;
    private String statusBill;
    private String bill;
    private int topTable;

    private  String orderKey;
    public Movie() {
    }

    public Movie(String title, String genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public int getCapacity()
    {return capacity;}

    public void setCapacity(int c)
    {
        capacity = c;
    }


    public String getKey()
    {
        return key;
    }

    public void setKey(String s)
    {
        key = s;
    }


    public String getOrderKey()
    {
        return orderKey;
    }

    public void setOrderKey(String s)
    {
        orderKey = s;
    }


    public String getStatus()
    {
        return statusBill;
    }

    public void setStatus(String s)
    {
        statusBill= s;
    }
    public String getBill()
    {
        return bill;
    }

    public void setBill(String s)
    {
        bill = s;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}