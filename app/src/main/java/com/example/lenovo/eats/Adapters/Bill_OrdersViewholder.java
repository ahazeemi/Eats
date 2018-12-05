package com.example.lenovo.eats.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.lenovo.eats.ClassModel.Bill_MyOrder;
import com.example.lenovo.eats.R;


public class Bill_OrdersViewholder extends RecyclerView.ViewHolder {
    private TextView subtotal, tax, grandTotal, orderNum;
    private RecyclerView mOrder;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button splitBtn;
    private CheckBox checkBox;

    public Bill_OrdersViewholder(@NonNull final View itemView, final Bill_OrdersAdapter.OrdersAdapterListener odListener, final Bill_OrdersAdapter.OrdersCheckboxListener checkboxListener) {
        super(itemView);
        mOrder = itemView.findViewById(R.id.orderList);
        subtotal = itemView.findViewById(R.id.subtotal);
        tax = itemView.findViewById(R.id.tax);
        grandTotal = itemView.findViewById(R.id.grandTotal);
        splitBtn = itemView.findViewById(R.id.splitBtn);

        splitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                odListener.onSplitClick(v, getAdapterPosition());
            }
        });

        orderNum = itemView.findViewById(R.id.orderID);
        checkBox = itemView.findViewById(R.id.mergeSelect);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkboxListener.onChecked(buttonView, getAdapterPosition(), isChecked);
            }
        });
    }

    public void setData(Bill_MyOrder order, boolean merge)
    {
        if(merge){
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(false);
        }
        else
            checkBox.setVisibility(View.GONE);

        mLayoutManager = new LinearLayoutManager(itemView.getContext());
        mOrder.setLayoutManager(mLayoutManager);
        mOrder.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new Bill_OrderAdapter(order.getDishes());
        mOrder.setAdapter(mAdapter);

        orderNum.setText(order.getOrderId());
        subtotal.setText(String.valueOf(order.getSubTotal()));
        tax.setText(String.valueOf(order.getTax()));
        grandTotal.setText(String.valueOf(order.getGrandTotal()));
    }
}
