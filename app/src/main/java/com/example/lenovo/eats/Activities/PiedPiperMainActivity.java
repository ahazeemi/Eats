package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.eats.R;;

public class PiedPiperMainActivity extends AppCompatActivity {

    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_main);

        btn_login = findViewById( R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PiedPiperMainActivity.this, PiedPiperMainMenuActivity.class );

                startActivity( intent );
            }
        });

    }
}
