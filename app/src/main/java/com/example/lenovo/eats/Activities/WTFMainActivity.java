package com.example.lenovo.eats.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.eats.Adapters.WTFRVAdapter;
import com.example.lenovo.eats.ClassModel.WTFOrderItem;
import com.example.lenovo.eats.R;;

import java.util.ArrayList;

public class WTFMainActivity extends AppCompatActivity {

    public ArrayList<WTFOrderItem> order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wtf_activity_main);


        Bundle b=getIntent().getExtras();

        order = new ArrayList<WTFOrderItem>();

        WTFOrderItem item= new WTFOrderItem(1, "Biryani", 2);
        WTFOrderItem item2=new WTFOrderItem(2, "Chicken Roast", 1);
        WTFOrderItem item3=new WTFOrderItem(3, "Nuggets", 12);
        WTFOrderItem item4=new WTFOrderItem(4, "Mutton Karrahi", 2);
        WTFOrderItem item5=new WTFOrderItem(5, "Soup", 3);

        order.add(item);
        order.add(item2);
        order.add(item3);
        order.add(item4);
        order.add(item5);

        WTFRVAdapter adapter = new WTFRVAdapter(order, R.layout.wtf_row_layout, this);
        RecyclerView recyclerView = findViewById(R.id.Rcv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

}
