package com.example.lenovo.eats.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.eats.Activities.Bill_SplitBillActivity;
import com.example.lenovo.eats.ClassModel.Bill_MyOrder;
import com.example.lenovo.eats.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Bill_OrdersAdapter extends RecyclerView.Adapter<Bill_OrdersViewholder> {
//    private ArrayList<Order> orders_list;
    private ArrayList<Bill_MyOrder> orders_list;
    private boolean merge = false;
    private Context context;

    public void setMerge(){
        merge = true;
        notifyDataSetChanged();
    }

    public void clearMerge(){
        merge = false;
        notifyDataSetChanged();
    }

//    public OrdersAdapter(ArrayList<Order> orders, Context context)
    public Bill_OrdersAdapter(ArrayList<Bill_MyOrder> orders, Context context)
    {
        orders_list = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public Bill_OrdersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_view, parent, false);
        return new Bill_OrdersViewholder(v, new OrdersAdapterListener(){
            @Override
            public void onSplitClick(View v, int position) {
//                Toast.makeText(v.getContext().getApplicationContext(),"bills merged", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Bill_SplitBillActivity.class);
//                intent.putExtra("order", (Serializable) orders_list.get(position));
                intent.putExtra("position", (Serializable) position);
                intent.putExtra("allOrders", orders_list);
//                context.startActivity(intent);
                ((Activity)context).startActivityForResult(intent, 1);
            }
        }, new OrdersCheckboxListener(){

            @Override
            public void onChecked(View v, int position, boolean isChecked) {
                orders_list.get(position).setChecked(isChecked);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull Bill_OrdersViewholder holder, int position) {
        if(orders_list != null)
        {
            holder.setData(orders_list.get(position), merge);
        }
    }


    @Override
    public int getItemCount() {
        if(orders_list != null)
            return orders_list.size();
        else
            return 0;
    }


    public void setOrders_list(ArrayList<Bill_MyOrder> orders_list) {
        this.orders_list = orders_list;
    }

    public interface OrdersAdapterListener {

        void onSplitClick(View v, int position);
    }

    public interface OrdersCheckboxListener {
        void onChecked(View v, int position, boolean isChecked);
    }
}
