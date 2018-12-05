package com.example.lenovo.eats.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.eats.ClassModel.Inventory;
import com.example.lenovo.eats.ClassModel.UserOrder;
import com.example.lenovo.eats.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TrioOrderManagementActivity extends AppCompatActivity {
    public static UserOrder userOrder = new UserOrder();
    public static ArrayList<Inventory> inventoryArrayList = new ArrayList<>();

    private Context context;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, inventoryReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        context = getApplicationContext();
        /*FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:99688399269:android:94de520745e5b799")
                .setApiKey("AIzaSyCkU_3w29AyCW01z2D3cw8vfPULmOOfZ5E")
                .setDatabaseUrl("https://admin-module-ce0b7.firebaseio.com/").build();
        FirebaseApp.initializeApp(context, options, "admin-module-ce0b7");

        firebaseDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance("admin-module-ce0b7"));
        firebaseDatabase.setPersistenceEnabled(true);*/
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        inventoryReference = databaseReference.child("Inventory");

        inventoryReference.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                Inventory inventoryItem = dataSnapshot.getValue(Inventory.class);

                inventoryArrayList.add(inventoryItem);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                String key = dataSnapshot.getKey();

                for(int i = 0, size = inventoryArrayList.size(); i < size; i++)
                {
                    if(inventoryArrayList.get(i).getId().equals(key))
                    {
                        inventoryArrayList.get(i).setId(dataSnapshot.getKey());
                        inventoryArrayList.get(i).setAvailable_qty(Integer.valueOf(dataSnapshot.child("available_qty") + ""));
                        inventoryArrayList.get(i).setName(dataSnapshot.child("name") + "");
                        inventoryArrayList.get(i).setPpp(Float.valueOf(dataSnapshot.child("ppp") + ""));
                        inventoryArrayList.get(i).setQty_unit(dataSnapshot.child("qty_unit") + "");
                        inventoryArrayList.get(i).setReserved_qty(Integer.valueOf(dataSnapshot.child("reserved_qty") + ""));
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {
                String key = dataSnapshot.getKey();

                for(int i = 0, size = inventoryArrayList.size(); i < size; i++)
                {
                    if(inventoryArrayList.get(i).getId().equals(key))
                    {
                        inventoryArrayList.remove(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        Button continue_button = findViewById(R.id.continueButton);
        continue_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                //databaseReference.getRoot().push().setValue("Test");

                Intent i = new Intent (getApplicationContext(), TrioMenuDisplayActivity.class);
                startActivity(i);
            }
        });
    }
}
