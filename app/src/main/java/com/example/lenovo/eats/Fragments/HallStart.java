package com.example.lenovo.eats.Fragments;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lenovo.eats.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class HallStart extends Fragment {
    private RecyclerView mRecyclerView;
   // private HallContactAdapter mAdapter;
    private String queryy = "";
    private List<String>d1=new ArrayList<>();
    private List<String>userName=new ArrayList<>();
    private String cid;
    //List<HallContact> data = new ArrayList<>();
    private  String userId;
    FloatingActionButton fab;
    LinearLayout confirm;
    //  private CHATS.OnFragmentInteractionListener mListener;
    Button cancel, ok;
   // KamChalau chal;

    public HallStart(){
        //setHasOptionsMenu(true);
        //getData1();

        Log.e("testing contacts : ", "test partially successful");

    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        //setHasOptionsMenu(true);
        //FirebaseApp.initializeApp(this);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts1 , null, false);


        return view;
    }

    void add(String a)
    {
        d1.add(a);
       // setData();
    }

    public void getData1()
    {
        FirebaseDatabase.getInstance().getReference("Doctors").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String test;
                test=dataSnapshot.child("name").getValue(String.class);

                cid=dataSnapshot.getKey();
                if(!cid.equalsIgnoreCase(userId))
                {
                    add(test);
                }

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


        //  return obj;

    }



}
