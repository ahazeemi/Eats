package com.example.lenovo.eats.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lenovo.eats.Adapters.MenuItemsAdapter;
import com.example.lenovo.eats.ClassModel.MenuItems;
import com.example.lenovo.eats.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class TrioMenuDisplayActivity extends AppCompatActivity {

    private ListView listView;
    private MenuItemsAdapter mAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, adminReference;
    private Context context;
    private static boolean isCalled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display);

     //   setfirebase();
        displayMenu();


        String name = String.valueOf(" ");
        Log.d("name test", name);
        Button place_order_button = (Button) findViewById(R.id.placeOrderButton);
        place_order_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TrioOrderConfirmationActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //setfirebase();
        //displayMenu();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!isCalled) {
            isCalled = true;
//            setfirebase();
//            displayMenu();
        }
    }


    private void displayMenu() {

        listView = findViewById(R.id.menulist);
        final ArrayList<MenuItems> MenuList = new ArrayList<>();



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        adminReference = databaseReference.child("MenuItem");

        final AtomicBoolean check = new AtomicBoolean(false);

        Query reportQuery = adminReference.orderByKey();


        reportQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    {
                        // String name = String.valueOf(postSnapshot.child("name").getValue());
                        //  Log.d("name test",name);
                        ArrayList<String> ing_id = new ArrayList<>();
                        ArrayList<String> ing_quantity = new ArrayList<>();

                        for (DataSnapshot dataSnapshot1 : postSnapshot.child("ingredients").getChildren()) {
                            ing_id.add(String.valueOf(dataSnapshot1.getKey()));
                            ing_quantity.add(String.valueOf(dataSnapshot1.getValue()));

                            Log.d("ingredient 1 name test", String.valueOf(postSnapshot.child("ingredients").child(dataSnapshot1.getKey()).getKey()));
                            Log.d("ingredient 1 value test", String.valueOf(postSnapshot.child("ingredients").child(dataSnapshot1.getKey()).getValue()));
                        }


                        MenuItems tempItem = new MenuItems(postSnapshot.getKey(), R.drawable.chicken_karahi, String.valueOf(postSnapshot.child("name").getValue()), String.valueOf(postSnapshot.child("sale_price").getValue()), String.valueOf(postSnapshot.child("preparation_time").getValue()), ing_id, ing_quantity);
                        MenuList.add(tempItem);
                    }
                }

                mAdapter.notifyDataSetChanged();
                check.set(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MenuList.add(new MenuItems(R.drawable.pasta, "Pasta", "Rs: 200", ""));
        //MenuList.add(new MenuItems(R.drawable.fries, "Cheese Fries", "Rs: 150", ""));
        //MenuList.add(new MenuItems(R.drawable.chowmein, "Chowmein", "Rs: 300", ""));
        //MenuList.add(new MenuItems(R.drawable.cb, "ChickenBurger", "Rs: 200", ""));
        //MenuList.add(new MenuItems(R.drawable.beef, "Beef Karahi", "Rs: 300", ""));
        //MenuList.add(new MenuItems(R.drawable.beef, "Beef Karahi", "Rs: 300", ""));
        //MenuList.add(new MenuItems(R.drawable.pasta, "Pasta", "Rs: 200", ""));
        //MenuList.add(new MenuItems(R.drawable.fries, "Cheese Fries", "Rs: 150", ""));
        //MenuList.add(new MenuItems(R.drawable.chowmein, "Chowmein", "Rs: 300", ""));
        //MenuList.add(new MenuItems(R.drawable.beef, "Beef Karahi", "Rs: 200", ""));

        mAdapter = new MenuItemsAdapter(this, MenuList);
        listView.setAdapter(mAdapter);
    }

    private void setfirebase() {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:99688399269:android:94de520745e5b799")
                .setApiKey("AIzaSyCkU_3w29AyCW01z2D3cw8vfPULmOOfZ5E")
                .setDatabaseUrl("https://admin-module-ce0b7.firebaseio.com/").build();
        FirebaseApp.initializeApp(getApplicationContext(), options, "admin-module-ce0b7");
    }
}
