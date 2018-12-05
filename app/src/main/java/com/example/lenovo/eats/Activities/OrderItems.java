package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.eats.Adapters.MenuItemRecyclerViewAdapter;
import com.example.lenovo.eats.ClassModel.CustomerMiniOrder;
import com.example.lenovo.eats.ClassModel.Ingredient;
import com.example.lenovo.eats.ClassModel.MenuItem;
import com.example.lenovo.eats.ClassModel.MenuItemView;
import com.example.lenovo.eats.ClassModel.MenuItemComplaint;
import com.example.lenovo.eats.ClassModel.OrderMenuItem;
import com.example.lenovo.eats.Interfaces.OnListFragmentInteractionListener;
import com.example.lenovo.eats.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderItems extends AppCompatActivity implements OnListFragmentInteractionListener {

    private RecyclerView recyclerView;
    private MenuItemRecyclerViewAdapter adapter;
    OnListFragmentInteractionListener mListener;
    ArrayList<MenuItemView> menuItems;
    HashMap<String,MenuItemComplaint> menuItemsReordered;
    HashMap <String,Ingredient> ingredientsMap;
    ProgressBar progressBar;

    FirebaseDatabase firebaseDatabase;
    TextView itemsCountTextView;
    Integer itemsCount = 0;
    String orderId;
    String selectedMenuItemId;
    Integer selectedMenuItemPosition;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);
        menuItems = new ArrayList<>();
        menuItemsReordered = new HashMap<>();
        ingredientsMap = new HashMap<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        setTitle("Mini Order");

        itemsCountTextView = findViewById(R.id.itemsAdded);
        progressBar = ((ProgressBar)findViewById(R.id.progressBar));
        recyclerView = findViewById(R.id.items_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter = new MenuItemRecyclerViewAdapter(menuItems,this,  mListener = this));

        orderId = getIntent().getStringExtra("orderId");
        type=getIntent().getStringExtra("type");

        updateUI();

    }

    void updateUI()
    {
        DatabaseReference databaseReference=firebaseDatabase.getReference("Order/"+orderId+"/order_items");
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                FetchMenuItem(dataSnapshot.getKey(),dataSnapshot.getValue(OrderMenuItem.class));
                //FetchMenuItem(dataSnapshot.getKey(),dataSnapshot.getValue(Integer.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void FetchMenuItem(String key, final OrderMenuItem item)
    {
        final int quantity = item.getQuantity();
        DatabaseReference databaseReference=firebaseDatabase.getReference("MenuItem");
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MenuItemView menuItem = dataSnapshot.getValue(MenuItemView.class);
                if(menuItem!=null) {
                    menuItem.setMenuItemId(dataSnapshot.getKey());
                    menuItem.setQuantityOrdered(quantity);
                    menuItems.add(menuItem);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onFabClick(View view)
    {
        if(type.equals("customer"))
        {
            if(menuItemsReordered.size()==0)
            {
                Snackbar.make(findViewById(R.id.constraintLayout),
                        "Select at least one item", Snackbar.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            for(Map.Entry<String,Ingredient> entry:ingredientsMap.entrySet())
            {
                firebaseDatabase.getReference("Inventory").child(entry.getKey()).child("reserved_qty").setValue(entry.getValue().getReserved_qty());
                firebaseDatabase.getReference("Inventory").child(entry.getKey()).child("available_qty").setValue(entry.getValue().getAvailable_qty());
            }

            float price = 0;

            for(Map.Entry<String,MenuItemComplaint> entry:menuItemsReordered.entrySet()) {
                MenuItemComplaint menuItemComplaint = entry.getValue();
                price+=menuItemComplaint.getSale_price()*menuItemComplaint.getQuantity_reordered();
            }

            CustomerMiniOrder customerMiniOrder = new CustomerMiniOrder();
            customerMiniOrder.setOrder_items(menuItemsReordered);
            customerMiniOrder.setTimestamp(System.currentTimeMillis());
            customerMiniOrder.setMain_order_id(orderId);
            customerMiniOrder.setPrice(price);
            firebaseDatabase.getReference("CustomerMiniOrder").push().setValue(customerMiniOrder);

            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Mini Order Placed", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            finish();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                MenuItemComplaint menuItemComplaint = data.getExtras().getParcelable("menuItem");

                if(!menuItemsReordered.containsKey(selectedMenuItemId))
                {
                    //meaning it is a new menu item for reorder
                    itemsCount++;
                    itemsCountTextView.setText(Integer.toString(itemsCount)+" Items Added");
                }
                menuItemsReordered.put(selectedMenuItemId,menuItemComplaint);
                menuItems.get(selectedMenuItemPosition).setAddedToOrder(true);
                menuItems.get(selectedMenuItemPosition).setQuantityOrdered(menuItemComplaint.getQuantity_reordered());
                HashMap<String, Ingredient> temp = (HashMap<String, Ingredient>)data.getSerializableExtra("ingredientsMap");
                ingredientsMap.putAll(temp);
                adapter.notifyDataSetChanged();
            }
        }
        else if(requestCode==2)
        {
            if(resultCode == RESULT_OK) {
                String key = data.getStringExtra("menuItemId");
                MenuItemView temp = new MenuItemView();
                temp.setMenuItemId(key);
                int index = menuItems.indexOf(temp);
                menuItems.remove(index);
                adapter.notifyDataSetChanged();
                if (menuItems.size() == 0) {
                    Toast.makeText(this, "All Items Reordered", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }



    @Override
    public void onListFragmentInteraction(Bundle details, String action, boolean isFabClicked) {
        if(type.equals("customer")) {
            Intent intent = new Intent(this, MenuItemComplaintDetail.class);
            selectedMenuItemId = details.getString("menuItemId");
            selectedMenuItemPosition = details.getInt("menuItemPosition");
            intent.putExtra("menuItemId", selectedMenuItemId);
            intent.putExtra("menuItemName", details.getString("menuItemName"));
            intent.putExtra("menuItemObj",menuItemsReordered.get(selectedMenuItemId));
            intent.putExtra("ingredientsMap",ingredientsMap);

            startActivityForResult(intent,1);
        }
        else
        {
            Intent intent = new Intent(this, ChefComplaiantDetail.class);
            selectedMenuItemId = details.getString("menuItemId");
            intent.putExtra("menuItemId", selectedMenuItemId);
            intent.putExtra("menuItemName", details.getString("menuItemName"));
            intent.putExtra("orderId",orderId);
            startActivityForResult(intent,2);
        }




    }
}
