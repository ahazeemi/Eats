package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.eats.ClassModel.Dish;
import com.example.lenovo.eats.Interfaces.OnListFragmentInteractionListener;
import com.example.lenovo.eats.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


/**
 * Created by lenovo on 11/23/2018.
 */

public class DishRecyclerViewAdapter extends RecyclerView.Adapter<DishRecyclerViewAdapter.ViewHolder> {

    private final List<Dish> mValues;
    OnListFragmentInteractionListener mListener;
    FirebaseStorage storage;
    Context c;


    public DishRecyclerViewAdapter(List<Dish> items, List<String> ids,Context c, OnListFragmentInteractionListener mListener) {
        mValues = items;
        this.mListener = mListener;
        this.c=c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dish, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).getName());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("dishId", mValues.get(position).getDishId());
                mListener.onListFragmentInteraction(bundle, "dishDetail", true);
            }
        });


        StorageReference ref = storage.getReference().child("DishesPic/" + mValues.get(position).getDishId() + ".jpg");
        Glide.with(c.getApplicationContext()).load(ref).into(holder.mPicture);


    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder

    {
        public final View mView;
        public final ImageView mPicture;
        public final TextView mName;
        public final TextView mQuantity;
        public Dish mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPicture = view.findViewById(R.id.item_pic);
            mName = view.findViewById(R.id.item_name);
            mQuantity = view.findViewById(R.id.item_quantity);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }


    }
}
