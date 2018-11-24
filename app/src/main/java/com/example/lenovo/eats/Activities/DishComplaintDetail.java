package com.example.lenovo.eats.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.eats.Adapters.ChefIngredientsAdapter;
import com.example.lenovo.eats.Adapters.DishRecyclerViewAdapter;
import com.example.lenovo.eats.ClassModel.ChefIngredientsView;
import com.example.lenovo.eats.Interfaces.OnListFragmentInteractionListener;
import com.example.lenovo.eats.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DishComplaintDetail extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_complaint_detail);
    }


}
