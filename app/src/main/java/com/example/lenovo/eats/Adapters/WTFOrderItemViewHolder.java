package com.example.lenovo.eats.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.eats.R;;

public class WTFOrderItemViewHolder extends RecyclerView.ViewHolder {

    public TextView id;
    public TextView name;
    public EditText quantity;
    public Button up;
    public Button down;
    public Button cancel;

    public WTFOrderItemViewHolder(View view) {
        super(view);

        id=(TextView)view.findViewById(R.id.id);
        name=(TextView)view.findViewById(R.id.name);
        quantity=(EditText)view.findViewById(R.id.quantity);
        up=(Button)view.findViewById(R.id.up);
        down=(Button)view.findViewById(R.id.down);
        cancel=(Button)view.findViewById(R.id.cancel);

    }

}
