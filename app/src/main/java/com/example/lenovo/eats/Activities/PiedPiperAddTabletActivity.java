package com.example.lenovo.eats.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.lenovo.eats.ClassModel.PiedPiperTablet;
import com.example.lenovo.eats.R;;

import java.util.ArrayList;
import java.util.List;

public class PiedPiperAddTabletActivity extends AppCompatActivity {

    EditText et_tablet_no;
    Spinner sp_table_no;

    Button btn_add;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String TAG = "Test!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_add_tablet);


        et_tablet_no = findViewById( R.id.et_tablet_no );
        sp_table_no = findViewById( R.id.sp_table_no );
        btn_add = findViewById( R.id.btn_add );

        initializeTableSpinner();


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    int tablet_no = Integer.parseInt(  et_tablet_no.getText().toString() );
                    int table_no  = Integer.parseInt(  sp_table_no.getSelectedItem().toString() );

                    PiedPiperTablet new_tab = new PiedPiperTablet();

                    new_tab.assigned_to = table_no;
                    new_tab.tablet_no = tablet_no;

                    final DatabaseReference ref = database.getReference("Tablet");

                    ref.push().setValue( new_tab );

                    changeTableTabletNumber( table_no, tablet_no );


                    Toast.makeText(PiedPiperAddTabletActivity.this, "Tablet added successfully!", Toast.LENGTH_LONG).show() ;

                    refreshFields();
                }
                catch ( Exception e )
                {
                    Toast.makeText(PiedPiperAddTabletActivity.this, "Check entered information!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void refreshFields() {
        et_tablet_no.setText( "" );
        sp_table_no.setSelection( 0 );
    }

    private void initializeTableSpinner() {
        final List<String> table_nos = new ArrayList<>();
        final DatabaseReference ref = database.getReference("DiningTable");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                table_nos.add( "None" );

                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    String table_no = data.child( "table_no" ).getValue().toString();
                    table_nos.add( table_no );
                }

                ArrayAdapter<String> table_nos_adapter = new ArrayAdapter<String>(PiedPiperAddTabletActivity.this, android.R.layout.simple_spinner_item, table_nos );
                table_nos_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                sp_table_no.setAdapter(table_nos_adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });
    }

    private void changeTableTabletNumber(final int table_no, final int tablet_no )
    {
        final DatabaseReference ref = database.getReference("DiningTable");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if ( Integer.parseInt( data.child( "table_no" ).getValue().toString() ) == table_no )
                    {
                        String key = data.getKey();

                        ref.child( key ).child( "tablet_assigned" ).setValue( tablet_no );

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
