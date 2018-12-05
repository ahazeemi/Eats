package com.example.lenovo.eats.ClassModel;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class TDBDailyReport
{
    private String timestamp;
    private List<TDBItem> inventoryItems;
    private List<TDBOrder> orders; // order price has profit, tax added, add function to find
    private List<TDBEmployee> salaries;
    private List<TDBMiniOrder> miniOrders;
    private int tax;
    private int profit;
    private int cost;
    private int sales;

    public TDBDailyReport()
    {
        timestamp = "";
        inventoryItems = new ArrayList<>();
        orders = new ArrayList<>();
        salaries = new ArrayList<>();
        miniOrders = new ArrayList<>();
        tax = profit = cost = sales = 0;
    }

    public TDBDailyReport(String timestamp, List<TDBItem> inventoryItems, List<TDBOrder> orders, List<TDBEmployee> salaries, List<TDBMiniOrder> miniOrders, int tax, int profit, int cost, int sales)
    {
        this.timestamp = timestamp;
        this.inventoryItems = inventoryItems;
        this.orders = orders;
        this.salaries = salaries;
        this.miniOrders = miniOrders;
        this.tax = tax;
        this.profit = profit;
        this.cost = cost;
        this.sales = sales;
    }

    public TDBDailyReport(String timestamp, List<TDBItem> inventoryItems, List<TDBOrder> orders, List<TDBEmployee> salaries, List<TDBMiniOrder> miniOrders)
    {
        this.timestamp = timestamp;
        this.inventoryItems = inventoryItems;
        this.orders = orders;
        this.salaries = salaries;
        this.miniOrders = miniOrders;
    }

    public TDBDailyReport(String timestamp, List<TDBItem> inventoryItems, List<TDBOrder> orders, List<TDBMiniOrder> miniOrders, int tax, int profit, int cost, int sales)
    {
        this.timestamp = timestamp;
        this.inventoryItems = inventoryItems;
        this.orders = orders;
        this.miniOrders = miniOrders;
        this.tax = tax;
        this.profit = profit;
        this.cost = cost;
        this.sales = sales;
    }

    @Exclude
    public String getTimestamp()
    {
        return timestamp;
    }

    public List<TDBItem> getInventoryItems()
    {
        return inventoryItems;
    }

    public List<TDBOrder> getOrders()
    {
        return orders;
    }

    public List<TDBEmployee> getSalaries()
    {
        return salaries;
    }

    public List<TDBMiniOrder> getMiniOrders()
    {
        return miniOrders;
    }

    public int getTax()
    {
        return tax;
    }

    public void setTax(int tax)
    {
        this.tax = tax;
    }

    public int getProfit()
    {
        return profit;
    }

    public void setProfit(int profit)
    {
        this.profit = profit;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public int getSales()
    {
        return sales;
    }

    public void setSales(int sales)
    {
        this.sales = sales;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }
}
