package com.example.lenovo.eats.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lenovo.eats.R;;

public class PiedPiperPurchaseAnItemActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_ppp;
    EditText et_quantity;
    Spinner  sp_spinner;
    EditText et_time;
    EditText et_date;
    Button   btn_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_purchase_an_item);


        et_name = findViewById( R.id.et_name );
        et_ppp = findViewById( R.id.et_ppp );
        et_quantity = findViewById( R.id.et_quantity );
        sp_spinner = findViewById( R.id.sp_spinner );
        et_time = findViewById( R.id.et_time );
        et_date = findViewById( R.id.et_date );
        btn_done = findViewById(R.id.btn_done );


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
