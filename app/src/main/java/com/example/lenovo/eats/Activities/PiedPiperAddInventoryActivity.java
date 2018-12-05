package com.example.lenovo.eats.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.lenovo.eats.ClassModel.PiedPiperInventory;
import com.example.lenovo.eats.ClassModel.PiedPiperPurchaseLog;
import com.example.lenovo.eats.R;;

import java.util.Date;

public class PiedPiperAddInventoryActivity extends AppCompatActivity {

    EditText et_available_quantity;
    EditText et_inv_item;
    EditText et_ppp;
    Spinner  sp_qty_unit;
    EditText et_reserved_quantity;
    Button   btn_add;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_add_inventory);

        et_available_quantity  = findViewById(R.id.et_available_quantity);
        et_inv_item            = findViewById(R.id.et_inv_item) ;
        et_ppp                 = findViewById(R.id.et_ppp);
        sp_qty_unit            = findViewById(R.id.sp_qty_unit);
        et_reserved_quantity   = findViewById(R.id.et_reserved_quantity);
        btn_add                = findViewById(R.id.btn_add);



        final DatabaseReference myRef = database.getReference("Inventory");

        btn_add.setOnClickListener(new View.OnClickListener() {
            public String TAG = "Test!";

            @Override
            public void onClick(View view) {
                try
                {
                    final int available_quantity  = Integer.parseInt(et_available_quantity.getText().toString());
                    final String inv_item_name    = et_inv_item.getText().toString();
                    final float ppp               = Float.parseFloat( et_ppp.getText().toString() );
                    final String quantity_unit    = sp_qty_unit.getSelectedItem().toString();
                    final int quantity_in_reserve = Integer.parseInt(et_reserved_quantity.getText().toString());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.

                            boolean is_inv_item_new = true;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                String db_inv_item_name = data.child("name").getValue().toString().toLowerCase();
                                String inv_item_name_lowercase = inv_item_name.toLowerCase();

                                if ( db_inv_item_name.equals( inv_item_name_lowercase ) )
                                {
                                    is_inv_item_new = false;

                                    //Update that inv item.
                                    final DatabaseReference inv_item_ref = database.getReference("Inventory").child( data.getKey() );

                                    int already_stored_available_quantity = Integer.parseInt(data.child( "available_qty" ).getValue().toString());
                                    int already_stored_reserved_quantity = Integer.parseInt(data.child( "reserved_qty" ).getValue().toString());

                                    inv_item_ref.child( "ppp" ).setValue( ppp );
                                    inv_item_ref.child( "available_qty" ).setValue( already_stored_available_quantity + available_quantity );
                                    inv_item_ref.child( "qty_unit" ).setValue( quantity_unit );
                                    inv_item_ref.child( "reserved_qty" ).setValue( already_stored_reserved_quantity + quantity_in_reserve );

                                    Toast.makeText(PiedPiperAddInventoryActivity.this, "Updated!", Toast.LENGTH_LONG).show();

                                    //Create purchaseLog
                                    createPurchaseLog( inv_item_name, ppp, available_quantity );

                                    refreshFields();

                                    break;
                                }
                            }

                            if ( is_inv_item_new )
                            {
                                PiedPiperInventory new_item = new PiedPiperInventory();

                                new_item.available_qty = available_quantity;
                                new_item.name = inv_item_name;
                                new_item.ppp = ppp;
                                new_item.qty_unit = quantity_unit;
                                new_item.reserved_qty = quantity_in_reserve;

                                myRef.push().setValue( new_item );

                                Toast.makeText(PiedPiperAddInventoryActivity.this, "New inventory item created!", Toast.LENGTH_LONG).show();


                                //Create purchase log

                                createPurchaseLog( inv_item_name, ppp, available_quantity );


                                refreshFields();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });

                }
                catch ( Exception e )
                {
                    Toast.makeText(PiedPiperAddInventoryActivity.this, "Something went wrong. Please check entered data!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void refreshFields()
    {
        et_available_quantity.setText("");
        et_inv_item.setText("");
        et_ppp.setText("");

        et_reserved_quantity.setText("");
    }

    private void createPurchaseLog( String _name, float _ppp, int _quantity_added )
    {
        PiedPiperPurchaseLog log_entry = new PiedPiperPurchaseLog();

        log_entry.name = _name;
        log_entry.ppp  = _ppp;
        log_entry.quantity = _quantity_added;
        log_entry.timestamp = new Date().getTime();;

        DatabaseReference reference = database.getReference("PurchaseLog");
        reference.push().setValue( log_entry );
    }
}
