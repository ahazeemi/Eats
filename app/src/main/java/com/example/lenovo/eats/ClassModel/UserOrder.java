package com.example.lenovo.eats.ClassModel;

import android.app.Application;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class UserOrder {
    private ArrayList<String> items;
    private ArrayList<String> item_name;
    private ArrayList<Integer> item_quantity;
    private ArrayList<String> special_instructions;

    //    TO DO: Ask kitchen module for time format
    private Time time_of_completion;
    private Time timestamp;

     public UserOrder() {
        items = new ArrayList<>();
        item_quantity = new ArrayList<>();
        special_instructions = new ArrayList<>();
        item_name = new ArrayList<>();
    }

    public int addItem(String id, String name) {
        boolean flag = false;
        int qty = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == id)
            {
                item_quantity.set(i, (item_quantity.get(i) + 1));
                qty = item_quantity.get(i);
                Log.wtf("item id " + i, String.valueOf(id));
                Log.wtf("item quantity  " + i, String.valueOf(item_quantity.get(i)));
                flag = true;
            }
        }
        if (flag == false) {
            items.add(id);
            item_quantity.add(1);
            special_instructions.add("");
            item_name.add(name);
            qty = 1;
        }

        return qty;
    }

    public int minusItem(String id) {
        int qty = 0;

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == id) {
                item_quantity.set(i, (item_quantity.get(i) - 1));
                qty = item_quantity.get(i);
                Log.d("item id ", String.valueOf(id));
                Log.d("item id ", String.valueOf(item_quantity.get(i)));

                if (qty == 0) {
                    item_quantity.remove(i);
                    items.remove(i);
                    special_instructions.remove(i);
                    item_name.remove(i);
                }
            }
        }

        return qty;
    }

    public void updateItem(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == id) {
                item_quantity.set(i, (item_quantity.get(i) + 1));
            }
        }
    }

    public void setSpecial_instructions(String id, String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == id) {
                special_instructions.set(i, name);
            }
        }
    }

    public void setTime_of_completion(Time t) {
        this.time_of_completion = t;
    }

    public String getitem_name(int id) {
        return item_name.get(id);
    }

    public int getItemQuantity(int i)
    {
        return item_quantity.get(i);
    }

    public String getSpecialIns(int i)
    {
        return special_instructions.get(i);
    }

    public void setTimestamp(Time t) {
        this.timestamp = t;
    }

    public int totalItems()
    {
        return items.size();
    }
}