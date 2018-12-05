package com.example.lenovo.eats.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.lenovo.eats.ClassModel.Bill_OrderDish;
import com.example.lenovo.eats.R;

import java.util.ArrayList;

public class Bill_SplitAdapter extends RecyclerView.Adapter<Bill_SplitViewHolder> {
    private ArrayList<Bill_OrderDish> dishArrayList;
    private ArrayList<Bill_OrderDish> newDishList;
    private Bill_OrderAdapter newBillAdapter;

    public Bill_SplitAdapter(ArrayList<Bill_OrderDish> dishDetails, ArrayList<Bill_OrderDish> newDishes, Bill_OrderAdapter newAdapter) {
        dishArrayList = dishDetails;
        newDishList = newDishes;
        newBillAdapter = newAdapter;
    }
//    private SplitAdapterListener spListener;

    @NonNull
    @Override
    public Bill_SplitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_split_order_view, parent, false);
        return new Bill_SplitViewHolder(v, new SplitAdapterListener(){
            @Override
            public void addOnClick(View v, int position) {

                //add quantity checks here
                if (newDishList.get(position).getQuantity() > 0) {
                    float price = newDishList.get(position).getPrice() / newDishList.get(position).getQuantity();

                    dishArrayList.get(position).setQuantity(dishArrayList.get(position).getQuantity() + 1);
                    dishArrayList.get(position).setPrice(dishArrayList.get(position).getPrice() + price);

                    newDishList.get(position).setQuantity(newDishList.get(position).getQuantity() - 1);
                    newDishList.get(position).setPrice((float) (Math.round((newDishList.get(position).getPrice() - price) * 100.0) / 100.0));
                    notifyDataSetChanged();
                    newBillAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void subOnClick(View v, int position) {

                //add quantity checks here
                if (dishArrayList.get(position).getQuantity() > 0) {
                    float price = dishArrayList.get(position).getPrice() / dishArrayList.get(position).getQuantity();

                    dishArrayList.get(position).setQuantity(dishArrayList.get(position).getQuantity() - 1);
                    dishArrayList.get(position).setPrice(dishArrayList.get(position).getPrice() - price);

                    newDishList.get(position).setQuantity(newDishList.get(position).getQuantity() + 1);
                    newDishList.get(position).setPrice((float) (Math.round((newDishList.get(position).getPrice() + price) * 100.0) / 100.0));
                    notifyDataSetChanged();
                    newBillAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull Bill_SplitViewHolder holder, int position) {
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

    public interface SplitAdapterListener {

        void addOnClick(View v, int position);

        void subOnClick(View v, int position);
    }
}
