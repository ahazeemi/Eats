package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lenovo.eats.R;

public class TrioWaitingTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_time);


    }

    public void onBillClick(View view)
    {
        Intent intent= new Intent(this,Bill_ShowOrderActivity.class);
        startActivity(intent);

    }
}
