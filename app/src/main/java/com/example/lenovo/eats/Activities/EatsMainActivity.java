package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.eats.R;;

public class EatsMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("EATS");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eats_activity_main);

    }

    public void onHeadChefClick(View view)
    {
        startActivity(new Intent(EatsMainActivity.this, DineinChefInterface.class));
    }

    public void onAdminClick(View view)
    {
        startActivity(new Intent(EatsMainActivity.this, PiedPiperMainMenuActivity.class));
    }

    public void onCustomerClick(View view)
    {
        startActivity(new Intent(EatsMainActivity.this, TrioOrderManagementActivity.class));
    }

    public void onHallManagerClick(View view)
    {
        startActivity(new Intent(EatsMainActivity.this, HallLoginActivity.class));
    }

    public void chefComplaintClick(View view)
    {
        Intent intent = new Intent(this,OrderItems.class);
        intent.putExtra("orderId","1");
        intent.putExtra("type","chef");
        startActivity(intent);
    }

}
