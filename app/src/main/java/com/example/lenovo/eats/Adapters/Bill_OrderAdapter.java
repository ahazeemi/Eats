package com.example.lenovo.eats.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.lenovo.eats.ClassModel.Bill_OrderDish;
import com.example.lenovo.eats.R;

import java.util.ArrayList;

public class Bill_OrderAdapter extends RecyclerView.Adapter<Bill_OrderViewholder> {
    private ArrayList<Bill_OrderDish> dishArrayList;
    public Bill_OrderAdapter(ArrayList<Bill_OrderDish> dishDetails) {
        dishArrayList = dishDetails;
    }

    @NonNull
    @Override
    public Bill_OrderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_order_view, parent, false);
        return new Bill_OrderViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Bill_OrderViewholder holder, int position) {
        if(dishArrayList != null && holder != null)
        {
            holder.setData(position + 1, dishArrayList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(dishArrayList != null)
            return dishArrayList.size();
        else
            return 0;
    }
}
