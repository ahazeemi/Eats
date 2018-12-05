package com.example.lenovo.eats.ClassModel;
import java.util.List;
/**
 * Created by Sheri on 03-Dec-18.
 */

public class Dine_In_orderProxy {
    public String orderID;
    public List<String> items;
    public Dine_In_orderProxy(){};
    public Dine_In_orderProxy(String id, List<String> itemsSr)
    {
        items=itemsSr;
        orderID=id;
    }

}
