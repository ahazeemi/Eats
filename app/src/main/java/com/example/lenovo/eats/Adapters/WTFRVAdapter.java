package com.example.lenovo.eats.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.eats.Activities.WTFMainActivity;
import com.example.lenovo.eats.ClassModel.WTFOrderItem;

import java.util.List;

public class WTFRVAdapter extends RecyclerView.Adapter<WTFOrderItemViewHolder> {

    WTFMainActivity context;
    private List<WTFOrderItem> items;
    private int itemLayout;

    public WTFRVAdapter(List<WTFOrderItem> items, int itemLayout, WTFMainActivity context) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.context=context;
    }

    @Override public WTFOrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new WTFOrderItemViewHolder(v);
    }

    @Override public void onBindViewHolder(WTFOrderItemViewHolder holder, final int position) {
        if(items != null && holder != null)
        {
            holder.id.setText(Integer.toString(items.get(position).getItemId()));
            holder.name.setText(items.get(position).getItemName());

            holder.quantity.setText(Integer.toString(items.get(position).getQuantity()));

            holder.quantity.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {

                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });


            holder.cancel.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    context.order.get(position).quantity=0;
                    notifyDataSetChanged();

                }
            });

            holder.up.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    context.order.get(position).quantity++;
                    notifyDataSetChanged();

                }
            });

            holder.down.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(context.order.get(position).quantity>0)context.order.get(position).quantity--;
                    notifyDataSetChanged();

                }
            });

        }
    }

    @Override public int getItemCount() {
        if(items != null)
            return items.size();
        else
            return 0;
    }

}
