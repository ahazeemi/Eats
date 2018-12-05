package com.example.lenovo.eats.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.lenovo.eats.ClassModel.PiedPiperDiningTable;
import com.example.lenovo.eats.R;;

public class PiedPiperAddDiningTableActivity extends AppCompatActivity {

    EditText et_table_no;
    EditText et_seating_capacity;
    Button btn_add;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String TAG = "Test!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_add_dining_table);

        et_table_no = findViewById( R.id.et_table_no );
        et_seating_capacity = findViewById( R.id.et_seating_capacity );
        btn_add = findViewById( R.id.btn_add );


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    final int table_no = Integer.parseInt(et_table_no.getText().toString());
                    final int seating_capacity = Integer.parseInt(et_seating_capacity.getText().toString());


                    final DatabaseReference ref_check_table_no = database.getReference("DiningTable");

                    ref_check_table_no.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean is_found = false;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                if ( !data.child( "table_no" ).getValue().toString().matches("") && Integer.parseInt( data.child( "table_no" ).getValue().toString() ) == table_no )
                                {
                                    is_found = true;
                                    break;
                                }
                            }

                            if ( !is_found )
                            {
                                PiedPiperDiningTable new_table = new PiedPiperDiningTable();

                                new_table.table_no = table_no;
                                new_table.seating_capacity = seating_capacity;



                                final DatabaseReference ref = database.getReference("DiningTable");

                                ref.push().setValue( new_table );

                                Toast.makeText(PiedPiperAddDiningTableActivity.this, "Dining table added successfully!", Toast.LENGTH_LONG).show();

                                refreshFields();
                            }
                            else
                            {
                                Toast.makeText(PiedPiperAddDiningTableActivity.this, "Dining table with this num is already present!", Toast.LENGTH_LONG).show();

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
                    Toast.makeText(PiedPiperAddDiningTableActivity.this, "Something went wrong. Please check entered data!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void refreshFields() {

        et_table_no.setText("");
        et_seating_capacity.setText("");

    }
}
