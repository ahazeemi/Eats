package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lenovo.eats.Adapters.OrderItemsAdapter;
import com.example.lenovo.eats.ClassModel.TrioOrderItems;
import com.example.lenovo.eats.R;

import java.util.ArrayList;

public class TrioOrderConfirmationActivity extends AppCompatActivity
{

    private ListView listView;
    private OrderItemsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        listView = (ListView) findViewById(R.id.orderList);
        ArrayList<TrioOrderItems> OrderList = new ArrayList<>();

        Log.wtf("check 123", "checking");

        //Toast.makeText(getApplicationContext(),String.valueOf(TrioOrderManagementActivity.userOrder.no_of_items),Toast.LENGTH_SHORT );
        //Log.d("no of items", String.valueOf(TrioOrderManagementActivity.userOrder.no_of_items));
        for (int i = 0; i < TrioOrderManagementActivity.userOrder.totalItems(); ++i) {
            Log.wtf("blaa", TrioOrderManagementActivity.userOrder.getitem_name(0));
            OrderList.add(new TrioOrderItems(R.drawable.chicken_karahi, TrioOrderManagementActivity.userOrder.getitem_name(i), String.valueOf(TrioOrderManagementActivity.userOrder.getItemQuantity(i)), TrioOrderManagementActivity.userOrder.getSpecialIns(i)));
            // Log.wtf("Order item name: "+ i, TrioOrderManagementActivity.userOrder.getitem_name(i) );
            //Log.wtf("Order item quantity: "+ i, String.valueOf(TrioOrderManagementActivity.userOrder.getItemQuantity(i)) );
        }

        //OrderList.add( new TrioOrderItems(R.drawable.fries, "Cheese Fries" , "Quantity: 2"));
        //OrderList.add( new TrioOrderItems(R.drawable.chowmein, "Chowmein" , "Quantity: 1"));
        //OrderList.add( new TrioOrderItems(R.drawable.cb, "Chicken Burger" , "Quantity: 3"));
        //OrderList.add( new TrioOrderItems(R.drawable.beef, "Beef Karahi" , "Quantity: 1"));


        mAdapter = new OrderItemsAdapter(this, OrderList);
        listView.setAdapter(mAdapter);


        Button continue_button = (Button) findViewById(R.id.confirmOrderButton);
        continue_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {


                Intent i = new Intent(getApplicationContext(), TrioWaitingTimeActivity.class);
                startActivity(i);
            }
        });

    }
}
