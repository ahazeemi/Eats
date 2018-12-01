package com.example.lenovo.eats.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.eats.ClassModel.Ingredient;
import com.example.lenovo.eats.ClassModel.MenuItem;
import com.example.lenovo.eats.ClassModel.MenuItemComplaint;
import com.example.lenovo.eats.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuItemComplaintDetail extends AppCompatActivity {

    private EditText menuItemQuantity;
    private EditText complaintDetail;
    private TextView menuItemName;
    private ImageView menuItemPic;
    private String menuItemId;
    private FirebaseStorage storage;
    FirebaseDatabase firebaseDatabase;
    HashMap <String,Ingredient> ingredientsMap;
    HashMap<String,Integer> ingredientsOfMenuItem;
    Float salePrice;
    boolean quantityDisabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_complaint_detail);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        storage = FirebaseStorage.getInstance();

        ingredientsMap = new HashMap<>();
        ingredientsMap = (HashMap<String, Ingredient>)getIntent().getSerializableExtra("ingredientsMap");
        firebaseDatabase=FirebaseDatabase.getInstance();
        menuItemId = getIntent().getStringExtra("menuItemId");

        menuItemQuantity = findViewById(R.id.menu_item_quantity);
        menuItemName = findViewById(R.id.menu_item_name);
        menuItemPic = findViewById(R.id.menu_item_pic);
        complaintDetail = findViewById(R.id.complaint_detail);

        menuItemName.setText(getIntent().getStringExtra("menuItemName"));

        MenuItemComplaint menuItem =  getIntent().getExtras().getParcelable("menuItemObj");
        if(menuItem!=null)
        {
            // restoring views
            ((CheckBox)findViewById(R.id.taste)).setChecked(menuItem.getCauses().containsKey("Bad Taste"));
            ((CheckBox)findViewById(R.id.cleanliness)).setChecked(menuItem.getCauses().containsKey("Unclean"));
            ((CheckBox)findViewById(R.id.overcooked)).setChecked(menuItem.getCauses().containsKey("Overcooked"));
            ((CheckBox)findViewById(R.id.undercooked)).setChecked(menuItem.getCauses().containsKey("Undercooked"));

            menuItemQuantity.setText(Integer.toString(menuItem.getQuantity_reordered()));
            complaintDetail.setText(menuItem.getSpecial_instruction());
            disableEditText(menuItemQuantity);
            quantityDisabled = true;
        }

        StorageReference ref = storage.getReference().child("MenuItemPic/" + menuItemId + ".jpg");
        Glide.with(getApplicationContext()).load(ref).into(menuItemPic);

        fetchMenuItemDetails(menuItemId);

    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        //editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    void fetchMenuItemDetails(String menuItemId)
    {
        firebaseDatabase.getReference().child("MenuItem").child(menuItemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final MenuItem menuItem= dataSnapshot.getValue(MenuItem.class);
                salePrice = menuItem.getSale_price();
                ingredientsOfMenuItem = menuItem.getIngredients();
                for(final String key: ingredientsOfMenuItem.keySet())
                {
                    if(ingredientsMap.containsKey(key))
                        continue;

                    firebaseDatabase.getReference("Inventory").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Ingredient ingredient=dataSnapshot.getValue(Ingredient.class);
                            ingredientsMap.put(dataSnapshot.getKey(),ingredient);
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

    public void onAddItemClick(View view) {

        String itemQuantity = menuItemQuantity.getText().toString();
        if (TextUtils.isEmpty(itemQuantity) || Integer.parseInt(itemQuantity)==0)
        {
            Snackbar.make(findViewById(R.id.relativeLayout),
                    "Enter Quantity", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Integer quantity = Integer.parseInt(itemQuantity);
        if(!quantityDisabled)
        {
            if(ingredientsMap.size()>0)
            {
                for(Map.Entry<String,Integer> entry:ingredientsOfMenuItem.entrySet()) {
                    int available = ingredientsMap.get(entry.getKey()).getAvailable_qty();
                    int reserve = ingredientsMap.get(entry.getKey()).getReserved_qty();
                    if (available + reserve < (entry.getValue()*quantity)) {
                        DisplayAlert("The required amount of "+ ingredientsMap.get(entry.getKey()).getName()+" is not available in the inventory.","Insufficient Quantity");
                        return;
                    }
                }

                for(Map.Entry<String,Integer> entry:ingredientsOfMenuItem.entrySet())
                {
                    int available = ingredientsMap.get(entry.getKey()).getAvailable_qty();
                    int reserve = ingredientsMap.get(entry.getKey()).getReserved_qty();
                    int requiredQuantity = entry.getValue()*quantity;
                    if(available+reserve>=requiredQuantity)
                    {
                        if(requiredQuantity>available)
                        {
                            reserve=reserve-(requiredQuantity-available);
                            available=0;
                        }
                        else
                        {
                            available=available-requiredQuantity;
                        }

                        ingredientsMap.get(entry.getKey()).setAvailable_qty(available);
                        ingredientsMap.get(entry.getKey()).setReserved_qty(reserve);

                        //firebaseDatabase.getReference("Ingredient").child(entry.getKey()).child("reserved_qty").setValue(reserve);
                        //firebaseDatabase.getReference("Ingredient").child(entry.getKey()).child("available_qty").setValue(available);

                    }
                }
            }
        }

        String cause = "";
        String specialInstruction = complaintDetail.getText().toString();

        List<CheckBox> items = new ArrayList<CheckBox>();
        items.add((CheckBox)findViewById(R.id.taste));
        items.add((CheckBox)findViewById(R.id.cleanliness));
        items.add((CheckBox)findViewById(R.id.undercooked));
        items.add((CheckBox)findViewById(R.id.overcooked));

        HashMap<String,Boolean> causes = new HashMap<>();

        for (CheckBox item : items){
            if(item.isChecked()) {
                String temp = item.getText().toString();
                causes.put(temp,true);
                cause = cause.concat(temp);
                cause = cause.concat(",");
            }
        }

        MenuItemComplaint complaint = new MenuItemComplaint(causes,ingredientsOfMenuItem,salePrice,cause,quantity,specialInstruction);
        Intent intent = new Intent();
        intent.putExtra("menuItem",complaint);
        intent.putExtra("ingredientsMap",ingredientsMap);
        setResult(RESULT_OK,intent);

        finish();
        /*Toast.makeText(this, cause, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, specialInstruction, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, itemQuantity, Toast.LENGTH_SHORT).show();*/


    }
}
