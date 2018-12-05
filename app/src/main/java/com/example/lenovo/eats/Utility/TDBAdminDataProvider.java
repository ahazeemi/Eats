package com.example.lenovo.eats.Utility;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.lenovo.eats.ClassModel.TDBEmployee;
import com.example.lenovo.eats.ClassModel.TDBItem;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class TDBAdminDataProvider
{
    private static TDBAdminDataProvider instance;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, purchaseLogRef, salaryRef, employeeRef;
    private Context context;
    private final ArrayList<TDBEmployee> salaries = new ArrayList<>();
    private final ArrayList<TDBItem> purchaseLog = new ArrayList<>();
    private final ArrayList<Integer> employeeIDs = new ArrayList<>();

    private TDBAdminDataProvider()
    {
    }

    private TDBAdminDataProvider(final Context context)
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:99688399269:android:94de520745e5b799")
                .setApiKey("AIzaSyCkU_3w29AyCW01z2D3cw8vfPULmOOfZ5E")
                .setDatabaseUrl("https://admin-module-ce0b7.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(context, options, "AdminDB");

        firebaseDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance("AdminDB"));
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        purchaseLogRef = databaseReference.child("PurchaseLog");
        salaryRef = databaseReference.child("SalaryRecord");
        employeeRef = databaseReference.child("Employee");

        employeeRef.orderByKey().addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    TDBEmployee tempEmployee = new TDBEmployee(postSnapshot.child("name").getValue() + "",
                                                               postSnapshot.getKey() + "",
                                                               Integer.parseInt("0" + (postSnapshot.child("salary").getValue() == null? "": postSnapshot.child("salary").getValue())));
                    salaries.add(tempEmployee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        final DateTime now = DateTime.now();
        String monthYear = now.monthOfYear().getAsText() + now.getYear();
        salaryRef = salaryRef.child(monthYear);

        salaryRef.orderByKey().addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    int id = Integer.parseInt(postSnapshot.getKey() + "");

                    DateTime timestamp = new DateTime(Long.valueOf(postSnapshot.getValue() + "000"));

//                    if (now.dayOfMonth() == timestamp.dayOfMonth())
                    {
                        employeeIDs.add(id);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        purchaseLogRef.orderByKey().addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    DateTime timestamp = new DateTime(Long.valueOf(postSnapshot.child("timestamp").getValue() + "000"));

//                    if (now.isAfter(timestamp) && timestamp.isAfter(now.minusDays(1)))
                    {
                        int quantity = Integer.parseInt(postSnapshot.child("quantity").getValue() + "");
                        Double price, unitPrice = Double.parseDouble(postSnapshot.child("ppp").getValue() + "");
                        price = unitPrice * quantity;

                        TDBItem tempItem = new TDBItem(postSnapshot.child("name").getValue() + "", price, quantity + "", unitPrice);
//                        Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
                        purchaseLog.add(tempItem);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    public ArrayList<TDBEmployee> getSalaries()
    {
        for (int i = 0; i < salaries.size(); i++)
        {
            if (!employeeIDs.contains(salaries.get(i).getId()))
            {
                salaries.remove(i);
                i--;
            }
        }

        return salaries;
    }

    public ArrayList<TDBItem> getPurchaseLog()
    {
        return purchaseLog;
    }

    public static TDBAdminDataProvider getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new TDBAdminDataProvider(context);
        }

        instance.context = context;
        return instance;
    }
}
