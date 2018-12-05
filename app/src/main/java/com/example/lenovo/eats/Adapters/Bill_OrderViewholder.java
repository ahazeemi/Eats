package com.example.lenovo.eats.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.eats.ClassModel.Bill_OrderDish;
import com.example.lenovo.eats.R;

public class Bill_OrderViewholder extends RecyclerView.ViewHolder {
    private TextView num, dishName, quantity, price;

    public Bill_OrderViewholder(@NonNull View itemView) {
        super(itemView);

        num = itemView.findViewById(R.id.dishNum);
        dishName = itemView.findViewById(R.id.dishName);
        quantity = itemView.findViewById(R.id.dishQuantity);
        price = itemView.findViewById(R.id.dishPrice);
    }

    public void setData(int number, Bill_OrderDish orderItem)
    {
        num.setText(String.valueOf(number));
        dishName.setText(orderItem.getName()); //get dish name from db
        quantity.setText(String.valueOf(orderItem.getQuantity()));
        price.setText(String.valueOf(orderItem.getPrice()));
    }

}
