package com.example.lenovo.eats.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.lenovo.eats.Adapters.ChefIngredientsAdapter;
import com.example.lenovo.eats.ClassModel.ChefIngredientsView;
import com.example.lenovo.eats.ClassModel.ChefMiniOrder;
import com.example.lenovo.eats.ClassModel.Ingredient;
import com.example.lenovo.eats.ClassModel.MenuItem;
import com.example.lenovo.eats.Interfaces.OnListFragmentInteractionListener;
import com.example.lenovo.eats.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChefComplaiantDetail extends AppCompatActivity implements OnListFragmentInteractionListener {
    RecyclerView recyclerView;
    ChefIngredientsAdapter adapter;
    ArrayList<ChefIngredientsView> ingredients;
    HashMap<String,Integer> ingredientsMap;
    String orderId;
    String menuItemId;
    FirebaseDatabase firebaseDatabase;
    HashMap<String,Ingredient>metaIngredient;
    String time;
    EditText timeInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_complaiant_detail);
        metaIngredient=new HashMap<>();
        ingredientsMap=new HashMap<>();
        ingredients=new ArrayList<>();
        recyclerView = findViewById(R.id.ingredientsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter = new ChefIngredientsAdapter(ingredients,this,this,ingredientsMap));

        orderId = getIntent().getStringExtra("orderId");
        menuItemId=getIntent().getStringExtra("menuItemId");
        firebaseDatabase=FirebaseDatabase.getInstance();
        timeInput=findViewById(R.id.TimeInput);
        UpdateUI();

    }

    void UpdateUI()
    {
        firebaseDatabase.getReference().child("MenuItem").child(menuItemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 MenuItem menuItem= dataSnapshot.getValue(MenuItem.class);
                 for(String key:menuItem.getIngredients().keySet())
                 {
                     firebaseDatabase.getReference("Inventory").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             Ingredient ingredient=dataSnapshot.getValue(Ingredient.class);
                             metaIngredient.put(dataSnapshot.getKey(),ingredient);
                             ingredients.add(new ChefIngredientsView(ingredient.getName(),dataSnapshot.getKey()));
                             adapter.notifyDataSetChanged();
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
                 }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void onDoneClick(View view)
    {
        time=timeInput.getText().toString();
        if(time.equals(""))
        {
            DisplayAlert("Please Enter the Time","Time not Entered");
            return;
        }
        if(ingredientsMap.size()>0)
        {
            float priceSum=0;
            for(Map.Entry<String,Integer> entry:ingredientsMap.entrySet()) {
                int available = metaIngredient.get(entry.getKey()).getAvailable_qty();
                int reserve = metaIngredient.get(entry.getKey()).getReserved_qty();
                priceSum+=(entry.getValue()*metaIngredient.get(entry.getKey()).getPpp());
                if (available + reserve < entry.getValue()) {
                    DisplayAlert("Sorry the required amount of "+metaIngredient.get(entry.getKey()).getName()+"is not available","Required Amount not available");
                    return;
                }
            }

            for(Map.Entry<String,Integer> entry:ingredientsMap.entrySet())
            {
                int available=metaIngredient.get(entry.getKey()).getAvailable_qty();
                int reserve=metaIngredient.get(entry.getKey()).getReserved_qty();
                if(available+reserve>=entry.getValue())
                {
                    if(entry.getValue()>available)
                    {
                        reserve=reserve-(entry.getValue()-available);
                        available=0;
                    }
                    else
                    {
                        available=available-entry.getValue();
                    }

                    firebaseDatabase.getReference("Inventory").child(entry.getKey()).child("reserve_qty").setValue(reserve);
                    firebaseDatabase.getReference("Inventory").child(entry.getKey()).child("available_qty").setValue(available);

                }
            }


            ChefMiniOrder chefMiniOrder=new ChefMiniOrder(orderId,menuItemId,System.currentTimeMillis(),ingredientsMap,priceSum,Integer.parseInt(time));
            firebaseDatabase.getReference("ChefMiniOrder").push().setValue(chefMiniOrder);
            Intent intent=new Intent();
            intent.putExtra("menuItemId",menuItemId);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void onListFragmentInteraction(Bundle details, String action, boolean isFabClicked) {

    }

    void DisplayAlert(String message, String title)
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }


}
