package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.eats.Adapters.DishRecyclerViewAdapter;
import com.example.lenovo.eats.ClassModel.Dish;
import com.example.lenovo.eats.Interfaces.OnListFragmentInteractionListener;
import com.example.lenovo.eats.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderDishes extends AppCompatActivity implements OnListFragmentInteractionListener {

    private RecyclerView recyclerView;
    private DishRecyclerViewAdapter adapter;
    OnListFragmentInteractionListener mListener;
    ArrayList<Dish> dishes;
    FirebaseDatabase firebaseDatabase;
    int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dishes);

        recyclerView = findViewById(R.id.items_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter = new DishRecyclerViewAdapter(dishes,this,  mListener = this));

        orderId = getIntent().getIntExtra("orderId",0);

    }

    void updateUI()
    {
        DatabaseReference databaseReference=firebaseDatabase.getReference("Order/"+orderId+"/order_dishes");
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                FetchDish(dataSnapshot.getKey(),dataSnapshot.getValue().toString());
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

    void FetchDish(String key,String quantity)
    {
        DatabaseReference databaseReference=firebaseDatabase.getReference("Dish");
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Dish dish = dataSnapshot.getValue(Dish.class);
                dish.setDishId(dataSnapshot.getKey());
                dishes.add(dish);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onListFragmentInteraction(Bundle details, String action, boolean isFabClicked) {
        Intent intent = new Intent(this, DishComplaintDetail.class);
        intent.putExtra("dishId", details.getString("dishId"));

        startActivity(intent);
    }
}
