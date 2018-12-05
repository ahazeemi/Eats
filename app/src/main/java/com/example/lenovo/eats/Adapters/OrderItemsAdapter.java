package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.eats.ClassModel.TrioOrderItems;
import com.example.lenovo.eats.R;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsAdapter extends ArrayAdapter<TrioOrderItems> {

    private Context mContext;
    private List<TrioOrderItems> OrderList = new ArrayList<>();

    public OrderItemsAdapter(@NonNull Context context, ArrayList<TrioOrderItems> list) {
        super(context, 0 , list);
        mContext = context;
        OrderList = list;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_list_order_item,parent,false);

        TrioOrderItems currentItem = OrderList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_order);
        image.setImageResource(currentItem.getmImageDrawable());

        TextView name = (TextView) listItem.findViewById(R.id.textView_orderitemname);
        name.setText(currentItem.getoName());

        TextView quantity = (TextView) listItem.findViewById(R.id.quantity);
        quantity.setText(currentItem.getquantity());

        //TextView ins = (TextView) listItem.findViewById(R.id.specialEt);
        //ins.setText(currentItem.getSpecial());

        return listItem;
    }

    @Override
    public int getCount() {
        return OrderList.size();
    }
}
