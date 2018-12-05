package com.example.lenovo.eats.Utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.lenovo.eats.ClassModel.TDBMiniOrder;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class TDBMiniOrderProvider
{
    private static TDBMiniOrderProvider instance;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, miniOrderReference, customerMiniOrderReference;
    private Context context;
    private final ArrayList<TDBMiniOrder> miniOrders = new ArrayList<>();
    private final ArrayList<TDBMiniOrder> customerMiniOrders = new ArrayList<>();

    private TDBMiniOrderProvider() {
    }

    private TDBMiniOrderProvider(Context context)
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:322751219360:android:a25fe03e1a838382")
                .setApiKey("AIzaSyC-42UmYA0TOiyUgCuyBWDRUhyafDda7xY")
                .setDatabaseUrl("https://eats-24d11.firebaseio.com/").build();
        FirebaseApp.initializeApp(context, options, "MiniOrderDB");

        firebaseDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance("MiniOrderDB"));
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        miniOrderReference = databaseReference.child("MiniOrder");
        customerMiniOrderReference = databaseReference.child("CustomerMiniOrder");

        final AtomicBoolean check = new AtomicBoolean(false);

        Query reportQuery = miniOrderReference.orderByKey();
        reportQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    DateTime timestamp = new DateTime(Long.valueOf(postSnapshot.child("timestamp").getValue() + "000")), now = DateTime.now();

//                    if(now.isAfter(timestamp) && timestamp.isAfter(now.minusDays(1)))
                    {
                        TDBMiniOrder tempOrder = new TDBMiniOrder(postSnapshot.child("main_order_id").getValue() + "", Integer.parseInt(postSnapshot.child("price").getValue() + ""));
                        miniOrders.add(tempOrder);
                    }
                }
                check.set(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query customerQuery = miniOrderReference.orderByKey();
        customerQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    DateTime timestamp = new DateTime(Long.valueOf(postSnapshot.child("timestamp").getValue() + "000")), now = DateTime.now();

//                    if(now.isAfter(timestamp) && timestamp.isAfter(now.minusDays(1)))
                    {
                        TDBMiniOrder tempOrder = new TDBMiniOrder(postSnapshot.child("main_order_id").getValue() + "", Integer.parseInt(postSnapshot.child("price").getValue() + ""));
                        customerMiniOrders.add(tempOrder);
                    }
                }
                check.set(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//		while(!check.get()){
//			Log.d("app", "test");
//		}
    }

    public static TDBMiniOrderProvider getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new TDBMiniOrderProvider(context);
        }

        instance.context = context;
        return instance;
    }

    public void getOrders()
    {
        Toast.makeText(context, "Size: " + miniOrders.size(), Toast.LENGTH_SHORT).show();
    }

    public ArrayList<TDBMiniOrder> getMiniOrders()
    {
        return miniOrders;
    }

    public ArrayList<TDBMiniOrder> getCustomerMiniOrders()
    {
        return customerMiniOrders;
    }
}
