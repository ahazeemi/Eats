package com.example.lenovo.eats.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.lenovo.eats.ClassModel.PiedPiperEmployee;
import com.example.lenovo.eats.R;;

import java.util.ArrayList;
import java.util.List;

public class PiedPiperAddEmployeeActivity extends AppCompatActivity
{
    Spinner sp_designation;
    EditText et_email;
    EditText et_name;
    EditText et_salary;
    EditText et_shift_start;
    EditText et_shift_end;
    EditText et_speciality;
    EditText et_username;
    Spinner sp_table_no;
    EditText et_password;

    Button btn_add;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String TAG = "Test!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_add_employee);

        sp_designation     = findViewById( R.id.sp_designation );
        et_email           = findViewById( R.id.et_email );
        et_name            = findViewById( R.id.et_name );
        et_salary          = findViewById( R.id.et_salary );
        et_shift_start     = findViewById( R.id.et_shift_start );
        et_shift_end       = findViewById( R.id.et_shift_end );
        et_speciality      = findViewById( R.id.et_speciality );
        et_username        = findViewById( R.id.et_username );
        sp_table_no        = findViewById( R.id.sp_table_no );
        et_password        = findViewById( R.id.et_password );
        et_password        = findViewById(R.id.et_password );

        btn_add = findViewById( R.id.btn_add );



        initializeTableSpinner();

        initializeDesignationSpinner();

        btn_add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                try
                {
                    String designation     = sp_designation.getSelectedItem().toString();
                    String email           = et_email.getText().toString();
                    String name            = et_name.getText().toString();
                    int salary             = Integer.parseInt( et_salary.getText().toString() );
                    String shift_start     = et_shift_start.getText().toString();
                    String shift_end       = et_shift_end.getText().toString();
                    String speciality      = et_speciality.getText().toString();
                    String username        = et_username.getText().toString();
                    String password        = et_password.getText().toString();
                    String table_no_str    = sp_table_no.getSelectedItem().toString();



                    //Default value
                    int table_no           = -1;
                    if ( !table_no_str.equals( "None" ) )
                    {
                        table_no     = Integer.parseInt( table_no_str );
                    }


                    PiedPiperEmployee new_employee = new PiedPiperEmployee();

                    if ( table_no != -1 )
                    {
                        new_employee.assigned_table = table_no;
                    }

                    new_employee.designation    = designation;
                    new_employee.email          = email;
                    new_employee.name           = name;
                    new_employee.password       = password;
                    new_employee.salary         = salary;
                    new_employee.shift_end      = shift_end;
                    new_employee.shift_start    = shift_start;
                    new_employee.speciality     = speciality;
                    new_employee.username       = username;

                    final DatabaseReference ref = database.getReference("Employee");

                    ref.push().setValue( new_employee );

                    Toast.makeText(PiedPiperAddEmployeeActivity.this, "Employee added successfully!", Toast.LENGTH_LONG).show();

                    refreshFields();

                }
                catch ( Exception e )
                {
                    Toast.makeText(PiedPiperAddEmployeeActivity.this, "Something went wrong. Please check entered data!", Toast.LENGTH_LONG).show();
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

                ArrayAdapter<String> table_nos_adapter = new ArrayAdapter<String>(PiedPiperAddEmployeeActivity.this, android.R.layout.simple_spinner_item, table_nos);
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

    private void initializeDesignationSpinner()
    {
        final List<String> designations = new ArrayList<>();
        designations.add( "Waiter" );
        designations.add( "Chef" );
        designations.add( "Manager" );
        designations.add( "Chef Manager" );

        ArrayAdapter<String> designations_adapter = new ArrayAdapter<String>(PiedPiperAddEmployeeActivity.this, android.R.layout.simple_spinner_item, designations);
        designations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_designation.setAdapter(designations_adapter);
    }

    private void refreshFields()
    {
        sp_designation.setSelection( 0 );;
        et_email.setText( "" );;
        et_name.setText( "" );;
        et_salary.setText( "" );
        et_shift_start.setText( "" );
        et_shift_end.setText( "" );
        et_speciality.setText( "" );
        et_username.setText( "" );
        et_password.setText( "" );
        sp_table_no.setSelection( 0 );
    }
}
