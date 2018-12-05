package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.eats.R;;


public class PiedPiperMainMenuActivity extends AppCompatActivity {

    Button btn_add_inventory ;
    Button btn_add_menu_item ;
    Button btn_add_employee ;
    Button btn_add_dining_table ;
    Button btn_assign_tablet_to_table ;
    Button btn_purchase_an_item ;
    Button btn_add_tablet, viewReportsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_main_menu);

	    viewReportsButton = findViewById(R.id.viewReportsButton);
        btn_add_inventory    = findViewById( R.id.btn_add_inventory );
        btn_add_menu_item    = findViewById( R.id.btn_add_menu_item );
        btn_add_employee     = findViewById( R.id.btn_add_employee );
        btn_add_dining_table = findViewById( R.id.btn_add_dining_table );
        btn_assign_tablet_to_table = findViewById( R.id.btn_assign_tablet_to_table );
        btn_add_tablet       = findViewById( R.id.btn_add_tablet );
//        btn_purchase_an_item = findViewById( R.id.btn_purchase_an_item );

	    viewReportsButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    startActivity(new Intent(PiedPiperMainMenuActivity.this, TDBReportingMainActivity.class));
		    }
	    });

        btn_add_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PiedPiperMainMenuActivity.this , PiedPiperAddInventoryActivity.class );
                startActivity( intent );
            }
        });

        btn_add_menu_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PiedPiperMainMenuActivity.this , PiedPiperAddMenuItemActivity.class );
                startActivity( intent );
            }
        });

        btn_add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PiedPiperMainMenuActivity.this , PiedPiperAddEmployeeActivity.class );
                startActivity( intent );
            }
        });

        btn_add_dining_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PiedPiperMainMenuActivity.this , PiedPiperAddDiningTableActivity.class );
                startActivity( intent );
            }
        });

        btn_assign_tablet_to_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PiedPiperMainMenuActivity.this , PiedPiperAssignTabletToTableActivity.class );
                startActivity( intent );
            }
        });

        btn_add_tablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PiedPiperMainMenuActivity.this , PiedPiperAddTabletActivity.class );
                startActivity( intent );
            }
        });
    }
}
