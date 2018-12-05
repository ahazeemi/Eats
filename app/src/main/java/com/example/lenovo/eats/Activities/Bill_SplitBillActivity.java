package com.example.lenovo.eats.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.eats.Adapters.Bill_OrderAdapter;
import com.example.lenovo.eats.Adapters.Bill_SplitAdapter;
import com.example.lenovo.eats.ClassModel.Bill_MyOrder;
import com.example.lenovo.eats.ClassModel.Bill_OrderDish;
import com.example.lenovo.eats.R;

import java.util.ArrayList;

public class Bill_SplitBillActivity extends AppCompatActivity {

    private RecyclerView mOrdersRecyclerView;
    private Bill_SplitAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView newOrdersRecyclerView;
    private Bill_OrderAdapter newAdapter;
    private RecyclerView.LayoutManager newLayoutManager;
    private ArrayList<Bill_MyOrder> allOrders;

    private ArrayList<Bill_OrderDish> oldDishes;
    private ArrayList<Bill_OrderDish> dishes;
    private ArrayList<Bill_OrderDish> newDishes;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill);

        Button cancel = findViewById(R.id.cancelSplitBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.show();
            }
        });


        Button done = findViewById(R.id.doneSplitBtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do split thingy here
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                for(int i = 0; i < dishes.size(); i++){
                                    Bill_OrderDish temp = dishes.get(i);
                                    if (temp.getQuantity() == 0){
                                        dishes.remove(temp);
                                        i--;
                                    }
                                }

                                for(int i = 0; i < newDishes.size(); i++){
                                    Bill_OrderDish temp = newDishes.get(i);
                                    if (temp.getQuantity() == 0){
                                        newDishes.remove(temp);
                                        i--;
                                    }
                                }

                                if (dishes.size() > 0 && newDishes.size() > 0) {

                                    allOrders.get(position).setDishes(dishes);
                                    allOrders.get(position).setNumber_of_items(dishes.size());

                                    //calculate new bill for both allOrders.get(position) and new Order
                                    allOrders.get(position).calcuateBill();
                                    //create id from DB

                                    Bill_MyOrder myOrder = new Bill_MyOrder(newDishes,20.1f, 2.2f, 12.3f, 0, newDishes.size(), "14", allOrders.get(position).getTimestamp(), allOrders.get(position).getTime_of_completion(), allOrders.get(position).getSpecialInstruction(), allOrders.get(position).getOriginal_order_id());
                                    myOrder.setInserted(true);
                                    myOrder.calcuateBill();
                                    allOrders.add(myOrder);

//                                    allOrders.add(new MyOrder(newDishes, 20.1f, 2.2f, 12.3f, 0, "14"));

                                    Intent intent = new Intent();
                                    intent.putExtra("allOrders", allOrders);
                                    setResult(1, intent);
                                }
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.show();
            }
        });




        Intent orderIntent = getIntent();
        allOrders = (ArrayList<Bill_MyOrder>) orderIntent.getSerializableExtra("allOrders");
        position = (int) orderIntent.getSerializableExtra("position");

//        ArrayList<HashMap<String, OrderItem>> oldDishMap = allOrders.get(position).getOrder_items();
//        oldDishes = new Ar;
        oldDishes = allOrders.get(position).getDishes();
        int dSize = oldDishes.size();

        dishes = new ArrayList<>();
        for (int i = 0; i < dSize; i++){
            dishes.add(new Bill_OrderDish(oldDishes.get(i).getId(), oldDishes.get(i).getName(), oldDishes.get(i).getQuantity(), oldDishes.get(i).getPrice()));
        }

        newDishes = new ArrayList<>();
        for (int i = 0; i < dSize; i++){
            newDishes.add(new Bill_OrderDish(oldDishes.get(i).getId(), oldDishes.get(i).getName(), 0, 0));
        }


        mOrdersRecyclerView = findViewById(R.id.actualOrderList);
        mLayoutManager = new LinearLayoutManager(this);
        mOrdersRecyclerView.setLayoutManager(mLayoutManager);
        mOrdersRecyclerView.setItemAnimator(new DefaultItemAnimator());

        newOrdersRecyclerView = findViewById(R.id.newBillList);
        newLayoutManager = new LinearLayoutManager(this);
        newOrdersRecyclerView.setLayoutManager(newLayoutManager);
        newOrdersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        newAdapter = new Bill_OrderAdapter(newDishes);
        newOrdersRecyclerView.setAdapter(newAdapter);

        mAdapter = new Bill_SplitAdapter(dishes, newDishes, newAdapter);
        mOrdersRecyclerView.setAdapter(mAdapter);
    }
}
