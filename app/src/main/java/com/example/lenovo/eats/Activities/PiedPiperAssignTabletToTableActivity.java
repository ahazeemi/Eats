package com.example.lenovo.eats.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.lenovo.eats.R;;

import java.util.ArrayList;
import java.util.List;

public class PiedPiperAssignTabletToTableActivity extends AppCompatActivity {

    Spinner sp_table_no;
    Spinner sp_tablet_no;

    Button   et_assign;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String TAG = "Test!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_assign_tablet_to_table);

        sp_table_no = findViewById( R.id.sp_table_no );
        sp_tablet_no = findViewById( R.id.sp_tablet_no );

        et_assign = findViewById( R.id.et_assign );

        initializeTableSpinner();

        initializeTabletSpinner();

        et_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String table_no = sp_table_no.getSelectedItem().toString();
                String tablet_no = sp_tablet_no.getSelectedItem().toString();

                try
                {
                    int table_no_int = Integer.parseInt( table_no );
                    int tablet_no_int = Integer.parseInt( tablet_no );


                    changeTableTabletNumber( table_no_int, tablet_no_int );
                    changeTabletTableNumber( table_no_int, tablet_no_int );

                    refreshFields();
                }
                catch (Exception e)
                {
                    Toast.makeText(PiedPiperAssignTabletToTableActivity.this, "Check entered information!", Toast.LENGTH_LONG).show();
                }

            }
        });

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

                ArrayAdapter<String> table_nos_adapter = new ArrayAdapter<String>(PiedPiperAssignTabletToTableActivity.this, android.R.layout.simple_spinner_item, table_nos);
                table_nos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_table_no.setAdapter(table_nos_adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void initializeTabletSpinner() {
        final List<String> tablet_nos = new ArrayList<>();
        final DatabaseReference ref = database.getReference("Tablet");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override


            public void onDataChange(DataSnapshot dataSnapshot) {
                tablet_nos.add( "None" );

                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    String tablet_no = data.child( "tablet_no" ).getValue().toString();
                    tablet_nos.add( tablet_no );

                }

                ArrayAdapter<String> tablet_nos_adapter = new ArrayAdapter<String>(PiedPiperAssignTabletToTableActivity.this, android.R.layout.simple_spinner_item, tablet_nos);
                tablet_nos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_tablet_no.setAdapter(tablet_nos_adapter);
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

                        Toast.makeText(PiedPiperAssignTabletToTableActivity.this, "Table's tablet num is changed!", Toast.LENGTH_LONG).show();
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

    private void changeTabletTableNumber(final int table_no, final int tablet_no )
    {
        final DatabaseReference ref = database.getReference("Tablet");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if ( Integer.parseInt( data.child( "tablet_no" ).getValue().toString() ) == tablet_no )
                    {
                        String key = data.getKey();

                        ref.child( key ).child( "assigned_to" ).setValue( table_no );

                        Toast.makeText(PiedPiperAssignTabletToTableActivity.this, "Tablet's table num is changed!", Toast.LENGTH_LONG).show();
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

    private void refreshFields()
    {
        sp_tablet_no.setSelection( 0 );
        sp_table_no.setSelection( 0 );
    }
}
