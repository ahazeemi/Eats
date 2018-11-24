package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lenovo.eats.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onComplaintClick(View view)
    {
        Intent intent = new Intent(this,OrderItems.class);
        intent.putExtra("orderId","orderid");
        startActivity(intent);
    }
}
