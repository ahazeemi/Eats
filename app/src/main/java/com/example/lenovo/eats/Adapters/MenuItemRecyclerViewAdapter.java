package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.eats.ClassModel.MenuItemView;
import com.example.lenovo.eats.Interfaces.OnListFragmentInteractionListener;
import com.example.lenovo.eats.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


/**
 * Created by lenovo on 11/23/2018.
 */

public class MenuItemRecyclerViewAdapter extends RecyclerView.Adapter<MenuItemRecyclerViewAdapter.ViewHolder> {

    private final List<MenuItemView> mValues;
    private OnListFragmentInteractionListener mListener;
    private FirebaseStorage storage;
    private Context c;


    public MenuItemRecyclerViewAdapter(List<MenuItemView> items, Context c, OnListFragmentInteractionListener mListener) {
        mValues = items;
        this.mListener = mListener;
        this.c=c;
        storage = FirebaseStorage.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).getName());
        holder.mQuantity.setText(Integer.toString(mValues.get(position).getQuantityOrdered()));

        if(holder.mItem.isAddedToOrder())
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#c9ff9e80"));
        }
        else
        {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("menuItemId", mValues.get(position).getMenuItemId());
                bundle.putString("menuItemName", mValues.get(position).getName());
                bundle.putInt("menuItemPosition", position);
                mListener.onListFragmentInteraction(bundle, " menuItemDetail", true);
            }
        });


        StorageReference ref = storage.getReference().child("MenuItemPic/" + mValues.get(position).getMenuItemId() + ".jpg");
        Glide.with(c.getApplicationContext()).load(ref).into(holder.mPicture);


    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder

    {
        final View mView;
        final ImageView mPicture;
        final TextView mName;
        final TextView mQuantity;
        final CardView cardView;
        MenuItemView mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPicture = view.findViewById(R.id.menu_item_pic);
            mName = view.findViewById(R.id.item_name);
            mQuantity = view.findViewById(R.id.item_quantity);
            cardView = view.findViewById(R.id.item_menu_item_parent);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }


    }
}
