package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lenovo.eats.ClassModel.ChefIngredientsView;
import com.example.lenovo.eats.Interfaces.OnListFragmentInteractionListener;
import com.example.lenovo.eats.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hamza on 24-Nov-18.
 */

public class ChefIngredientsAdapter extends RecyclerView.Adapter<ChefIngredientsAdapter.IngredientsViewHolder>  {

    private final ArrayList<ChefIngredientsView> mValues;
    Context context;
    OnListFragmentInteractionListener mListener;
    HashMap<String,Integer>ingredientsMap;

    public ChefIngredientsAdapter(ArrayList<ChefIngredientsView> mValues, Context context, OnListFragmentInteractionListener mListener, HashMap<String, Integer> ingredientsMap) {
        this.mValues = mValues;
        this.context = context;
        this.mListener = mListener;
        this.ingredientsMap = ingredientsMap;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient,parent,false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  IngredientsViewHolder iHolder, final int position) {
        final IngredientsViewHolder holder=iHolder;
        holder.ingredientName.setText(mValues.get(position).getName());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredientsMap.containsKey(mValues.get(position).getIngredientId()))
                {
                    int count=ingredientsMap.get(mValues.get(position).getIngredientId());
                    holder.quantity.setText(String.valueOf(count+1));
                    ingredientsMap.put(mValues.get(position).getIngredientId(),count+1);

                }
                else
                {
                    holder.quantity.setText(String.valueOf(1));
                    ingredientsMap.put(mValues.get(position).getIngredientId(),1);
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredientsMap.containsKey(mValues.get(position).getIngredientId()))
                {
                    int count=ingredientsMap.get(mValues.get(position).getIngredientId());
                    holder.quantity.setText(String.valueOf(count-1));
                    if(count-1>0) {
                        ingredientsMap.put(mValues.get(position).getIngredientId(), count - 1);
                    }
                    else
                    {
                        ingredientsMap.remove(mValues.get(position).getIngredientId());
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView ingredientName;
        public final ImageButton add;
        public final ImageButton minus;
        public final TextView quantity;
        public IngredientsViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            ingredientName=itemView.findViewById(R.id.ingredient_name);
            add=itemView.findViewById(R.id.ingredients_add);
            minus=itemView.findViewById(R.id.ingredients_minus);
            quantity=itemView.findViewById(R.id.ingredients_quantity);
        }
    }
}
