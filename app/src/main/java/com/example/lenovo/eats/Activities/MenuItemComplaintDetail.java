package com.example.lenovo.eats.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.eats.ClassModel.MenuItemComplaint;
import com.example.lenovo.eats.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuItemComplaintDetail extends AppCompatActivity {

    private EditText menuItemQuantity;
    private EditText complaintDetail;
    private TextView menuItemName;
    private ImageView menuItemPic;
    private String menuItemId;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_complaint_detail);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        storage = FirebaseStorage.getInstance();

        menuItemId = getIntent().getStringExtra("menuItemId");

        menuItemQuantity = findViewById(R.id.menu_item_quantity);
        menuItemName = findViewById(R.id.menu_item_name);
        menuItemPic = findViewById(R.id.menu_item_pic);
        complaintDetail = findViewById(R.id.complaint_detail);

        menuItemName.setText(getIntent().getStringExtra("menuItemName"));

        StorageReference ref = storage.getReference().child("MenuItemPic/" + menuItemId + ".jpg");
        Glide.with(getApplicationContext()).load(ref).into(menuItemPic);

    }

    public void onAddItemClick(View view) {
        String itemQuantity = menuItemQuantity.getText().toString();
        if (TextUtils.isEmpty(itemQuantity))
        {
            Snackbar.make(findViewById(R.id.relativeLayout),
                    "Enter Quantity", Snackbar.LENGTH_SHORT).show();
            return;
        }

        String cause = "";
        String specialInstruction = complaintDetail.getText().toString();
        Integer quantity = Integer.parseInt(menuItemQuantity.getText().toString());

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

        MenuItemComplaint complaint = new MenuItemComplaint(causes,cause,quantity,specialInstruction);
        Intent intent = new Intent();
        intent.putExtra("menuItem",complaint);
        setResult(RESULT_OK,intent);

        finish();
        /*Toast.makeText(this, cause, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, specialInstruction, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, itemQuantity, Toast.LENGTH_SHORT).show();*/


    }
}
