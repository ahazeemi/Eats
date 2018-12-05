package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.eats.Activities.TrioOrderManagementActivity;
import com.example.lenovo.eats.ClassModel.MenuItems;
import com.example.lenovo.eats.R;

import java.util.ArrayList;
import java.util.List;

public class MenuItemsAdapter extends ArrayAdapter<MenuItems>
{
    private Context mContext;
    private List<MenuItems> MenuList = new ArrayList<>();
    final ArrayList<MenuItems> OrderList = new ArrayList<>();

    public MenuItemsAdapter(@NonNull Context context, ArrayList<MenuItems> list) {
        super(context, 0 , list);
        mContext = context;
        MenuList = list;
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

         View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_list_menu_item,parent,false);

        final MenuItems currentItem = MenuList.get(position);
        //Log.wtf("position", String.valueOf(position));

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_menu);
        image.setImageResource(currentItem.getmImageDrawable());

        TextView name = (TextView) listItem.findViewById(R.id.textView_itemname);
        name.setText(currentItem.getmName());

        TextView price = (TextView) listItem.findViewById(R.id.price);
        price.setText(currentItem.getmPrice());

        EditText specialEt = listItem.findViewById(R.id.specialEt);

        final TextView quantityTv = listItem.findViewById(R.id.quantityTv);

        Button add_button = listItem.findViewById(R.id.addButton);
        add_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.wtf("position", String.valueOf(position));
                String q = String.valueOf(quantityTv.getText());
               int qty =  TrioOrderManagementActivity.userOrder.addItem(currentItem.getItem_id(), currentItem.getmName());
               // int qty = TrioOrderManagementActivity.userOrder.addItem(currentItem.getItem_id(), currentItem.getmName(),position);

                quantityTv.setText(qty + "");
            }
        });

        Button minus_button = listItem.findViewById(R.id.subtractButton);
        minus_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int qty = TrioOrderManagementActivity.userOrder.minusItem(currentItem.getItem_id());
                quantityTv.setText(qty + "");
            }
        });

        specialEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TrioOrderManagementActivity.userOrder.setSpecial_instructions(currentItem.getItem_id(), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return listItem;
    }



}
