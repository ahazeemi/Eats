package com.example.lenovo.eats.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.eats.Adapters.Bill_OrdersAdapter;
import com.example.lenovo.eats.ClassModel.Bill_Order;
import com.example.lenovo.eats.ClassModel.Bill_MyOrder;
import com.example.lenovo.eats.ClassModel.Bill_OrderDish;
import com.example.lenovo.eats.ClassModel.BIll_OrderItem;
import com.example.lenovo.eats.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import de.cketti.mailto.EmailIntentBuilder;

public class Bill_ShowOrderActivity extends AppCompatActivity {

    private RecyclerView mOrdersRecyclerView;
    private Bill_OrdersAdapter mAdapter;
    private Button merge_btn, checkout_btn;
    private Menu mMenu;
    private DatabaseReference dbref;

    private ArrayList<Bill_MyOrder> allOrders;
    private ArrayList<Bill_MyOrder> removedOrders;
    private HashMap<String,Bill_Order> allDBOrders;
    private HashMap<String,com.example.lenovo.eats.ClassModel.MenuItem> allMenuItems;
    private float taxRate = 17.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_activity_main);
        dbref = FirebaseDatabase.getInstance().getReference("Order");
        merge_btn = findViewById(R.id.mergerBtn);
        checkout_btn = findViewById(R.id.checkoutBtn);

        merge_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setMerge();
                merge_btn.setEnabled(false);
                checkout_btn.setEnabled(false);
                mMenu.findItem(R.id.cancelMerge).setVisible(true);
                mMenu.findItem(R.id.doneMerge).setVisible(true);
            }
        });

        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkoutDialogue();
            }
        });

        removedOrders = new ArrayList<>();

        // Assuming list of orders from Orders module will come

//        Intent orderIntent =  getIntent();
//        allDBOrders = (HashMap<String,Order>) orderIntent.getSerializableExtra("orders"); get order list from order module
//        allMenuItems = (HashMap<String, com.example.workmachine.billgeneration.DataObjects.MenuItem>) orderIntent.getSerializableExtra("menu_items"); get menu items list from order module
//        allOrders = convertToMyOrders(allDBOrders, allMenuItems);


        //dummy data here. Orders will be maintained in allOrders list throughout the module
        allOrders = new ArrayList<>();

        ArrayList<Bill_OrderDish> dishes = new ArrayList<>();
        dishes.add(new Bill_OrderDish("1", "Beef Karahi", 1, 800f));
        dishes.add(new Bill_OrderDish("-LSxOYhMPOx9i7CnMmql", "Prawns", 1, 1500f));
        Bill_MyOrder mo = new Bill_MyOrder(dishes, 0, 0, 0, 0, dishes.size(), "1", 1234, 12345, "Bas jldi bn ja", "1");
        mo.calcuateBill();
        allOrders.add(mo);

        ArrayList<Bill_OrderDish> dishes2 = new ArrayList<>();
        dishes2.add(new Bill_OrderDish("1", "Beef Karahi", 1, 800f));
        Bill_MyOrder mo2 = new Bill_MyOrder(dishes2, 0, 0, 0, 0, dishes2.size(), "-LSxj1XuKu6ynWc4giDS", 1234, 12345, "Bas jldi bn ja", "-LSxj1XuKu6ynWc4giDS");
        mo2.calcuateBill();
        allOrders.add(mo2);


        mOrdersRecyclerView = findViewById(R.id.ordersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mOrdersRecyclerView.setLayoutManager(mLayoutManager);
        mOrdersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new Bill_OrdersAdapter(allOrders, this);
        mOrdersRecyclerView.setAdapter(mAdapter);
    }


    private ArrayList<Bill_MyOrder> convertToMyOrders(HashMap<String,Bill_Order> dbOrders, HashMap<String, com.example.lenovo.eats.ClassModel.MenuItem> dbMenuItems){
        ArrayList<Bill_MyOrder> myOrders = new ArrayList<>();
        for (String key:
             dbOrders.keySet()) {
            Bill_Order o = dbOrders.get(key);
            if (o != null) {
                HashMap<String, BIll_OrderItem> oItems = o.getOrder_items();

                ArrayList<Bill_OrderDish> dish = new ArrayList<>();

                for (String k:
                     oItems.keySet()) {
                    BIll_OrderItem oIt = oItems.get(key);

                    if (oIt != null){
                        com.example.lenovo.eats.ClassModel.MenuItem  item = dbMenuItems.get(k);

                        if (item != null)
                            dish.add(new Bill_OrderDish(k, item.getName(), oIt.getQuantity(), item.getSale_price() * oIt.getQuantity()));
                    }
                }

                Bill_MyOrder myOrder = new Bill_MyOrder(dish, 0, 0, 0, 0, dish.size(), key, o.getTimestamp(), o.getTime_of_completion(), o.getSpecialInstruction(), o.getOriginal_order_id());
                myOrder.calcuateBill();
                myOrders.add(myOrder);
            }
        }
        return myOrders;
    }

//    private HashMap<String, Order> convertToOrders(ArrayList<MyOrder> allMyOrders){
//        HashMap<String, Order> orders = new HashMap<>();
//
//        for (MyOrder myOrder:
//             allMyOrders) {
//            HashMap<String, OrderItem> orderItems = new HashMap<>();
//
//            for (OrderDish od:
//                 myOrder.getDishes()) {
//                OrderItem orderItem = new OrderItem(od.getQuantity(), od.getPrice());
//                orderItems.put(od.getId(), orderItem);
//            }
//
//            Order ord = new Order(orderItems, myOrder.getSpecialInstruction(), myOrder.getOriginal_order_id(), myOrder.getBillStatus(), myOrder.getNumber_of_items(), myOrder.getGrandTotal(), myOrder.getTax(), myOrder.getSubTotal(), myOrder.getTimestamp(), myOrder.getTime_of_completion());
//            orders.put(myOrder.getOrderId(), ord);
//        }
//
//        return orders;
//    }

    private Bill_Order convertToOrder(Bill_MyOrder myOrder){
        HashMap<String, BIll_OrderItem> orderItems = new HashMap<>();

        for (Bill_OrderDish od:
                myOrder.getDishes()) {
            BIll_OrderItem orderItem = new BIll_OrderItem(od.getQuantity(), od.getPrice());
            orderItems.put(od.getId(), orderItem);
        }

        return new Bill_Order(orderItems, myOrder.getSpecialInstruction(), myOrder.getOriginal_order_id(), myOrder.getBillStatus(), myOrder.getNumber_of_items(), myOrder.getGrandTotal(), myOrder.getTax(), myOrder.getSubTotal(), myOrder.getTimestamp(), myOrder.getTime_of_completion());
    }

    public void checkoutDialogue()
    {
        final Dialog checkoutDialog = new Dialog(this);
        checkoutDialog.setContentView(R.layout.bill_email_dialogue);
        checkoutDialog.show();

        Button skipBtn = checkoutDialog.findViewById(R.id.skipBtn);
        Button doneBtn = checkoutDialog.findViewById(R.id.doneBtn);

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOrders(allOrders, removedOrders);
                Intent intent = new Intent(getApplicationContext(), Bill_FeedbackActivity.class);
                startActivity(intent);
                finish();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOrders(allOrders, removedOrders);

                //email the bill to the customer and start feedback activity
                EditText email_address = checkoutDialog.findViewById(R.id.emailAddress);
                String email = email_address.getText().toString();
                String bill = "";
                for (int i = 0; i < allOrders.size(); i++) {
                    bill += "Order No. " + allOrders.get(i).getOrderId() + "\n";
                    ArrayList<Bill_OrderDish> dishes = allOrders.get(i).getDishes();
                    for (int j = 0; j < dishes.size(); j++) {
                        bill += dishes.get(j).getName() + "\t" + dishes.get(j).getQuantity() + "\t" + dishes.get(j).getQuantity() * dishes.get(j).getPrice() + "\n";
                    }
                    bill += "Subtotal: " + allOrders.get(i).getSubTotal() + "\n";
                    bill += "Tax: " + allOrders.get(i).getTax() + "\n";
                    bill += "Grand Total: " + allOrders.get(i).getGrandTotal() + "\n\n";
                }

                if(email.isEmpty())
                {
                    TextInputLayout inputLayout = checkoutDialog.findViewById(R.id.email);
                    inputLayout.setError("Please enter an email address");
                }
                else {
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Intent emailIntentStarted = EmailIntentBuilder.from(checkoutDialog.getContext())
                                .to(email)
                                .subject("Your bills at the Restaurant")
                                .body(bill).build();
                        startActivityForResult(emailIntentStarted, 2);
                    }
                    else
                    {
                        TextInputLayout inputLayout = checkoutDialog.findViewById(R.id.email);
                        inputLayout.setError("Please enter a valid email address");
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1) {
            allOrders = (ArrayList<Bill_MyOrder>) data.getExtras().getSerializable("allOrders");
            mAdapter.setOrders_list(allOrders);
            mAdapter.notifyDataSetChanged();
        }
        else if(requestCode == 2)
        {
            Intent intent = new Intent(getApplicationContext(), Bill_FeedbackActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void insertOrders(ArrayList<Bill_MyOrder> allMyOrders, ArrayList<Bill_MyOrder> removedOrders)
    {

        for (int i = 0; i < removedOrders.size(); i++){
            DatabaseReference dbChildRef = dbref.child(removedOrders.get(i).getOrderId());
            dbChildRef.removeValue();
        }

        for (int i = 0; i < allMyOrders.size(); i++){
            Bill_Order order = convertToOrder(allMyOrders.get(i));
            if (allMyOrders.get(i).isInserted()){
                //insert order
                dbref.push().setValue(order);
            } else {
                //update order
                DatabaseReference dbChildRef = dbref.child(allMyOrders.get(i).getOrderId());
                dbChildRef.setValue(order);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.merge_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cancelMerge:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mAdapter.clearMerge();
                                merge_btn.setEnabled(true);
                                checkout_btn.setEnabled(true);
                                mMenu.findItem(R.id.cancelMerge).setVisible(false);
                                mMenu.findItem(R.id.doneMerge).setVisible(false);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        });
                builder.show();
                return true;
            case R.id.doneMerge:
                //merge orders

                builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ArrayList<Integer> orderNums = new ArrayList<>();

                                for(int i = 0; i < allOrders.size(); i++){
                                    if (allOrders.get(i).isChecked()) {
                                        orderNums.add(i);
                                    }
                                }

                                if (orderNums.size() == 2) {

                                    Bill_MyOrder firstOrder = allOrders.get(orderNums.get(0));
                                    Bill_MyOrder secondOrder = allOrders.get(orderNums.get(1));

                                    int dishSize = secondOrder.getDishes().size();
                                    for (int i = 0; i < dishSize; i++) {
                                        Bill_OrderDish od = secondOrder.getDishes().get(i);
                                        int ds = firstOrder.getDishes().size();
                                        boolean found = false;
                                        for (int j = 0; j < ds; j++){
                                            if (firstOrder.getDishes().get(j).getId().equals(od.getId())){
                                                found = true;
                                                firstOrder.getDishes().get(j).setQuantity(firstOrder.getDishes().get(j).getQuantity() + od.getQuantity());
                                                firstOrder.getDishes().get(j).setPrice(firstOrder.getDishes().get(j).getPrice() + od.getPrice());
                                            }
                                        }
                                        if (!found)
                                            firstOrder.getDishes().add(secondOrder.getDishes().get(i));
                                    }

                                    firstOrder.calcuateBill();

                                    if (!secondOrder.isInserted())
                                        removedOrders.add(secondOrder);
                                    allOrders.remove(secondOrder);

                                    mAdapter.clearMerge();
                                    merge_btn.setEnabled(true);
                                    checkout_btn.setEnabled(true);
                                    mMenu.findItem(R.id.cancelMerge).setVisible(false);
                                    mMenu.findItem(R.id.doneMerge).setVisible(false);
                                    Toast.makeText(getApplicationContext(),"bills merged", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Please select 2 orders only!", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
