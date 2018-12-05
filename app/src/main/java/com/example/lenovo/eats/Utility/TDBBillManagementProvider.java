package com.example.lenovo.eats.Utility;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.lenovo.eats.ClassModel.TDBOrder;

import java.util.ArrayList;

public class TDBBillManagementProvider
{
    private static TDBBillManagementProvider instance;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, orderRef;
    private Context context;
    private final ArrayList<TDBOrder> orders = new ArrayList<>();

    private TDBBillManagementProvider() {}

    private TDBBillManagementProvider(Context context)
    {
        orders.add(new TDBOrder(1, 1, 3000));
        orders.add(new TDBOrder(2, 2, 4500));
        orders.add(new TDBOrder(3, 3, 5300));

//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("")
//                .setApiKey("")
//                .setDatabaseUrl("").build();
//
//        FirebaseApp.initializeApp(context, options, "BillManagementDB");
//        firebaseDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance("BillManagementDB"));
//
//        databaseReference = firebaseDatabase.getReference();
//        orderRef = databaseReference.child("Order");
//
//        orderRef.addChildEventListener(new ChildEventListener()
//        {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
//            {
//                // check if the order is for the current day
//
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
//            {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
//            {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
//            {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//
//            }
//        });
    }

    public static TDBBillManagementProvider getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new TDBBillManagementProvider(context);
        }

        instance.context = context;
        return instance;
    }

    public ArrayList<TDBOrder> getOrders()
    {
        return orders;
    }
}
