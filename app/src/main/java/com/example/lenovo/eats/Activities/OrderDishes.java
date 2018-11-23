package com.example.lenovo.eats.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.eats.Adapters.DishRecyclerViewAdapter;
import com.example.lenovo.eats.Interfaces.OnListFragmentInteractionListener;
import com.example.lenovo.eats.R;

public class OrderDishes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DishRecyclerViewAdapter adapter;
    OnListFragmentInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dishes);
    }
}
