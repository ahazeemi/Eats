package com.example.lenovo.eats.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.eats.ClassModel.Bill_OrderDish;
import com.example.lenovo.eats.R;


public class Bill_SplitViewHolder extends RecyclerView.ViewHolder {
    private TextView num, dishName, quantity, price;
    private Button dishAdd;
    private Button dishSub;

    public Bill_SplitViewHolder(@NonNull View itemView, final Bill_SplitAdapter.SplitAdapterListener spListener) {
        super(itemView);

        num = itemView.findViewById(R.id.dishNumSplit);
        dishName = itemView.findViewById(R.id.dishNameSplit);
        quantity = itemView.findViewById(R.id.dishQuantitySplit);
        price = itemView.findViewById(R.id.dishPriceSplit);

        dishAdd = itemView.findViewById(R.id.dishAdd);
        dishAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spListener.addOnClick(v, getAdapterPosition());
            }
        });

        dishSub = itemView.findViewById(R.id.dishSub);
        dishSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spListener.subOnClick(v, getAdapterPosition());
            }
        });
    }

    public void setData(int number, Bill_OrderDish orderItem)
    {
        num.setText(String.valueOf(number));
        dishName.setText(orderItem.getName());
        quantity.setText(String.valueOf(orderItem.getQuantity()));
        price.setText(String.valueOf(orderItem.getPrice()));
    }

}
