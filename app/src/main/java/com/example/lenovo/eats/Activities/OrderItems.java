package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.lenovo.eats.Adapters.MenuItemRecyclerViewAdapter;
import com.example.lenovo.eats.ClassModel.MenuItemView;
import com.example.lenovo.eats.ClassModel.MenuItemComplaint;
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

public class OrderItems extends AppCompatActivity implements OnListFragmentInteractionListener {

    private RecyclerView recyclerView;
    private MenuItemRecyclerViewAdapter adapter;
    OnListFragmentInteractionListener mListener;
    ArrayList<MenuItemView> menuItems;
    HashMap<String,MenuItemComplaint> menuItemsReordered;
    FirebaseDatabase firebaseDatabase;
    TextView itemsCountTextView;
    Integer itemsCount = 0;
    String orderId;
    String selectedMenuItemId;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);
        menuItems = new ArrayList<>();
        menuItemsReordered = new HashMap<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        setTitle("Mini Order");

        itemsCountTextView = findViewById(R.id.itemsAdded);
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

                FetchMenuItem(dataSnapshot.getKey(),dataSnapshot.getValue(Integer.class));
                FetchMenuItem(dataSnapshot.getKey(),dataSnapshot.getValue(Integer.class));
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

    void FetchMenuItem(String key, final Integer quantity)
    {
        DatabaseReference databaseReference=firebaseDatabase.getReference("MenuItem");
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MenuItemView menuItem = dataSnapshot.getValue(MenuItemView.class);
                menuItem.setMenuItemId(dataSnapshot.getKey());
                menuItem.setQuantityOrdered(quantity);
                menuItems.add(menuItem);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                MenuItemComplaint menuItemComplaint = getIntent().getExtras().getParcelable("menuItem");
                if(!menuItemsReordered.containsKey(selectedMenuItemId))
                {
                    //meaning it is a new menu item for reorder
                    itemsCount++;
                    itemsCountTextView.setText(Integer.toString(itemsCount)+" Items Added");
                }
                menuItemsReordered.put(selectedMenuItemId,menuItemComplaint);
            }
        }
    }



    @Override
    public void onListFragmentInteraction(Bundle details, String action, boolean isFabClicked) {
        if(type.equals("customer")) {
            Intent intent = new Intent(this, MenuItemComplaintDetail.class);
            selectedMenuItemId = details.getString("menuItemId");
            intent.putExtra("menuItemId", selectedMenuItemId);
            intent.putExtra("menuItemName", details.getString("menuItemName"));
            startActivityForResult(intent,1);
        }
        else
        {
            Intent intent = new Intent(this, ChefComplaiantDetail.class);
            selectedMenuItemId = details.getString("menuItemId");
            intent.putExtra("menuItemId", selectedMenuItemId);
            intent.putExtra("menuItemName", details.getString("menuItemName"));
            intent.putExtra("orderId",orderId);
            startActivity(intent);
        }




    }
}
